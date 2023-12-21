package com.example.myapplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;

public class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("需要实例化");
    }

    //初始化context
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    //获取应用的Context
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("上下文为空");
    }
}
