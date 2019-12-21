package com.panrongda.qingweather.util;

import com.blankj.utilcode.util.TimeUtils;

/**
 * Create by Dada on 2019/12/21 10:33.
 */
public class TimeUtil {

    private TimeUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断传入时间是否超过传入的小时数
     */
    public static boolean timeBigLittleHour(String date, int hour) {
        long millis1 = TimeUtils.string2Millis(date);
        long millis2 = System.currentTimeMillis();

        return Math.abs(millis1 - millis2) > hour * 60 * 60 * 1000;
    }
}
