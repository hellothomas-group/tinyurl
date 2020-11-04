package com.hellothomas.assignment.controller;

import com.hellothomas.assignment.pojo.CreateParams;
import com.hellothomas.assignment.service.TinyUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
     * @param createParams
     * @return org.springframework.http.ResponseEntity<java.util.Map>
     */
    @PostMapping(value = "/create")
    @ApiOperation(value = "创建tinyURL")
    public ResponseEntity<Map> createTinyURL(@RequestBody CreateParams createParams) {
        Map<String, String> resultMap = new HashMap();
        String originUrlStr = createParams.getOriginUrl();
        log.info("origin_url: " + originUrlStr);
        String tinyUrlStr = tinyURLService.createTinyUrl(originUrlStr);
        log.info("tiny_url: " + tinyUrlStr);
        resultMap.put("origin_url", originUrlStr);
        resultMap.put("tiny_url", tinyUrlStr);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);

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
    public ResponseEntity<Map> getOriginURL(@RequestParam("tiny_url") String tinyUrlStr) {
        Map<String, String> resultMap = new HashMap();
        log.info("tiny_url: " + tinyUrlStr);
        String originUrlStr = tinyURLService.getOriginUrl(tinyUrlStr);

        resultMap.put("origin_url", originUrlStr);
        resultMap.put("tiny_url", tinyUrlStr);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

}
