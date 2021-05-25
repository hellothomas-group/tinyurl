package xyz.hellothomas.tinyurl.query.applicaton;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static xyz.hellothomas.tinyurl.query.common.constants.Constants.CAFFEINE_NULL_CACHE_NAME;

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

    private final UrlMappingService urlMappingService;

    public UniqueSeqService(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    /**
     * @Author 80234613
     * @Date 2019-7-7 12:19
     * @Descripton 全局唯一序号转换为原originURL
     * @param seqEncode
     * @Return java.lang.String
     */
    @Cacheable(cacheNames = CAFFEINE_NULL_CACHE_NAME, cacheManager = "caffeineCacheManager",
            unless = "#result != null")
    public String seqEncodeConvertToOriginUrl(String seqEncode) {
        log.info("seqEncode: {}", seqEncode);
        String originUrl = urlMappingService.queryOriginUrl(seqEncode);
        log.info("originUrl: {}", originUrl);
        return originUrl;
    }

}
