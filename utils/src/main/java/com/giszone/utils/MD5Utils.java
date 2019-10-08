package com.giszone.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {


    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes(StandardCharsets.UTF_8));
            return HexUtils.toHexString(bytes).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取设备AGI机器码
     *
     * @return
     */
    public static String getMachineCode(Context context, String salt) {
        String machineCode = getDeviceId(context) + getSerial() + salt;
        return md5(machineCode);
    }


    /**
     * 获取IMEI设备识别码
     *
     * @return
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    private static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return "";
        } else {
            return telephonyManager.getDeviceId();
        }
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    private static String getSerial() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return Build.SERIAL;
        } else {
            return Build.getSerial();
        }
    }

}
