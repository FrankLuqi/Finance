package com.example.finance.common;

import android.app.Application;
import android.content.Context;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Administrator on 2018.8.18.
 */

public class MyApplication extends Application{

    public static Context context = null;

    public static android.os.Handler handler = null;

    public static Thread mainThread = null;

    public static int mainThreadId = 0;//主线程id

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();//获取当前上下文
        handler = new android.os.Handler();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();
        //实例化全局异常捕获系统
        CrashHandler crashHandler = CrashHandler.getInstance();
        //初始化全局异常捕获
        crashHandler.init(context);
    }

}
