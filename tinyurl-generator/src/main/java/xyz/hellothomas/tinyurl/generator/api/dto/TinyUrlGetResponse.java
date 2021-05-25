package xyz.hellothomas.tinyurl.generator.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName TinyUrlGetResponse
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
@ApiModel(description = "查询originURL请求对象")
public class TinyUrlGetResponse {

    @ApiModelProperty(value = "原始url", required = true, dataType = "string", example = "\"https://oa.cmbchina" +
            ".com/OAMS/Shell/\"")
    private String originUrl;

    @ApiModelProperty(value = "tinyUrl", required = true, dataType = "string", example = "\"http://127.0.0" +
            ".1:8080/LLLLOt\"")
    private String tinyUrl;
}
