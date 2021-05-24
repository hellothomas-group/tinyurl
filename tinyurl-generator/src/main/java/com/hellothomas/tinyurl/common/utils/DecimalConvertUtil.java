package com.hellothomas.tinyurl.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DecimalConvertService
 * @Author 80234613
 * @Date 2019-7-4 13:31
 * @Descripton 进制转换工具
 * @Version 1.0
 */
public class DecimalConvertUtil {
    private static final int DECIMAL_LENGTH = 6;
    private static final String[] DIGIT_ARRAY = new String[]{
            // 最右开始，第一位
            "LBClqO43AIbTMDy8G1eVZmF0hutRkzXdQ6wNfiKEJaxWHPSg57rj9nosY2vUcp",
            // 最右开始，第二位
            "BClqO43AIbTMDy8G1eVZmF0hutRkzXdQ6wNfiKEJaxWHPSg57rj9nosY2vUcpL",
            // 最右开始，第三位
            "ClqO43AIbTMDy8G1eVZmF0hutRkzXdQ6wNfiKEJaxWHPSg57rj9nosY2vUcpLB",
            // 最右开始，第四位
            "lqO43AIbTMDy8G1eVZmF0hutRkzXdQ6wNfiKEJaxWHPSg57rj9nosY2vUcpLBC",
            // 最右开始，第五位
            "qO43AIbTMDy8G1eVZmF0hutRkzXdQ6wNfiKEJaxWHPSg57rj9nosY2vUcpLBCl",
            // 最右开始，第六位
            "O43AIbTMDy8G1eVZmF0hutRkzXdQ6wNfiKEJaxWHPSg57rj9nosY2vUcpLBClq"};
    /**
     * 各位的编码array(个位.百位...)
     */
    private static final List<char[]> DIGIT_ARRAY_LIST = new ArrayList<>();

    /**
     * 各位的编码map(个位.百位...)
     */
    private static final List<Map<Character, Integer>> DIGIT_MAP_LIST = new ArrayList<>();

    static {
        for (int i = 0; i < DECIMAL_LENGTH; i++) {
            char[] array = DIGIT_ARRAY[i].toCharArray();
            Map<Character, Integer> charValueMap = new HashMap(64);
            for (int j = 0; j < array.length; j++) {
                charValueMap.put(array[j], j);
            }
            DIGIT_MAP_LIST.add(charValueMap);
            DIGIT_ARRAY_LIST.add(array);
        }
    }

    private DecimalConvertUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @Author 80234613
     * @Date 2019-7-7 11:26
     * @Descripton 数字转换为进制字符串，仅支持2到62进制
     * @param number
     * @param decimal
     * @Return java.lang.String
     */
    public static String numberConvertToDecimal(long number, int decimal) {
        StringBuilder sb = new StringBuilder();
        // 初始为个位
        int digit = 0;
        while (number != 0) {
            int digitDecimal = (int) (number - (number / decimal) * decimal);
            sb.append(DIGIT_ARRAY_LIST.get(digit)[digitDecimal]);
            number /= decimal;
            digit++;
        }

        for (int i = digit; i < DECIMAL_LENGTH; i++) {
            sb.append(DIGIT_ARRAY_LIST.get(i)[0]);
        }
        return sb.reverse().toString();
    }

    /**
     * @Author 80234613
     * @Date 2019-7-7 11:34
     * @Descripton 进制字符串转换为数字
     * @param decimalStr
     * @param decimal
     * @Return long
     */
    public static long decimalConvertToNumber(String decimalStr, int decimal) {
        long sum = 0;
        long multiple = 1;
        char[] chars = decimalStr.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            char ch = chars[i];
            sum += DIGIT_MAP_LIST.get(chars.length - 1 - i).get(ch) * multiple;
            multiple *= decimal;
        }
        return sum;
    }


}
