package com.hellothomas.assignment.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;

import static com.hellothomas.assignment.constants.Constants.PROXY_PATH;

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

    @Autowired
    private UniqueSeqService uniqueSeqService;

    @Autowired
    private DecimalConvertService decimalConvertService;

    /**
     * @Author 80234613
     * @Date 2019-7-7 12:36
     * @Descripton 根据originURL创建tinyURL方法
     * @param originUrl
     * @Return java.lang.String
     */
    public String createTinyUrl(URL originUrl) {
        long seq = uniqueSeqService.generateSeq(originUrl.toString());
        String tinyUrl = address + PROXY_PATH + "/" + decimalConvertService.numberConvertToDecimal(seq, 62);
        return tinyUrl;
    }

    /**
     * @Author 80234613
     * @Date 2019-7-7 12:36
     * @Descripton 根据tinyURL查询oringURL
     * @param tinyUrl
     * @Return java.lang.String
     */
    public String getOriginUrl(URL tinyUrl) {
        if (tinyUrl.getPath().length() < 2 || !tinyUrl.toString().startsWith(address + PROXY_PATH)) {
            return null;
        }

        String seqString = StringUtils.removeStartIgnoreCase(tinyUrl.getPath(), PROXY_PATH + "/");
        long seq = decimalConvertService.decimalConvertToNumber(seqString, 62);

        return uniqueSeqService.SeqConvertToOriginUrl(seq);
    }

}
