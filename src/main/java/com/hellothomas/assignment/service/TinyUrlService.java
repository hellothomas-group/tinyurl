package com.hellothomas.assignment.service;

import com.hellothomas.assignment.enums.UrlTypeEnum;
import com.hellothomas.assignment.exception.MyException;
import com.hellothomas.assignment.utils.UrlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.net.URL;

import static com.hellothomas.assignment.constants.Constants.PROXY_PATH;
import static com.hellothomas.assignment.enums.ErrorCodeEnum.URL_FORMAT_ERROR;

/**
 * @ClassName TinyUrlService
 * @Author 80234613
 * @Date 2019-7-7 12:33
 * @Descripton TinyUrlService
 * @Version 1.0
 */
@Service("tinyURLService")
public class TinyUrlService {

    @Value("${tiny-url.address}")
    private String address;

    private final UniqueSeqService uniqueSeqService;
    private final DecimalConvertService decimalConvertService;
    private final UrlMappingService urlMappingService;

    public TinyUrlService(UniqueSeqService uniqueSeqService, DecimalConvertService decimalConvertService, UrlMappingService urlMappingService) {
        this.uniqueSeqService = uniqueSeqService;
        this.decimalConvertService = decimalConvertService;
        this.urlMappingService = urlMappingService;
    }

    /**
     * @Author 80234613
     * @Date 2019-7-7 12:36
     * @Descripton 根据originURL创建tinyURL方法
     * @param originUrl
     * @Return java.lang.String
     */
    public String createTinyUrl(String originUrl) {
        // 校验url合法性
        checkUrl(originUrl);
        String originUrlMd5 = DigestUtils.md5DigestAsHex(originUrl.getBytes());
        String seqEncode = uniqueSeqService.generateSeqEncode(originUrl, originUrlMd5);
        // todo 性能优化
        long seq = decimalConvertService.decimalConvertToNumber(seqEncode, 62);
        urlMappingService.insertRecord(seq, originUrl, seqEncode, originUrlMd5, UrlTypeEnum.SYSTEM);
        return address + PROXY_PATH + "/" + seqEncode;
    }

    /**
     * @Author 80234613
     * @Date 2019-7-7 12:36
     * @Descripton 根据tinyURL查询oringURL
     * @param tinyUrlStr
     * @Return java.lang.String
     */
    public String getOriginUrl(String tinyUrlStr) {
        checkUrl(tinyUrlStr);

        if (!tinyUrlStr.startsWith(address + PROXY_PATH)) {
            return null;
        }

        String seqEncode = StringUtils.removeStartIgnoreCase(tinyUrlStr, address + PROXY_PATH + "/");

        String originUrlStr = uniqueSeqService.seqConvertToOriginUrl(seqEncode);
        if (originUrlStr == null) {
            throw new MyException(URL_FORMAT_ERROR);
        }
        return originUrlStr;
    }

    private void checkUrl(String urlStr) {
        URL tinyURL = UrlUtil.parse(urlStr);
        if (tinyURL == null) {
            throw new MyException(URL_FORMAT_ERROR);
        }
    }

}
