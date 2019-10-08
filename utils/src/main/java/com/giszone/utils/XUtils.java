package com.giszone.utils;

import android.content.Context;

public class XUtils {

    public static final String TAG = "MainTAG";

    protected static Context context;

    public static Context getContext() {
        return context.getApplicationContext();
    }

    public static synchronized void init(Context context) {
        XUtils.context = context;
    }

}
