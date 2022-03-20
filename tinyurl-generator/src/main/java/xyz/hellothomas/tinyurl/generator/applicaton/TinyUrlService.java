package xyz.hellothomas.tinyurl.generator.applicaton;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import xyz.hellothomas.tinyurl.common.common.constants.CommonConstants;
import xyz.hellothomas.tinyurl.common.infrastructure.exception.MyException;
import xyz.hellothomas.tinyurl.generator.api.dto.TinyUrlCreateResponse;
import xyz.hellothomas.tinyurl.generator.common.enums.GeneratorErrorCodeEnum;
import xyz.hellothomas.tinyurl.common.common.utils.DecimalConvertUtil;
import xyz.hellothomas.tinyurl.generator.common.utils.UrlUtil;
import xyz.hellothomas.tinyurl.generator.domain.vo.UrlMappingResult;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * @author Thomas
 * @date 2022/3/19 23:44
 * @description
 * @version 1.0
 */
@Service
@Slf4j
public class TinyUrlService {
    private static final String SEQ_ENCODE_PATTERN = "^[[A-Za-z0-9]+]{8}";

    private final UrlMappingService urlMappingService;
    private final UniqueSeqService uniqueSeqService;
    @Value("${tiny-url.address}")
    private String address;

    public TinyUrlService(UrlMappingService urlMappingService, UniqueSeqService uniqueSeqService) {
        this.urlMappingService = urlMappingService;
        this.uniqueSeqService = uniqueSeqService;
    }

    /**
     * 根据originURL创建tinyURL方法
     *
     * @param originUrl
     * @param userId
     * @param expirationTime
     * @return
     */
    public TinyUrlCreateResponse createTinyUrlResponse(String originUrl, String userId, LocalDateTime expirationTime) {
        UrlUtil.checkUrl(originUrl);

        String originUrlMd5 = DigestUtils.md5DigestAsHex(originUrl.getBytes());
        int partitionTag = getPartitionTag(originUrlMd5);

        UrlMappingResult result = urlMappingService.queryUrlMappingResult(originUrlMd5, partitionTag);
        if (result != null && result.getSeqEncode() != null) {
            log.info("既有seqEncode：" + result.getSeqEncode());
        } else {
            result = uniqueSeqService.generateUrlMappingResult(originUrl, originUrlMd5, partitionTag, userId,
                    expirationTime);
        }

        return TinyUrlCreateResponse.builder()
                .originUrl(result.getOriginUrl())
                .tinyUrl(String.format("%s/%s%s", address,
                        DecimalConvertUtil.numberConvertToDecimal(result.getPartitionTag(), 62, 2),
                        result.getSeqEncode()))
                .expirationTime(result.getExpireTime())
                .build();
    }

    /**
     * 根据tinyURL查询originURL
     *
     * @param tinyUrlStr
     * @return
     */
    public String getOriginUrl(String tinyUrlStr) {
        if (!tinyUrlStr.startsWith(address)) {
            throw new MyException(GeneratorErrorCodeEnum.URL_ADDRESS_INVALID);
        }
        String path = StringUtils.removeStartIgnoreCase(tinyUrlStr, address + "/");

        checkPath(path);
        int partitionTag = (int) DecimalConvertUtil.decimalConvertToNumber(path.substring(0, 2), 62);
        checkPartitionTag(partitionTag);
        String seqEncode = path.substring(2);
        return uniqueSeqService.seqEncodeConvertToOriginUrl(seqEncode, partitionTag);
    }

    public int getPartitionTag(String originUrlMd5) {
        return Math.abs(originUrlMd5.hashCode() % CommonConstants.PARTITION_COUNT) + 1;
    }

    private void checkPartitionTag(int partitionTag) {
        if (partitionTag < 1 || partitionTag > CommonConstants.PARTITION_COUNT) {
            throw new MyException(GeneratorErrorCodeEnum.URL_PATH_INVALID);
        }
    }

    private void checkPath(String path) {
        if (!Pattern.matches(SEQ_ENCODE_PATTERN, path)) {
            throw new MyException(GeneratorErrorCodeEnum.URL_PATH_INVALID);
        }
    }
}
