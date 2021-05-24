package com.hellothomas.tinyurl.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
public class ApiRequestCreate {

    private String originUrl;

    private LocalDateTime expirationTime;
}
