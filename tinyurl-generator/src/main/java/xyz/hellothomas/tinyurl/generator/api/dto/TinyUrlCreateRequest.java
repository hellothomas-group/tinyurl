package xyz.hellothomas.tinyurl.generator.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @ClassName TinyUrlCreateRequest
 * @Author 80234613
 * @Date 2019-7-8 20:57
 * @Descripton
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@ApiModel(description = "创建tinyUrl请求对象")
public class TinyUrlCreateRequest {

    @NotBlank
    @Length(max = 2083)
    @ApiModelProperty(value = "原始url", required = true, dataType = "string", example = "\"https://gitee.com/hellothomas\"")
    private String originUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    @ApiModelProperty(value = "过期时间", dataType = "string", example = "\"2023-01-31 10:22:22\"")
    private LocalDateTime expirationTime;
}
