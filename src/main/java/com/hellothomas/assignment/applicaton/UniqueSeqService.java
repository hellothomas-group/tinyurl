package com.hellothomas.assignment.applicaton;

import com.hellothomas.assignment.common.enums.UrlTypeEnum;
import com.hellothomas.assignment.common.utils.SleepUtil;
import com.hellothomas.assignment.domain.UrlSequence;
import com.hellothomas.assignment.infrastructure.exception.MyException;
import com.hellothomas.assignment.infrastructure.mapper.UrlSequenceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static com.hellothomas.assignment.common.constants.Constants.DEFAULT_HOST_ADDRESS;
import static com.hellothomas.assignment.common.constants.Constants.DEFAULT_HOST_NAME;
import static com.hellothomas.assignment.common.enums.ErrorCodeEnum.*;

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
    private static final int GENERATE_REPEAT_COUNT = 5;
    private static final int REFRESH_REPEAT_COUNT = 10;
    private static final int GENERATE_WAIT_MS = 500;
    private static final int REFRESH_WAIT_MAX_MS = 500;

    private final UrlMappingService urlMappingService;
    private final DecimalConvertService decimalConvertService;
    private final UrlSequenceMapper urlSequenceMapper;

    @Value("${seq.increment-step}")
    private Long seqIncrementStep = 1L;
    private AtomicLong atomicCount;
    private volatile Long startSeq;
    private String hostName;
    private String hostAddress;
    private Random random;

    public UniqueSeqService(UrlMappingService urlMappingService, DecimalConvertService decimalConvertService, UrlSequenceMapper urlSequenceMapper) {
        this.urlMappingService = urlMappingService;
        this.decimalConvertService = decimalConvertService;
        this.urlSequenceMapper = urlSequenceMapper;
        this.atomicCount = new AtomicLong();
        this.random = new Random();
    }

    /**
     * @Author 80234613
     * @Date 2019-7-7 12:19
     * @Descripton 为OriginURL生成全局唯一序号
     * @param originUrlMd5
     * @Return java.lang.String
     */
    public String generateSeqEncode(String originUrlStr, String originUrlMd5) {
        String seqEncode = urlMappingService.querySeqEncode(originUrlMd5);
        if (seqEncode != null) {
            log.info("既有seqEncode：" + seqEncode);
            return seqEncode;
        }
        long seq = generateSeq();

        log.info("新生成seq：" + seq);
        seqEncode = decimalConvertService.numberConvertToDecimal(seq, 62);
        // 存数据库和redis
        urlMappingService.insertRecord(seq, originUrlStr, seqEncode, originUrlMd5, UrlTypeEnum.SYSTEM);

        return seqEncode;
    }

    private long generateSeq() {
        int generateCount = 1;
        while (generateCount <= GENERATE_REPEAT_COUNT) {
            long seqCount = atomicCount.incrementAndGet();
            if (seqCount <= seqIncrementStep) {
                return startSeq + seqCount - 1;
            } else if (seqCount == seqIncrementStep + 1) {
                refreshUrlSeqRange();
                continue;
            } else {
                log.info("等待重试生成序号");
                SleepUtil.millisecond(GENERATE_WAIT_MS);
            }

            generateCount++;
        }

        log.error(GENERATE_SEQ_ERROR.getMessage());
        throw new MyException(GENERATE_SEQ_ERROR);
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
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            hostName = localhost == null ? DEFAULT_HOST_NAME : localhost.getHostName();
            hostAddress = localhost == null ? DEFAULT_HOST_ADDRESS : localhost.getHostAddress();
        } catch (UnknownHostException e) {
            log.warn(GET_LOCAL_HOST_ERROR.getMessage(), e);
        }
        refreshUrlSeqRange();
    }

    private synchronized void refreshUrlSeqRange() {
        int refreshCount = 1;
        while (refreshCount <= REFRESH_REPEAT_COUNT) {
            int delayMs = random.nextInt(REFRESH_WAIT_MAX_MS);
            log.info("等待{}ms, 第{}次刷新UrlSeqRange", delayMs, refreshCount);
            SleepUtil.millisecond(delayMs);

            try {
                long newId = 1;
                long newStartSeq = 1;
                UrlSequence maxIdUrlSequence = urlSequenceMapper.selectByMaxPrimaryKey();
                if (maxIdUrlSequence != null) {
                    newId = maxIdUrlSequence.getId() + 1;
                    newStartSeq = maxIdUrlSequence.getEndSeq() + 1;
                }

                UrlSequence urlSequence = UrlSequence.builder()
                        .id(newId)
                        .hostName(hostName)
                        .hostIp(hostAddress)
                        .startSeq(newStartSeq)
                        .endSeq(newStartSeq + seqIncrementStep - 1)
                        .build();

                urlSequenceMapper.insert(urlSequence);

                // 重置起始序号和计数器
                startSeq = newStartSeq;
                atomicCount.set(0);
                log.info("新起始序号{}, 步进{}", startSeq, seqIncrementStep);
                return;
            } catch (Exception e) {
                log.warn("第{}次刷新UrlSeqRange失败, 异常为{}", refreshCount, delayMs, e);
            }

            refreshCount++;
        }


    }

}
