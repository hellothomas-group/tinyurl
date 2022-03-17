package xyz.hellothomas.tinyurl.generator.applicaton;

import com.sankuai.inf.leaf.IDGen;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import xyz.hellothomas.tinyurl.common.infrastructure.exception.MyException;
import xyz.hellothomas.tinyurl.generator.common.enums.GeneratorErrorCodeEnum;
import xyz.hellothomas.tinyurl.generator.common.utils.DecimalConvertUtil;
import xyz.hellothomas.tinyurl.generator.domain.vo.UrlMappingResult;

import java.time.LocalDateTime;

import static xyz.hellothomas.tinyurl.generator.common.constants.Constants.ORIGIN_URL_MD5_CACHE_NAME;

/**
 * @ClassName UniqueSeqService
 * @Author 80234613
 * @Date 2019-7-4 13:22
 * @Descripton 全局唯一序号Service
 * @Version 1.0
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
     * @author 80234613 唐圆
     * @date 2021/1/4
     * @param originUrlStr
     * @param originUrlMd5
     * @param userId
     * @param expirationTime
     * @return UrlMappingResult
     * @throws
     */
    @CachePut(cacheNames = ORIGIN_URL_MD5_CACHE_NAME, key = "#originUrlMd5", unless = "#result == null")
    public UrlMappingResult generateUrlMappingResult(String originUrlStr, String originUrlMd5, String userId,
                                                     LocalDateTime expirationTime) {
        long seq = generateSeq();
        log.info("新生成seq：" + seq);

        String seqEncode = DecimalConvertUtil.numberConvertToDecimal(seq, 62);

        // 默认过期时间为一年
        if (expirationTime == null) {
            expirationTime = LocalDateTime.now().plusYears(1);
        }

        urlMappingService.saveUrlMapping(originUrlStr, originUrlMd5, seq, seqEncode, userId, expirationTime);

        return UrlMappingResult.builder()
                .originUrl(originUrlStr)
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
     * @Author 80234613
     * @Date 2019-7-7 12:19
     * @Descripton 全局唯一序号转换为原originURL
     * @param seqEncode
     * @Return java.lang.String
     */
    public String seqEncodeConvertToOriginUrl(String seqEncode) {
        String originUrl = urlMappingService.queryOriginUrl(seqEncode);
        if (originUrl == null) {
            throw new MyException(GeneratorErrorCodeEnum.URL_NOT_EXIST);
        }
        return originUrl;
    }
}
