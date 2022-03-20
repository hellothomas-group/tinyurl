package xyz.hellothomas.tinyurl.generator.common.utils;

import xyz.hellothomas.tinyurl.common.infrastructure.exception.MyException;

import java.net.MalformedURLException;
import java.net.URL;

import static xyz.hellothomas.tinyurl.generator.common.enums.GeneratorErrorCodeEnum.URL_FORMAT_ERROR;

/**
 * @author Thomas
 * @date 2022/3/19 23:49
 * @description Url工具类
 * @version 1.0
 */
public class UrlUtil {

    private UrlUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 符Url转换为URL对象
     *
     * @param urlStr
     * @return
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
        URL tinyURL = UrlUtil.parse(urlStr);
        if (tinyURL == null) {
            throw new MyException(URL_FORMAT_ERROR);
        }
    }
}
