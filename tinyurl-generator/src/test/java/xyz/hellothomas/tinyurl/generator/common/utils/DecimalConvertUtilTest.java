package xyz.hellothomas.tinyurl.generator.common.utils;

import org.junit.Test;

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
}