package xyz.hellothomas.tinyurl.generator.applicaton;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import xyz.hellothomas.tinyurl.common.infrastructure.exception.MyException;
import xyz.hellothomas.tinyurl.generator.common.enums.GeneratorErrorCodeEnum;
import xyz.hellothomas.tinyurl.generator.common.utils.DecimalConvertUtil;
import xyz.hellothomas.tinyurl.generator.common.utils.SleepUtil;
import xyz.hellothomas.tinyurl.generator.domain.UrlSequence;
import xyz.hellothomas.tinyurl.generator.domain.vo.UrlMappingResult;
import xyz.hellothomas.tinyurl.generator.infrastructure.mapper.UrlSequenceMapper;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static xyz.hellothomas.tinyurl.generator.common.constants.Constants.*;

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
    private static final int GENERATE_REPEAT_COUNT = 3;
    private static final int REFRESH_REPEAT_COUNT = 10;
    private static final int GENERATE_WAIT_MS = 500;
    private static final int REFRESH_WAIT_MAX_MS = 500;

    private final UrlMappingService urlMappingService;
    private final UrlSequenceMapper urlSequenceMapper;

    @Value("${tiny-url.seq.increment-step}")
    private Long seqIncrementStep = 1L;
    private AtomicLong atomicCount;
    private volatile Long startSeq;
    private String hostName;
    private String hostAddress;
    private Random random;

    public UniqueSeqService(UrlMappingService urlMappingService, UrlSequenceMapper urlSequenceMapper) {
        this.urlMappingService = urlMappingService;
        this.urlSequenceMapper = urlSequenceMapper;
        this.atomicCount = new AtomicLong();
        this.random = new Random();
    }

    /**
     * 为OriginURL生成全局唯一序号
     *
     * @author 80234613 唐圆
     * @date 2021/1/4
     * @param originUrlStr
     * @param originUrlMd5
     * @param userId
     * @param expirationTime
     * @return UrlMappingResult
     * @throws
     */
    @CachePut(cacheNames = ORIGIN_URL_MD5_CACHE_NAME, key = "#originUrlMd5", unless = "#result == null")
    public UrlMappingResult generateUrlMappingResult(String originUrlStr, String originUrlMd5, String userId,
                                                     LocalDateTime expirationTime) {
        long seq = generateSeq();
        log.info("新生成seq：" + seq);

        String seqEncode = DecimalConvertUtil.numberConvertToDecimal(seq, 62);

        // 默认过期时间为一年
        if (expirationTime == null) {
            expirationTime = LocalDateTime.now().plusYears(1);
        }

        urlMappingService.saveUrlMapping(originUrlStr, originUrlMd5, seq, seqEncode, userId, expirationTime);

        return UrlMappingResult.builder()
                .originUrl(originUrlStr)
                .seqEncode(seqEncode)
                .userId(userId)
                .expireTime(expirationTime)
                .build();
    }

    private long generateSeq() {
        int generateCount = 1;
        while (generateCount <= GENERATE_REPEAT_COUNT) {
            long seqCount = atomicCount.incrementAndGet();
            if (seqCount <= seqIncrementStep) {
                return startSeq + seqCount - 1;
            } else if (seqCount == seqIncrementStep + 1) {
                // todo 刷新失败情况
                refreshUrlSeqRange();
                continue;
            } else {
                log.info("等待重试生成序号");
                SleepUtil.millisecond(GENERATE_WAIT_MS);
            }

            generateCount++;
        }

        log.error(GeneratorErrorCodeEnum.GENERATE_SEQ_ERROR.getMessage());
        throw new MyException(GeneratorErrorCodeEnum.GENERATE_SEQ_ERROR);
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
            throw new MyException(GeneratorErrorCodeEnum.URL_NOT_EXIST);
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
            log.warn(GeneratorErrorCodeEnum.GET_LOCAL_HOST_ERROR.getMessage(), e);
        }
        boolean refreshResult = refreshUrlSeqRange();
        if (!refreshResult) {
            log.error(GeneratorErrorCodeEnum.REFRESH_EXIT_ERROR.getMessage());
            throw new MyException(GeneratorErrorCodeEnum.REFRESH_EXIT_ERROR);
        }
    }

    private synchronized boolean refreshUrlSeqRange() {
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

                LocalDateTime currentDateTime = LocalDateTime.now();
                UrlSequence urlSequence = UrlSequence.builder()
                        .id(newId)
                        .hostName(hostName)
                        .hostIp(hostAddress)
                        .startSeq(newStartSeq)
                        .endSeq(newStartSeq + seqIncrementStep - 1)
                        .createTime(currentDateTime)
                        .updateTime(currentDateTime)
                        .build();

                urlSequenceMapper.insert(urlSequence);

                // 重置起始序号和计数器
                startSeq = newStartSeq;
                atomicCount.set(0);
                log.info("新起始序号{}, 步进{}", startSeq, seqIncrementStep);
                return true;
            } catch (Exception e) {
                log.warn("第{}次刷新UrlSeqRange失败, 异常为{}", refreshCount, e);
            }

            refreshCount++;
        }

        return false;
    }

}
