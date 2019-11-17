package com.csk.mmall.util;

/**
 * @description:
 * @author: caishengkai
 * @time: 2019/11/17 15:25
 */
public class BigDecimalUtil {

    public static java.math.BigDecimal add(double v1, double v2){
        java.math.BigDecimal b1 = new java.math.BigDecimal(Double.toString(v1));
        java.math.BigDecimal b2 = new java.math.BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    public static java.math.BigDecimal sub(double v1, double v2){
        java.math.BigDecimal b1 = new java.math.BigDecimal(Double.toString(v1));
        java.math.BigDecimal b2 = new java.math.BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }


    public static java.math.BigDecimal mul(double v1, double v2){
        java.math.BigDecimal b1 = new java.math.BigDecimal(Double.toString(v1));
        java.math.BigDecimal b2 = new java.math.BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    public static java.math.BigDecimal div(double v1, double v2){
        java.math.BigDecimal b1 = new java.math.BigDecimal(Double.toString(v1));
        java.math.BigDecimal b2 = new java.math.BigDecimal(Double.toString(v2));
        return b1.divide(b2,2, java.math.BigDecimal.ROUND_HALF_UP);//四舍五入,保留2位小数
        //除不尽的情况
    }
}
