package com.hellothomas.assignment.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

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

    @Value("${seq.init-value}")
    private Long seqInitValue;
    private AtomicLong aLong;

    private final DecimalConvertService decimalConvertService;
    /**
     * Key:originUrlMd5
     * Value:idEncode
     */
    private final Map<String, String> originValueMap;
    /**
     * Key:idEncode
     * Value:originUrl
     */
    private final Map<String, String> seqValueMap;


    public UniqueSeqService(DecimalConvertService decimalConvertService) {
        this.decimalConvertService = decimalConvertService;
        originValueMap = new ConcurrentHashMap();
        seqValueMap = new ConcurrentHashMap();
    }

    /**
     * @Author 80234613
     * @Date 2019-7-7 12:19
     * @Descripton 为OriginURL生成全局唯一序号
     * @param originUrlMd5
     * @Return java.lang.String
     */
    public String generateSeqEncode(String originUrlStr, String originUrlMd5) {
        String seqEncode = originValueMap.get(originUrlMd5);
        if (seqEncode != null) {
            log.info("既有seqEncode：" + seqEncode);
            return seqEncode;
        }
        long seq = aLong.incrementAndGet();
        log.info("新生成seq：" + seq);
        seqEncode = decimalConvertService.numberConvertToDecimal(seq, 62);
        originValueMap.put(originUrlMd5, seqEncode);
        seqValueMap.put(seqEncode, originUrlStr);
        return seqEncode;
    }

    /**
     * @Author 80234613
     * @Date 2019-7-7 12:19
     * @Descripton 全局唯一序号转换为原originURL
     * @param seqEncode
     * @Return java.lang.String
     */
    public String seqConvertToOriginUrl(String seqEncode) {
        return seqValueMap.get(seqEncode);
    }

    @PostConstruct
    private void init() {
        aLong = new AtomicLong(seqInitValue);
    }

}
