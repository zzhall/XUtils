package com.giszone.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getName();

    private static ConnectivityManager getConnManager() {
        return (ConnectivityManager) XUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 注册网络状态监听
     *
     * @param networkCallback
     */
    public static void regNetworkChangeListener(ConnectivityManager.NetworkCallback networkCallback) {
        ConnectivityManager connManager = getConnManager();
        if (connManager != null)
            connManager.requestNetwork(new NetworkRequest.Builder().build(), networkCallback);
        else
            Log.d(TAG, "注册网络状态改变监听失败");
    }

    // 移除网络状态监听
    public static void removeNetworkChangeListener(ConnectivityManager.NetworkCallback networkCallback) {
        ConnectivityManager connManager = getConnManager();
        if (connManager != null)
            connManager.unregisterNetworkCallback(networkCallback);
        else
            Log.d(TAG, "取消网络状态改变监听失败");
    }

    /**
     * 网络是否可用
     *
     * @return
     */
    public static boolean isAvailable() {
        ConnectivityManager connManager = getConnManager();
        if (connManager != null) {
            NetworkInfo info = connManager.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取网络连接类型名
     *
     * @return
     */
    public static String getNetworkTypeName() {
        ConnectivityManager connManager = getConnManager();
        if (connManager != null) {
            NetworkInfo info = connManager.getActiveNetworkInfo();
            if (info != null) {
                return info.getTypeName();
            }
        }
        return "网络连接异常";
    }

    /**
     * 获取网络类型 ,返回-1为无网络
     *
     * @return
     */
    public static int getNetworkType() {
        ConnectivityManager connManager = getConnManager();
        if (connManager != null) {
            NetworkInfo info = connManager.getActiveNetworkInfo();
            if (info != null) {
                return info.getType();
            }
        }
        return -1;
    }

    public static String getIpAddress() {
        // 获取外网IP地址
        try {
            Response response = new OkHttpClient().newCall(new Request.Builder().url("http://www.3322.org/dyndns/getip").build()).execute();
            if (response.body() != null) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getLocalIp();
    }

    public static String getLocalIp() {
        ConnectivityManager connectivityManager = (ConnectivityManager) XUtils.getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            // 3/4g网络
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                //  wifi网络
                WifiManager wifiManager = (WifiManager) XUtils.getContext().getApplicationContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = Objects.requireNonNull(wifiManager).getConnectionInfo();
                int ip = wifiInfo.getIpAddress();
                return String.format(Locale.CHINA, "%d.%d.%d.%d", ip & 0xFF, (ip >> 8) & 0xFF, (ip >> 16) & 0xFF, ip >> 24 & 0xFF);
            } else if (info.getType() == ConnectivityManager.TYPE_ETHERNET) {
                // 有限网络
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface networkInterface = en.nextElement();
                        Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                        while (inetAddresses.hasMoreElements()) {
                            InetAddress inetAddress = inetAddresses.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
        }
        return "0.0.0.0";
    }
}
