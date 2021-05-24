package com.hellothomas.tinyurl.infrastructure.mapper;

import com.hellothomas.tinyurl.domain.UrlSequence;
import com.hellothomas.tinyurl.domain.UrlSequenceExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UrlSequenceMapper {
    long countByExample(UrlSequenceExample example);

    int deleteByExample(UrlSequenceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UrlSequence record);

    int insertSelective(UrlSequence record);

    List<UrlSequence> selectByExample(UrlSequenceExample example);

    UrlSequence selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UrlSequence record, @Param("example") UrlSequenceExample example);

    int updateByExample(@Param("record") UrlSequence record, @Param("example") UrlSequenceExample example);

    int updateByPrimaryKeySelective(UrlSequence record);

    int updateByPrimaryKey(UrlSequence record);

    UrlSequence selectByMaxPrimaryKey();
}