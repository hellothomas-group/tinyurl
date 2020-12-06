package com.hellothomas.tinyurl.applicaton;

import com.hellothomas.tinyurl.common.enums.UrlTypeEnum;
import com.hellothomas.tinyurl.domain.UrlMapping;
import com.hellothomas.tinyurl.domain.UrlMappingExample;
import com.hellothomas.tinyurl.infrastructure.mapper.UrlMappingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private final RedisTemplate redisTemplate;
    private final UrlMappingMapper urlMappingMapper;

    public UrlMappingService(RedisTemplate redisTemplate, UrlMappingMapper urlMappingMapper) {
        this.redisTemplate = redisTemplate;
        this.urlMappingMapper = urlMappingMapper;
    }

    @Cacheable(cacheNames = "OriginUrlMd5", key = "#originUrlMd5",
            unless = "#result == null")
    public String querySeqEncode(String originUrlMd5) {
//        String originUrlMd5Key = ORIGIN_URL_MD5_KEY_PREFIX.concat(originUrlMd5);
//        String seqEncode = (String) redisTemplate.opsForValue().get(originUrlMd5Key);
//        if (seqEncode != null) {
//            // redis存在,直接返回
//            return seqEncode;
//        }
        String seqEncode = null;
        UrlMappingExample urlMappingExample = new UrlMappingExample();
        urlMappingExample.createCriteria().andOriginUrlMd5EqualTo(originUrlMd5);
        List<UrlMapping> urlMappings = urlMappingMapper.selectByExample(urlMappingExample);

        if (!urlMappings.isEmpty()) {
            seqEncode = urlMappings.get(0).getTinyUrl();
        }

        return seqEncode;
    }

    @Cacheable(cacheNames = "IdEncode", key = "#seqEncode", unless = "#result == null")
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

    @CachePut(cacheNames = "IdEncode", key = "#seqEncode", unless = "#result == null")
    public String saveUrlMapping(String originUrlStr, String originUrlMd5, long seq, String seqEncode) {
        insertRecord(seq, originUrlStr, seqEncode, originUrlMd5, UrlTypeEnum.SYSTEM);
        // 为了缓存redis
        return originUrlStr;
    }

    private int insertRecord(long id, String originUrl, String seqEncode, String originUrlMd5,
                             UrlTypeEnum urlTypeEnum) {
        UrlMapping urlMapping = UrlMapping.builder()
                .id(id)
                .originUrl(originUrl)
                .tinyUrl(seqEncode)
                .originUrlMd5(originUrlMd5)
                .urlType(urlTypeEnum.getValue())
                .build();

        return urlMappingMapper.insertSelective(urlMapping);
    }
}
