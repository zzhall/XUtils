package com.giszone.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.annotation.NonNull;

public class TimeUtils {

    public static final long MIN_MILLIS = 60000L;
    public static final long HOUR_MILLIS = 60 * MIN_MILLIS;
    public static final long DAY_MILLIS = 24 * HOUR_MILLIS;
    public static final long MONTH_MILLIS = 31 * DAY_MILLIS;
    public static final long YEAR_MILLIS = 365 * DAY_MILLIS;


    public static String getTimeString(@NonNull String pattern, long timeMillis) {
        if (timeMillis == 0L) {
            return "-";
        } else {
            return new SimpleDateFormat(pattern, Locale.CHINA).format(timeMillis);
        }
    }

    public static String getTimeStringYMD(long timeMillis) {
        return getTimeString("yyyy/MM/dd", timeMillis);
    }

    public static String getCNTimeStringYMD(long timeMillis) {
        return getTimeString("yyyy年MM月dd日", timeMillis);
    }

    public static String getTimeStringYMDHM(long timeMillis) {
        return getTimeString("yyyy/MM/dd HH:mm", timeMillis);
    }

    public static String getTimeStringHM(long timeMillis) {
        return getTimeString("HH:mm", timeMillis);
    }

    public static String getTimeStringYMDHMS(long timeMillis) {
        return getTimeString("yyyy/MM/dd HH:mm:ss", timeMillis);
    }

    public static String getTimeStringForFile(long timeMillis) {
        return getTimeString("yyyyMMddHHmmssSSS", timeMillis);
    }

    /**
     * to long type time millis
     *
     * @param timeString like "20180101"
     * @return
     */
    public static long getTimeMillis(String timeString) {
        try {
            return new SimpleDateFormat("yyyyMMdd", Locale.CHINA)
                    .parse(timeString)
                    .getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 解析有效期，截至当天23:59:59
     *
     * @param timeString 类似“2019/06/27”
     * @return
     */
    public static long parseExpireTime(String timeString) {
        try {
            return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA)
                    .parse(timeString + " 23:59:59")
                    .getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static String flyTimeTotalFormat(long millisecond) {
        if (millisecond <= 0L) {
            return "0";
        } else if (millisecond < MIN_MILLIS) {
            return String.format(Locale.CHINA, "%d秒", millisecond / 1000);
        } else if (millisecond < HOUR_MILLIS) {
            return String.format(Locale.CHINA, "%.1f分钟", millisecond / 60000.0);
        } else {
            return String.format(Locale.CHINA, "%.2f小时", millisecond / 3600000.0);
        }
    }
}
