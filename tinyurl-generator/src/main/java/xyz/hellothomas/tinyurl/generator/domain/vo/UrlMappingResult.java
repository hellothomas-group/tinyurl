package xyz.hellothomas.tinyurl.generator.domain.vo;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @className UrlMappingResult
 * @author 80234613 唐圆
 * @date 2021/1/4 14:27
 * @descripton
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
