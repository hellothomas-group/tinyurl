package com.hellothomas.tinyurl.common.utils;

import com.hellothomas.tinyurl.infrastructure.exception.MyException;

import java.net.MalformedURLException;
import java.net.URL;

import static com.hellothomas.tinyurl.common.enums.ErrorCodeEnum.URL_FORMAT_ERROR;

/**
 * @ClassName UrlUtil
 * @Author Thomas
 * @Date 2019/7/8 22:53
 * @Description Url工具类
 * @Version 1.0
 */
public class UrlUtil {

    private static final String URL_CMBLIFE_PREFIX = "cmblife://";

    private UrlUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @Author 80234613
     * @Date 2019-7-9 8:43
     * @Descripton 字符Url转换为URL对象
     * @param urlStr
     * @Return java.net.URL
     */
    public static URL parse(String urlStr) {
        URL url;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            return null;
        }
        if (url.getProtocol().equals("") || url.getHost().equals("")) {
            return null;
        }
        return url;
    }

    public static void checkUrl(String urlStr) {
        // cmb app协议
        if (urlStr.startsWith(URL_CMBLIFE_PREFIX)) {
            return;
        }
        URL tinyURL = UrlUtil.parse(urlStr);
        if (tinyURL == null) {
            throw new MyException(URL_FORMAT_ERROR);
        }
    }
}
