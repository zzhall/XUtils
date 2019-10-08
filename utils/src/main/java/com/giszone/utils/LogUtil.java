package com.giszone.utils;

import android.text.TextUtils;
import android.util.Log;

public class LogUtil {
    /**
     * 截断输出日志
     *
     * @param msg
     */
    public static void logd(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        int segmentSize = 3 * 1024;
        long length = msg.length();

        // 长度小于等于限制直接打印
        if (length <= segmentSize) {
            Log.d(XUtils.TAG, msg);
        } else {
            while (msg.length() > segmentSize) {
                // 循环分段打印日志
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                Log.d(XUtils.TAG, "-------------------" + logContent);
            }
            // 打印剩余日志
            Log.d(XUtils.TAG, "-------------------" + msg);
        }
    }

}