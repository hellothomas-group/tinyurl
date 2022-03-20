package xyz.hellothomas.tinyurl.generator.domain.vo;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Thomas
 * @date 2022/3/19 23:44
 * @description
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlMappingResult {
    /**
     * 原始长链接
     */
    private String originUrl;

    /**
     * 分区标志
     */
    private Integer partitionTag;

    /**
     * 编码后序号
     */
    private String seqEncode;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
}
