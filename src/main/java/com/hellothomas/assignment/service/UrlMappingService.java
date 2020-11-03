package com.hellothomas.assignment.service;

import com.hellothomas.assignment.domain.UrlMapping;
import com.hellothomas.assignment.enums.UrlTypeEnum;
import com.hellothomas.assignment.infrastructure.mapper.UrlMappingMapper;
import org.springframework.stereotype.Service;

/**
 * @className UrlMappingService
 * @author Thomas
 * @date 2020/11/2 17:24
 * @description
 * @version 1.0
 */
@Service
public class UrlMappingService {
    private final UrlMappingMapper urlMappingMapper;

    public UrlMappingService(UrlMappingMapper urlMappingMapper) {
        this.urlMappingMapper = urlMappingMapper;
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
