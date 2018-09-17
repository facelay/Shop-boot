package com.imooc.sell.utils;

public class MathUtil {

    private static final double DT = 0.01;

    public static boolean equals(Double a, Double b) {
        if (DT < Math.abs(a - b)) {
            return true;
        } else {
            return false;
        }

    }

}
