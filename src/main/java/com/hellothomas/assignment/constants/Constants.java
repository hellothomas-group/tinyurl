package com.hellothomas.assignment.constants;

/**
 * @className Constants
 * @author Thomas
 * @date 2020/10/30 20:40
 * @description
 * @version 1.0
 */
public class Constants {

    private Constants() {
        throw new IllegalStateException("Constants class");
    }

    public static final String EMPTY_STRING = "";
    public static final String PROXY_PATH = "/proxy";
    public static final String ORIGIN_URL_MD5_KEY_PREFIX = "OriginUrlMd5.";
    public static final String ID_ENCODE_PREFIX = "IdEncode.";
    public static final String DEFAULT_HOST_NAME = "UNKNOWN";
    public static final String DEFAULT_HOST_ADDRESS = "UNKNOWN";
}
