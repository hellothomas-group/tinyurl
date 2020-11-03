package com.hellothomas.assignment.infrastructure.mapper;

import com.hellothomas.assignment.domain.UrlMapping;
import com.hellothomas.assignment.domain.UrlMappingExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UrlMappingMapper {
    long countByExample(UrlMappingExample example);

    int deleteByExample(UrlMappingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UrlMapping record);

    int insertSelective(UrlMapping record);

    List<UrlMapping> selectByExample(UrlMappingExample example);

    UrlMapping selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UrlMapping record, @Param("example") UrlMappingExample example);

    int updateByExample(@Param("record") UrlMapping record, @Param("example") UrlMappingExample example);

    int updateByPrimaryKeySelective(UrlMapping record);

    int updateByPrimaryKey(UrlMapping record);
}