package com.hellothomas.tinyurl.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @ClassName TinyUrlCreateResponse
 * @Author 80234613
 * @Date 2019-7-8 20:57
 * @Descripton
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "创建tinyUrl返回对象")
public class TinyUrlCreateResponse {

    @ApiModelProperty(value = "原始url", required = true, dataType = "string", example = "\"https://oa.cmbchina" +
            ".com/OAMS/Shell/\"")
    private String originUrl;

    @ApiModelProperty(value = "tinyUrl", required = true, dataType = "string", example = "\"http://127.0.0" +
            ".1:8080/LLLLOt\"")
    private String tinyUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "过期时间", dataType = "string", example = "\"2021-01-31 10:22:22\"")
    private LocalDateTime expirationTime;
}
