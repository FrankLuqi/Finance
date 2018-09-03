package com.example.finance.common;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.finance.utils.UIUtils;

import butterknife.internal.Utils;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018.8.24.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler{

    private static CrashHandler crashHandler = null;

    private CrashHandler()
    {

    }

    public static CrashHandler getInstance()
    {
        if (crashHandler == null)
        {
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    private Context context;

    public void init(Context context)
    {
        //将CrashHandler设为默认的异常处理
        this.context = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        handleException(thread,throwable);
    }

    /**
     * 判断该异常是否需要自己去处理
     * @param ex
     * @return
     */

    public boolean isHandle(Throwable ex)
    {
        if (ex == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 自定义异常处理
     * @param thread
     * @param ex
     */

    private void handleException(Thread thread, Throwable ex)
    {
        new Thread()
        {
            @Override
            public void run() {
                //android只有主线程中预先初始化了looper除主线程之外要想对UI进行操作需要对looper进行操作
                Looper.prepare();
                UIUtils.Toast("抱歉，系统出现异常，即将关闭",false);
                Looper.loop();
            }
        }.start();
        collectionException(ex);
        try {
            Thread.sleep(2000);
            AppManager.getInstance().removeAll();
            //关闭当前进程
            android.os.Process.killProcess(android.os.Process.myPid());
            //关闭虚拟机，释放所有内存
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 收集崩溃异常信息（可以在这里将消息发到后台数据库
     * @param ex
     */
    private void collectionException(Throwable ex) {
        final String deviceInfo = Build.DEVICE+Build.VERSION.SDK_INT + Build.MODEL + Build.PRODUCT;
        final String errorInfo = ex.getMessage();
        new Thread()
        {
            @Override
            public void run() {
                Log.e(TAG, "running error 运行时错误: " +errorInfo);
            }
        }.start();
    }

}
