package com.hellothomas.assignment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 */
@Getter
@Setter
@ToString
@Builder
public class UrlSequence implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 起始序号
     */
    private Long startSeq;

    /**
     * 结束序号
     */
    private Long endSeq;

    /**
     * 主机名
     */
    private String hostName;

    /**
     * 主机IP
     */
    private String hostIp;

    /**
     * 生成时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;
}