package xyz.hellothomas.tinyurl.generator.applicaton;

import xyz.hellothomas.tinyurl.generator.api.dto.TinyUrlCreateResponse;
import xyz.hellothomas.tinyurl.generator.common.utils.UrlUtil;
import xyz.hellothomas.tinyurl.generator.domain.vo.UrlMappingResult;
import xyz.hellothomas.tinyurl.generator.common.enums.ErrorCodeEnum;
import xyz.hellothomas.tinyurl.generator.infrastructure.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * @ClassName TinyUrlService
 * @Author 80234613
 * @Date 2019-7-7 12:33
 * @Descripton TinyUrlService
 * @Version 1.0
 */
@Service
@Slf4j
public class TinyUrlService {
    private static final String SEQ_ENCODE_PATTERN = "^[[A-Za-z0-9]+]{6}";

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
     * @author 80234613 唐圆
     * @date 2021/1/4
     * @param originUrl
     * @param userId
     * @param expirationTime
     * @return TinyUrlCreateResponse
     * @throws
     */
    public TinyUrlCreateResponse createTinyUrlResponse(String originUrl, String userId, LocalDateTime expirationTime) {
        UrlUtil.checkUrl(originUrl);

        String originUrlMd5 = DigestUtils.md5DigestAsHex(originUrl.getBytes());

        UrlMappingResult result = urlMappingService.queryUrlMappingResult(originUrlMd5);
        if (result != null && result.getSeqEncode() != null) {
            log.info("既有seqEncode：" + result.getSeqEncode());
        } else {
            result = uniqueSeqService.generateUrlMappingResult(originUrl, originUrlMd5, userId, expirationTime);
        }

        return TinyUrlCreateResponse.builder()
                .originUrl(result.getOriginUrl())
                .tinyUrl(address + "/" + result.getSeqEncode())
                .expirationTime(result.getExpireTime())
                .build();
    }

    /**
     * @Author 80234613
     * @Date 2019-7-7 12:36
     * @Descripton 根据tinyURL查询originURL
     * @param tinyUrlStr
     * @Return java.lang.String
     */
    public String getOriginUrl(String tinyUrlStr) {
        if (!tinyUrlStr.startsWith(address)) {
            throw new MyException(ErrorCodeEnum.URL_ADDRESS_INVALID);
        }
        String seqEncode = StringUtils.removeStartIgnoreCase(tinyUrlStr, address + "/");
        checkSeqEncode(seqEncode);
        return uniqueSeqService.seqEncodeConvertToOriginUrl(seqEncode);
    }

    private void checkSeqEncode(String seqEncode) {
        if (!Pattern.matches(SEQ_ENCODE_PATTERN, seqEncode)) {
            throw new MyException(ErrorCodeEnum.URL_PATH_INVALID);
        }
    }
}
