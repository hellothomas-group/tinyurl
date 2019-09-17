package com.hellothomas.assignment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
@Service
public class UniqueSeqService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniqueSeqService.class);

    private AtomicLong aLong;
    private Map<String, Long> originValueMap;
    private Map<Long, String> seqValueMap;

    public UniqueSeqService() {
        String seqInitValue = System.getenv("SEQ_INIT_VALUE");
        Assert.hasText(seqInitValue, "环境变量序号初始值(SEQ_INIT_VALUE)未设置");
        LOGGER.info("SEQ_INIT_VALUE:" + seqInitValue);
        aLong = new AtomicLong(Long.parseLong(seqInitValue));
        originValueMap = new ConcurrentHashMap<String, Long>();
        seqValueMap = new ConcurrentHashMap<Long, String>();
    }

    /**
     * @Author 80234613
     * @Date 2019-7-7 12:19
     * @Descripton 为OriginURL生成全局唯一序号
     * @param originUrl
     * @Return long
     */
    public long generateSeq(String originUrl) {
        Long seq = originValueMap.get(originUrl);
        if (seq != null) {
            LOGGER.info("既有seq：" + seq.toString());
            return seq;
        }
        seq = aLong.incrementAndGet();
        LOGGER.info("新生成seq：" + seq.toString());
        originValueMap.put(originUrl, seq);
        seqValueMap.put(seq, originUrl);
        return seq;
    }

    /**
     * @Author 80234613
     * @Date 2019-7-7 12:19
     * @Descripton 全局唯一序号转换为原originURL
     * @param seq
     * @Return java.lang.String
     */
    public String SeqConvertToOriginUrl(long seq) {
        return seqValueMap.get(seq);
    }

}
