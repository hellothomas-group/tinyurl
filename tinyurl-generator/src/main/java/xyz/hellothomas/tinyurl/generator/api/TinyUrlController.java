package xyz.hellothomas.tinyurl.generator.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.hellothomas.tinyurl.common.dto.ApiResponse;
import xyz.hellothomas.tinyurl.generator.api.dto.TinyUrlCreateRequest;
import xyz.hellothomas.tinyurl.generator.api.dto.TinyUrlCreateResponse;
import xyz.hellothomas.tinyurl.generator.api.dto.TinyUrlGetResponse;
import xyz.hellothomas.tinyurl.generator.applicaton.TinyUrlService;

import javax.validation.Valid;

/**
 * @author Thomas
 * @date 2022/3/19 23:47
 * @description TinyURL生成和查询
 * @version 1.0
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
     * 根据originURL创建tinyURL
     *
     * @param userId
     * @param createRequest
     * @return
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
     * 根据tinyURL查询originURL
     *
     * @param tinyUrlStr
     * @return
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
