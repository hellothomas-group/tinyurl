package com.hellothomas.assignment.applicaton;

import com.hellothomas.assignment.infrastructure.exception.MyException;
import com.hellothomas.assignment.common.utils.UrlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.net.URL;

import static com.hellothomas.assignment.common.Constants.PROXY_PATH;
import static com.hellothomas.assignment.common.enums.ErrorCodeEnum.URL_FORMAT_ERROR;

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

    public TinyUrlService(UniqueSeqService uniqueSeqService) {
        this.uniqueSeqService = uniqueSeqService;

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
        return uniqueSeqService.seqEncodeConvertToOriginUrl(seqEncode);
    }

    private void checkUrl(String urlStr) {
        URL tinyURL = UrlUtil.parse(urlStr);
        if (tinyURL == null) {
            throw new MyException(URL_FORMAT_ERROR);
        }
    }

}
