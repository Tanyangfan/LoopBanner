package com.mushi.util;

import android.util.Log;

public class LogUtils {
    private static final String TAG = "mushi ";

    public static void d(String tag, String log) {
        Log.d(tag, log);
    }

    public static void d(String log) {
        Log.d(TAG, log);
    }

    public static void e(String log) {
        Log.e(TAG, log);
    }

    public static void e(String tag, String log) {
        Log.e(tag, log);
    }

}
