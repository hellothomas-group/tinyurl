package xyz.hellothomas.tinyurl.query.applicaton;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import xyz.hellothomas.tinyurl.common.domain.UrlMapping;
import xyz.hellothomas.tinyurl.common.domain.UrlMappingExample;
import xyz.hellothomas.tinyurl.common.infrastructure.mapper.UrlMappingMapper;
import xyz.hellothomas.tinyurl.query.common.constants.QueryConstants;

import java.util.List;

/**
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

    @Cacheable(cacheNames = QueryConstants.ID_ENCODE_CACHE_NAME, key = "#seqEncode", unless = "#result == null")
    public String queryOriginUrl(String seqEncode, int partitionTag) {
        String originUrl = null;
        UrlMappingExample urlMappingExample = new UrlMappingExample();
        urlMappingExample.createCriteria().andTinyUrlEqualTo(seqEncode)
                .andPartitionTagEqualTo(partitionTag);
        List<UrlMapping> urlMappings = urlMappingMapper.selectByExample(urlMappingExample);

        if (!urlMappings.isEmpty()) {
            originUrl = urlMappings.get(0).getOriginUrl();
        }

        return originUrl;
    }

}
