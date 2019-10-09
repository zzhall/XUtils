package com.giszone.utils;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.StringRes;


/**
 * Created by zzh on 2/28/17.
 */

public class ToastUtils {

    private static Toast sToast;
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void setResultToToast(final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void setResultToToast(@StringRes final int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, Toast.LENGTH_SHORT);
            }
        });
    }

    public static void setResultToToast(final CharSequence text, final int duration) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(text, duration);
            }
        });
    }

    public static void setResultToToast(@StringRes final int resId, final int duration) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(resId, duration);
            }
        });
    }

    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     */
    private static void showToast(CharSequence text, int duration) {
        if (sToast == null || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            sToast = Toast.makeText(XUtils.getContext(), text, duration);
        } else {
            sToast.setText(text);
            sToast.setDuration(duration);
        }
        sToast.show();
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private static void showToast(@StringRes final int resId, int duration) {
        showToast(XUtils.getContext().getResources().getText(resId).toString(), duration);
    }


}
