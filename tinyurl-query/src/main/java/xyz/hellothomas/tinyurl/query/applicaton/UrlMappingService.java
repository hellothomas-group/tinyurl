package xyz.hellothomas.tinyurl.query.applicaton;

import xyz.hellothomas.tinyurl.query.domain.UrlMapping;
import xyz.hellothomas.tinyurl.query.domain.UrlMappingExample;
import xyz.hellothomas.tinyurl.query.infrastructure.mapper.UrlMappingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static xyz.hellothomas.tinyurl.query.common.constants.Constants.ID_ENCODE_CACHE_NAME;

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

    @Cacheable(cacheNames = ID_ENCODE_CACHE_NAME, key = "#seqEncode", unless = "#result == null")
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

}
