package com.hellothomas.assignment.controller;

import com.hellothomas.assignment.Pojo.CreateParams;
import com.hellothomas.assignment.exception.MyException;
import com.hellothomas.assignment.service.TinyUrlService;
import com.hellothomas.assignment.utils.UrlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
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
public class TinyUrlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TinyUrlController.class);

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
        Map<String, String> resultMap = new HashMap<String, String>();
        String originUrlStr = createParams.getOrigin_url();
        LOGGER.info("origin_url: " + originUrlStr);
        URL originURL = UrlUtil.parse(originUrlStr);
        if (originURL == null) {
            throw new MyException("URL格式错误！");
        }
        String tinyUrlStr = tinyURLService.createTinyUrl(originURL);
        LOGGER.info("tiny_url: " + tinyUrlStr);
        resultMap.put("origin_url", originUrlStr);
        resultMap.put("tiny_url", tinyUrlStr);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);

    }

    /**
     * @Author Thomas
     * @Date 2019/7/8
     * @Description 根据tinyURL查询oringURL
     * @param tinyUrlStr
     * @return org.springframework.http.ResponseEntity<java.util.Map>
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "查询originURL")
    public ResponseEntity<Map> getOriginURL(@RequestHeader("tiny_url") String tinyUrlStr) {
        Map<String, String> resultMap = new HashMap<String, String>();
        LOGGER.info("tiny_url: " + tinyUrlStr);
        URL tinyURL = UrlUtil.parse(tinyUrlStr);
        if (tinyURL == null) {
            throw new MyException("URL格式错误！");
        }
        String originUrlStr = tinyURLService.getOriginUrl(tinyURL);
        if (originUrlStr == null) {
            throw new MyException("无对应origin_url!");
        }
        resultMap.put("origin_url", originUrlStr);
        resultMap.put("tiny_url", tinyUrlStr);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

}
