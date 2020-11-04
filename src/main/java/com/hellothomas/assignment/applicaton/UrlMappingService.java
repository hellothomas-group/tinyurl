package com.hellothomas.assignment.applicaton;

import com.hellothomas.assignment.domain.UrlMapping;
import com.hellothomas.assignment.domain.UrlMappingExample;
import com.hellothomas.assignment.constants.enums.UrlTypeEnum;
import com.hellothomas.assignment.infrastructure.mapper.UrlMappingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hellothomas.assignment.constants.Constants.ID_ENCODE_PREFIX;
import static com.hellothomas.assignment.constants.Constants.ORIGIN_URL_MD5_KEY_PREFIX;

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

    public String querySeqEncode(String originUrlMd5) {
        String originUrlMd5Key = ORIGIN_URL_MD5_KEY_PREFIX.concat(originUrlMd5);
        String seqEncode = (String) redisTemplate.opsForValue().get(originUrlMd5Key);
        if (seqEncode != null) {
            // redis存在,直接返回
            return seqEncode;
        }
        UrlMappingExample urlMappingExample = new UrlMappingExample();
        urlMappingExample.createCriteria().andOriginUrlMd5EqualTo(originUrlMd5);
        List<UrlMapping> urlMappings = urlMappingMapper.selectByExample(urlMappingExample);

        if (!urlMappings.isEmpty()) {
            log.debug("redis不存在{},数据库存在", originUrlMd5Key);
            seqEncode = urlMappings.get(0).getTinyUrl();
            // redis不存在,数据库存在
            redisTemplate.opsForValue().set(originUrlMd5Key, seqEncode);
        }

        return seqEncode;
    }

    public String queryOriginUrl(String seqEncode) {
        String idEncodeKey = ID_ENCODE_PREFIX.concat(seqEncode);
        String originUrl = (String) redisTemplate.opsForValue().get(idEncodeKey);
        if (originUrl != null) {
            // redis存在,直接返回
            return originUrl;
        }
        UrlMappingExample urlMappingExample = new UrlMappingExample();
        urlMappingExample.createCriteria().andTinyUrlEqualTo(seqEncode);
        List<UrlMapping> urlMappings = urlMappingMapper.selectByExample(urlMappingExample);

        if (!urlMappings.isEmpty()) {
            originUrl = urlMappings.get(0).getOriginUrl();
            // redis不存在,数据库存在
            redisTemplate.opsForValue().set(idEncodeKey, originUrl);
        }

        return seqEncode;
    }

    public int insertRecord(long id, String originUrl, String seqEncode, String originUrlMd5,
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
