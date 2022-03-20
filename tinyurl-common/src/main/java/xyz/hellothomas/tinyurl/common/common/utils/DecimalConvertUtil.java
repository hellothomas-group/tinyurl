package xyz.hellothomas.tinyurl.common.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas
 * @date 2022/3/19 23:48
 * @description 进制转换工具
 * @version 1.0
 */
public class DecimalConvertUtil {
    private static final int DECIMAL_LENGTH = 6;
    private static final String[] DIGIT_ARRAY = new String[]{
            // 最右开始，第一位
            "c2PLkTFslnwYJOCB6Zfv9oXEajpV4mNDWhuIrqKHt7eb5dQy3MS0RziU8xAgG1",
            // 最右开始，第二位
            "2PLkTFslnwYJOCB6Zfv9oXEajpV4mNDWhuIrqKHt7eb5dQy3MS0RziU8xAgG1c",
            // 最右开始，第三位
            "PLkTFslnwYJOCB6Zfv9oXEajpV4mNDWhuIrqKHt7eb5dQy3MS0RziU8xAgG1c2",
            // 最右开始，第四位
            "LkTFslnwYJOCB6Zfv9oXEajpV4mNDWhuIrqKHt7eb5dQy3MS0RziU8xAgG1c2P",
            // 最右开始，第五位
            "kTFslnwYJOCB6Zfv9oXEajpV4mNDWhuIrqKHt7eb5dQy3MS0RziU8xAgG1c2PL",
            // 最右开始，第六位
            "TFslnwYJOCB6Zfv9oXEajpV4mNDWhuIrqKHt7eb5dQy3MS0RziU8xAgG1c2PLk"};
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
     * 数字转换为进制字符串
     *
     * @param number
     * @param decimal
     * @return
     */
    public static String numberConvertToDecimal(long number, int decimal) {
        return numberConvertToDecimal(number, decimal, DECIMAL_LENGTH);
    }

    /**
     * 数字转换为进制字符串
     *
     * @param number
     * @param decimal
     * @param decimalMinLength 进制字符串最小长度
     * @return
     */
    public static String numberConvertToDecimal(long number, int decimal, int decimalMinLength) {
        StringBuilder sb = new StringBuilder();
        // 初始为个位
        int digit = 0;
        while (number != 0) {
            int digitDecimal = (int) (number - (number / decimal) * decimal);
            sb.append(DIGIT_ARRAY_LIST.get(digit)[digitDecimal]);
            number /= decimal;
            digit++;
        }

        for (int i = digit; i < decimalMinLength; i++) {
            sb.append(DIGIT_ARRAY_LIST.get(i)[0]);
        }
        return sb.reverse().toString();
    }

    /**
     * 进制字符串转换为数字
     *
     * @param decimalStr
     * @param decimal
     * @return
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
