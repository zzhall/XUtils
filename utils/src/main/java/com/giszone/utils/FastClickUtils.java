package com.giszone.utils;

import android.util.Log;


public class FastClickUtils {

    private static long lastClickTime = 0;//上次点击的时间

    public static boolean isFastClick() {
        long currentTime = System.currentTimeMillis();//当前系统时间
        Log.d(XUtils.TAG, "FastClickUtils.isFastClick: " + currentTime);
        boolean isAllowClick = currentTime - lastClickTime <= 500;
        lastClickTime = currentTime;
        return isAllowClick;
    }

}
