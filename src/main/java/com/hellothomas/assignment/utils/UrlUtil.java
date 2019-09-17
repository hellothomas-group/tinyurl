package com.hellothomas.assignment.utils;

import com.hellothomas.assignment.exception.MyException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName UrlUtil
 * @Author Thomas
 * @Date 2019/7/8 22:53
 * @Description Url工具类
 * @Version 1.0
 */
public class UrlUtil {

    /**
     * @Author 80234613
     * @Date 2019-7-9 8:43
     * @Descripton 字符Url转换为URL对象
     * @param urlStr
     * @Return java.net.URL
     */
    public static URL parse(String urlStr) {
        URL url = null;
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
}
