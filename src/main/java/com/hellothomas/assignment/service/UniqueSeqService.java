package com.hellothomas.assignment.service;

import com.hellothomas.assignment.constants.enums.UrlTypeEnum;
import com.hellothomas.assignment.infrastructure.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

import static com.hellothomas.assignment.constants.Constants.ID_ENCODE_PREFIX;
import static com.hellothomas.assignment.constants.Constants.ORIGIN_URL_MD5_KEY_PREFIX;
import static com.hellothomas.assignment.constants.enums.ErrorCodeEnum.URL_NOT_EXIST;

/**
 * @ClassName UniqueSeqService
 * @Author 80234613
 * @Date 2019-7-4 13:22
 * @Descripton 全局唯一序号Service
 * @Version 1.0
 */
@Slf4j
@Service
public class UniqueSeqService {
    private final RedisTemplate redisTemplate;
    private final UrlMappingService urlMappingService;
    private final DecimalConvertService decimalConvertService;

    @Value("${seq.init-value}")
    private Long seqInitValue;
    private AtomicLong aLong;

    public UniqueSeqService(RedisTemplate redisTemplate, UrlMappingService urlMappingService, DecimalConvertService decimalConvertService) {
        this.redisTemplate = redisTemplate;
        this.urlMappingService = urlMappingService;
        this.decimalConvertService = decimalConvertService;
    }

    /**
     * @Author 80234613
     * @Date 2019-7-7 12:19
     * @Descripton 为OriginURL生成全局唯一序号
     * @param originUrlMd5
     * @Return java.lang.String
     */
    public String generateSeqEncode(String originUrlStr, String originUrlMd5) {
        String originUrlMd5Key = ORIGIN_URL_MD5_KEY_PREFIX.concat(originUrlMd5);
        String seqEncode = urlMappingService.querySeqEncode(originUrlMd5);
        if (seqEncode != null) {
            log.info("既有seqEncode：" + seqEncode);
            return seqEncode;
        }
        long seq = aLong.incrementAndGet();
        log.info("新生成seq：" + seq);
        seqEncode = decimalConvertService.numberConvertToDecimal(seq, 62);
        // 存数据库
        urlMappingService.insertRecord(seq, originUrlStr, seqEncode, originUrlMd5, UrlTypeEnum.SYSTEM);
        // 存redis
        redisTemplate.opsForValue().set(originUrlMd5Key, seqEncode);
        log.debug("{}放入redis成功", originUrlMd5Key);
        String idEncodeKey = ID_ENCODE_PREFIX.concat(seqEncode);
        redisTemplate.opsForValue().set(idEncodeKey, originUrlStr);
        log.debug("{}放入redis成功", idEncodeKey);

        return seqEncode;
    }

    /**
     * @Author 80234613
     * @Date 2019-7-7 12:19
     * @Descripton 全局唯一序号转换为原originURL
     * @param seqEncode
     * @Return java.lang.String
     */
    public String seqEncodeConvertToOriginUrl(String seqEncode) {
        String originUrl = urlMappingService.queryOriginUrl(seqEncode);
        if (originUrl == null) {
            throw new MyException(URL_NOT_EXIST);
        }
        return originUrl;
    }

    @PostConstruct
    private void init() {
        aLong = new AtomicLong(seqInitValue);
    }

}
