package xyz.hellothomas.tinyurl.common.common.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class DecimalConvertUtilTest {

    @Test
    public void numberConvertToDecimal() {
        System.out.println(DecimalConvertUtil.numberConvertToDecimal(1, 62));
        System.out.println(DecimalConvertUtil.numberConvertToDecimal(10, 62));
        System.out.println(DecimalConvertUtil.numberConvertToDecimal(62, 62));
        System.out.println(DecimalConvertUtil.numberConvertToDecimal(100, 62));
        System.out.println(DecimalConvertUtil.numberConvertToDecimal(62 * 62, 62));
        System.out.println(DecimalConvertUtil.numberConvertToDecimal(62 * 62 * 62, 62));
        System.out.println(DecimalConvertUtil.numberConvertToDecimal(62 * 62 * 62 * 62, 62));
        System.out.println(DecimalConvertUtil.numberConvertToDecimal(62 * 62 * 62 * 62 * 62, 62));
        long number = 62 * 62 * 62 * 62 * 62 * 62l - 1;
        System.out.println(DecimalConvertUtil.numberConvertToDecimal(number, 62));

    }

    @Test
    public void decimalConvertToNumber() {
        System.out.println(DecimalConvertUtil.decimalConvertToNumber("OqlCBB", 62));
        System.out.println(DecimalConvertUtil.decimalConvertToNumber("OqlCBb", 62));
        System.out.println(DecimalConvertUtil.decimalConvertToNumber("OqlCCL", 62));
        System.out.println(DecimalConvertUtil.decimalConvertToNumber("OqlCCK", 62));
        System.out.println(DecimalConvertUtil.decimalConvertToNumber("OqllBL", 62));
        System.out.println(DecimalConvertUtil.decimalConvertToNumber("OqqCBL", 62));
        System.out.println(DecimalConvertUtil.decimalConvertToNumber("OOlCBL", 62));
        System.out.println(DecimalConvertUtil.decimalConvertToNumber("4qlCBL", 62));
        System.out.println(DecimalConvertUtil.decimalConvertToNumber("qlCBLp", 62));
    }

    @Test
    public void randomDecimalString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        String test = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int[] intArray = new int[test.length()];
        Arrays.fill(intArray, -1);
        for (int i = 0; i < test.length(); i++) {
            while (true) {
                int temp = random.nextInt(test.length());
                if (!isExist(intArray, i, temp)) {
                    intArray[i] = temp;
                    break;
                }
            }
            sb.append(test.charAt(intArray[i]));
        }
        System.out.println(sb);
    }

    private boolean isExist(int[] intArray, int i, int temp) {
        for (int j = 0; j < i; j++) {
            if (temp == intArray[j]) {
                return true;
            }
        }

        return false;
    }
}