package com.hellothomas.tinyurl.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className ApiResponseCreate
 * @author 80234613 唐圆
 * @date 2021/1/14 18:49
 * @descripton
 * @version 1.0
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseCreate {
    @JsonProperty("code")
    private String code;

    @JsonProperty("data")
    private TinyUrlCreateResponse data;

    @JsonProperty("message")
    private String message;
}
