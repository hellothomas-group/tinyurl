package xyz.hellothomas.tinyurl.generator.applicaton;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import xyz.hellothomas.tinyurl.common.common.constants.Constants;
import xyz.hellothomas.tinyurl.common.domain.UrlMapping;
import xyz.hellothomas.tinyurl.common.domain.UrlMappingExample;
import xyz.hellothomas.tinyurl.common.infrastructure.mapper.UrlMappingMapper;
import xyz.hellothomas.tinyurl.generator.common.enums.UrlTypeEnum;
import xyz.hellothomas.tinyurl.generator.domain.vo.UrlMappingResult;

import java.time.LocalDateTime;
import java.util.List;

import static xyz.hellothomas.tinyurl.generator.common.constants.Constants.ORIGIN_URL_MD5_CACHE_NAME;

/**
 * @className UrlMappingService
 * @author Thomas
 * @date 2020/11/2 17:24
 * @description
 * @version 1.0
 */
@Slf4j
@Service
public class UrlMappingService {
    private final UrlMappingMapper urlMappingMapper;

    public UrlMappingService(UrlMappingMapper urlMappingMapper) {
        this.urlMappingMapper = urlMappingMapper;
    }

    /**
     * 使用 @Cacheable 注解时，默认为redisCache
     */
    @Cacheable(cacheNames = ORIGIN_URL_MD5_CACHE_NAME, key = "#originUrlMd5",
            unless = "#result == null")
    public String querySeqEncode(String originUrlMd5) {
        String seqEncode = null;
        UrlMappingExample urlMappingExample = new UrlMappingExample();
        urlMappingExample.createCriteria().andOriginUrlMd5EqualTo(originUrlMd5);
        List<UrlMapping> urlMappings = urlMappingMapper.selectByExample(urlMappingExample);

        if (!urlMappings.isEmpty()) {
            seqEncode = urlMappings.get(0).getTinyUrl();
        }

        return seqEncode;
    }

    @Cacheable(cacheNames = ORIGIN_URL_MD5_CACHE_NAME, key = "#originUrlMd5",
            unless = "#result == null")
    public UrlMappingResult queryUrlMappingResult(String originUrlMd5) {
        UrlMappingResult result = null;
        UrlMappingExample urlMappingExample = new UrlMappingExample();
        urlMappingExample.createCriteria().andOriginUrlMd5EqualTo(originUrlMd5);
        List<UrlMapping> urlMappings = urlMappingMapper.selectByExample(urlMappingExample);

        if (!urlMappings.isEmpty()) {
            UrlMapping urlMapping = urlMappings.get(0);
            result = UrlMappingResult.builder()
                    .originUrl(urlMapping.getOriginUrl())
                    .seqEncode(urlMapping.getTinyUrl())
                    .userId(urlMapping.getUserId())
                    .expireTime(urlMapping.getExpireTime() == null ? null : urlMapping.getExpireTime())
                    .build();
        }

        return result;
    }

    @Cacheable(cacheNames = Constants.ID_ENCODE_CACHE_NAME, key = "#seqEncode", unless = "#result == null")
    public String queryOriginUrl(String seqEncode) {
        String originUrl = null;
        UrlMappingExample urlMappingExample = new UrlMappingExample();
        urlMappingExample.createCriteria().andTinyUrlEqualTo(seqEncode);
        List<UrlMapping> urlMappings = urlMappingMapper.selectByExample(urlMappingExample);

        if (!urlMappings.isEmpty()) {
            originUrl = urlMappings.get(0).getOriginUrl();
        }

        return originUrl;
    }

    @CachePut(cacheNames = Constants.ID_ENCODE_CACHE_NAME, key = "#seqEncode", unless = "#result == null")
    public String saveUrlMapping(String originUrlStr, String originUrlMd5, long seq, String seqEncode, String userId,
                                 LocalDateTime expirationTime) {
        insertRecord(seq, originUrlStr, seqEncode, originUrlMd5, userId, expirationTime, UrlTypeEnum.SYSTEM);
        // 为了缓存redis
        return originUrlStr;
    }

    private int insertRecord(long id, String originUrl, String seqEncode, String originUrlMd5, String userId,
                             LocalDateTime expirationTime, UrlTypeEnum urlTypeEnum) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        UrlMapping urlMapping = UrlMapping.builder()
                .id(id)
                .originUrl(originUrl)
                .tinyUrl(seqEncode)
                .originUrlMd5(originUrlMd5)
                .userId(userId)
                .createTime(currentDateTime)
                .updateTime(currentDateTime)
                .expireTime(expirationTime == null ? null : expirationTime)
                .urlType(urlTypeEnum.getValue())
                .build();

        return urlMappingMapper.insert(urlMapping);
    }
}
