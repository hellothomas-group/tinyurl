package xyz.hellothomas.tinyurl.generator.applicaton;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.hellothomas.tinyurl.common.domain.UrlMapping;
import xyz.hellothomas.tinyurl.common.domain.UrlMappingExample;
import xyz.hellothomas.tinyurl.common.infrastructure.mapper.UrlMappingMapper;
import xyz.hellothomas.tinyurl.generator.common.enums.UrlTypeEnum;
import xyz.hellothomas.tinyurl.generator.domain.vo.UrlMappingResult;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Thomas
 * @date 2022/3/19 23:42
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

    public UrlMappingResult queryUrlMappingResult(String originUrlMd5, int partitionTag) {
        UrlMappingResult result = null;
        UrlMappingExample urlMappingExample = new UrlMappingExample();
        urlMappingExample.createCriteria().andOriginUrlMd5EqualTo(originUrlMd5)
                .andPartitionTagEqualTo(partitionTag);
        List<UrlMapping> urlMappings = urlMappingMapper.selectByExample(urlMappingExample);

        if (!urlMappings.isEmpty()) {
            UrlMapping urlMapping = urlMappings.get(0);
            result = UrlMappingResult.builder()
                    .originUrl(urlMapping.getOriginUrl())
                    .partitionTag(urlMapping.getPartitionTag())
                    .seqEncode(urlMapping.getTinyUrl())
                    .userId(urlMapping.getUserId())
                    .expireTime(urlMapping.getExpireTime() == null ? null : urlMapping.getExpireTime())
                    .build();
        }

        return result;
    }

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

    public String saveUrlMapping(String originUrlStr, String originUrlMd5, int partitionTag, long seq,
                                 String seqEncode, String userId, LocalDateTime expirationTime) {
        insertRecord(seq, originUrlStr, seqEncode, originUrlMd5, partitionTag, userId, expirationTime,
                UrlTypeEnum.SYSTEM);
        // 为了缓存redis
        return originUrlStr;
    }

    private int insertRecord(long id, String originUrl, String seqEncode, String originUrlMd5, int partitionTag,
                             String userId, LocalDateTime expirationTime, UrlTypeEnum urlTypeEnum) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        UrlMapping urlMapping = UrlMapping.builder()
                .id(id)
                .partitionTag(partitionTag)
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
