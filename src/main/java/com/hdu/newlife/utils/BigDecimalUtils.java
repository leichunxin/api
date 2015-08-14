package com.hdu.newlife.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BigDecimalUtils {

    // 进行除法运算
    public static double div(double d1, double d2, int len) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    // 进行加法运算
    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).doubleValue();
    }

    // 进行减法运算
    public static double sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2).doubleValue();
    }

    // 进行乘法运算
    public static double mul(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).doubleValue();
    }
    
    //格式化数据，保留一位小数
    public static double format(double d) {
		BigDecimal b = new BigDecimal(d);  
		double f = b.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();  
        return f;
    }
    
    //格式化数据，转换成百分数
    public static String percentFormat(double d) {
		DecimalFormat format = new DecimalFormat("0.0%");
		String s = format.format(d);  
        return s;
    }

}
