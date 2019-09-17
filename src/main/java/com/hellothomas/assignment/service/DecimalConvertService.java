package com.hellothomas.assignment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(DecimalConvertService.class);

    private final char[] array;

    private Map<Character, Integer> charValueMap = new HashMap<Character, Integer>();

    public DecimalConvertService() {
        array  = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                            'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
                            'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm',
                            'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D',
                            'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};
        charValueMap = new HashMap<Character, Integer>();
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
