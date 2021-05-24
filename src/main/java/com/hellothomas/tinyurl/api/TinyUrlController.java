package com.hellothomas.tinyurl.api;

import com.hellothomas.tinyurl.api.dto.ApiResponse;
import com.hellothomas.tinyurl.api.dto.TinyUrlCreateRequest;
import com.hellothomas.tinyurl.api.dto.TinyUrlCreateResponse;
import com.hellothomas.tinyurl.api.dto.TinyUrlGetResponse;
import com.hellothomas.tinyurl.applicaton.TinyUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName TinyURLController
 * @Author 80234613
 * @Date 2019-7-4 13:18
 * @Descripton TinyURL生成和查询
 * @Version 1.0
 */
@RestController
@RequestMapping("/tinyurl")
@Api(tags = "tinyURL相关操作")
@Slf4j
public class TinyUrlController {

    private final TinyUrlService tinyURLService;

    TinyUrlController(TinyUrlService tinyURLService) {
        this.tinyURLService = tinyURLService;
    }

    /**
     * @Author Thomas
     * @Date 2019/7/8
     * @Description 根据originURL创建tinyURL
     * @param createRequest
     * @return org.springframework.http.ResponseEntity<java.util.Map>
     */
    @PostMapping(value = "/create")
    @ApiOperation(value = "创建tinyURL")
    public ApiResponse<TinyUrlCreateResponse> createTinyURL(@RequestHeader String userId,
                                                            @Valid @RequestBody TinyUrlCreateRequest createRequest) {
        log.info("tinyUrlCreateRequest: " + createRequest);
        TinyUrlCreateResponse createResponse = tinyURLService.createTinyUrlResponse(createRequest.getOriginUrl(),
                userId, createRequest.getExpirationTime());
        log.info("tinyUrl: " + createResponse.getTinyUrl());
        return ApiResponse.success(createResponse);
    }

    /**
     * @Author Thomas
     * @Date 2019/7/8
     * @Description 根据tinyURL查询originURL
     * @param tinyUrlStr
     * @return org.springframework.http.ResponseEntity<java.util.Map>
     */
    @GetMapping(value = "/get")
    @ApiOperation(value = "查询originURL")
    public ApiResponse<TinyUrlGetResponse> getOriginURL(@RequestParam("tinyUrl") String tinyUrlStr) {
        log.info("tinyUrl: " + tinyUrlStr);
        String originUrlStr = tinyURLService.getOriginUrl(tinyUrlStr);

        TinyUrlGetResponse getResponse = TinyUrlGetResponse.builder()
                .originUrl(originUrlStr)
                .tinyUrl(tinyUrlStr)
                .build();
        return ApiResponse.success(getResponse);
    }
}
