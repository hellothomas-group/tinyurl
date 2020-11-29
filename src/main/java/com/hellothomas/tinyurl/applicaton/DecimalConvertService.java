package com.hellothomas.tinyurl.applicaton;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DecimalConvertService
 * @Author 80234613
 * @Date 2019-7-4 13:31
 * @Descripton 进制转换Service
 * @Version 1.0
 */
@Service
public class DecimalConvertService {
    public static final int DECIMAL_LENGTH = 6;
    private final char[] array;

    private Map<Character, Integer> charValueMap;

    public DecimalConvertService() {
        array = "LBClqO43AIbTMDy8G1eVZmF0hutRkzXdQ6wNfiKEJaxWHPSg57rj9nosY2vUcp".toCharArray();
        charValueMap = new HashMap(64);
        for (int i = 0; i < array.length; i++) {
            charValueMap.put(array[i], i);
        }
    }

    /**
     * @Author 80234613
     * @Date 2019-7-7 11:26
     * @Descripton 数字转换为进制字符串，仅支持2到62进制
     * @param number
     * @param decimal
     * @Return java.lang.String
     */
    public String numberConvertToDecimal(long number, int decimal) {
        StringBuilder sb = new StringBuilder();
        while (number != 0) {
            sb.append(array[(int) (number - (number / decimal) * decimal)]);
            number /= decimal;
        }

        int fillLength = DECIMAL_LENGTH - sb.length();
        for (int i = 0; i < fillLength; i++) {
            sb.append(array[0]);
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
    public long decimalConvertToNumber(String decimalStr, int decimal) {
        long sum = 0;
        long multiple = 1;
        char[] chars = decimalStr.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            char ch = chars[i];
            sum += charValueMap.get(ch) * multiple;
            multiple *= decimal;
        }
        return sum;
    }


}
