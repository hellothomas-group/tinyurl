package com.hellothomas.assignment.service;

import com.hellothomas.assignment.utils.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * @ClassName TinyUrlService
 * @Author 80234613
 * @Date 2019-7-7 12:33
 * @Descripton TinyUrlService
 * @Version 1.0
 */
@Service("tinyURLService")
public class TinyUrlService {

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
        String prefixStr = originUrl.getProtocol() + "://" + originUrl.getHost() + "/";
        long seq = uniqueSeqService.generateSeq(originUrl.toString());
        String tinyUrl = prefixStr + decimalConvertService.numberConvertToDecimal(seq, 62);
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
        if (tinyUrl.getPath().length() < 2) {
            return null;
        }
        String prefixTinyurl = tinyUrl.getProtocol() + "://" + tinyUrl.getHost() + "/";
        long seq = decimalConvertService.decimalConvertToNumber(tinyUrl.getPath().substring(1), 62);
        String originUrlStr = uniqueSeqService.SeqConvertToOriginUrl(seq);
        URL originUrl = UrlUtil.parse(originUrlStr);
        if (originUrl == null) {
            return null;
        }

        String prefixOriginUrl = originUrl.getProtocol() + "://" + originUrl.getHost() + "/";
        if (!(prefixOriginUrl == prefixTinyurl || prefixOriginUrl.equals(prefixTinyurl))) {
            return null;
        }

        return originUrlStr;
    }

}
