package xyz.hellothomas.tinyurl.generator.applicaton;

import com.sankuai.inf.leaf.IDGen;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.hellothomas.tinyurl.common.infrastructure.exception.MyException;
import xyz.hellothomas.tinyurl.generator.common.enums.GeneratorErrorCodeEnum;
import xyz.hellothomas.tinyurl.common.common.utils.DecimalConvertUtil;
import xyz.hellothomas.tinyurl.generator.domain.vo.UrlMappingResult;

import java.time.LocalDateTime;

/**
 * @author Thomas
 * @date 2022/3/19 23:42
 * @description 全局唯一序号Service
 * @version 1.0
 */
@Slf4j
@Service
public class UniqueSeqService {
    private static final String BIZ_TAG = "tinyurl";
    private final UrlMappingService urlMappingService;
    private final IDGen idGen;

    public UniqueSeqService(UrlMappingService urlMappingService, IDGen idGen) {
        this.urlMappingService = urlMappingService;
        this.idGen = idGen;
    }

    /**
     * 为OriginURL生成全局唯一序号
     *
     * @param originUrlStr
     * @param originUrlMd5
     * @param partitionTag
     * @param userId
     * @param expirationTime
     * @return
     */
    public UrlMappingResult generateUrlMappingResult(String originUrlStr, String originUrlMd5, int partitionTag,
                                                     String userId, LocalDateTime expirationTime) {
        long seq = generateSeq();
        log.info("新生成seq：" + seq);

        String seqEncode = DecimalConvertUtil.numberConvertToDecimal(seq, 62);

        // 默认过期时间为一年
        if (expirationTime == null) {
            expirationTime = LocalDateTime.now().plusYears(1);
        }

        urlMappingService.saveUrlMapping(originUrlStr, originUrlMd5, partitionTag, seq, seqEncode, userId,
                expirationTime);

        return UrlMappingResult.builder()
                .originUrl(originUrlStr)
                .partitionTag(partitionTag)
                .seqEncode(seqEncode)
                .userId(userId)
                .expireTime(expirationTime)
                .build();
    }

    private long generateSeq() {
        Result result = idGen.get(BIZ_TAG);
        if (Status.SUCCESS == result.getStatus()) {
            return result.getId();
        }

        log.error(GeneratorErrorCodeEnum.GENERATE_SEQ_ERROR.getMessage());
        throw new MyException(GeneratorErrorCodeEnum.GENERATE_SEQ_ERROR);
    }

    /**
     * 全局唯一序号转换为原originURL
     *
     * @param seqEncode
     * @param partitionTag
     * @return
     */
    public String seqEncodeConvertToOriginUrl(String seqEncode, int partitionTag) {
        String originUrl = urlMappingService.queryOriginUrl(seqEncode, partitionTag);
        if (originUrl == null) {
            throw new MyException(GeneratorErrorCodeEnum.URL_NOT_EXIST);
        }
        return originUrl;
    }
}
