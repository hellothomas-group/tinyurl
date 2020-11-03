package com.hellothomas.assignment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @classname UrlMapping
 * @author Thomas
 * @date 2020/11/2 16:51
 * @description
 * @version 1.0
 */
@Getter
@Setter
@ToString
@Builder
public class UrlMapping implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 原始长链接
     */
    private String originUrl;

    /**
     * 长链接md5值
     */
    private String originUrlMd5;

    /**
     * 短链接
     */
    private String tinyUrl;

    /**
     * 短链接类型生成类型,系统: “system”,自定义: “custom” 0为system,1为custom 缺省为0
     */
    private Integer urlType;

    /**
     * 生成时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;
}