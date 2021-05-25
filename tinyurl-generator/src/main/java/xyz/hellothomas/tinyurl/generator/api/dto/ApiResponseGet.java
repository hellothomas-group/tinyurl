package xyz.hellothomas.tinyurl.generator.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className ApiResponseGet
 * @author 80234613 唐圆
 * @date 2021/1/14 18:51
 * @descripton
 * @version 1.0
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseGet {
    @JsonProperty("code")
    private String code;

    @JsonProperty("data")
    private TinyUrlGetResponse data;

    @JsonProperty("message")
    private String message;
}
