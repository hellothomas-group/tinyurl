package xyz.hellothomas.tinyurl.common.infrastructure.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.hellothomas.tinyurl.common.domain.UrlMapping;
import xyz.hellothomas.tinyurl.common.domain.UrlMappingExample;

import java.util.List;

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