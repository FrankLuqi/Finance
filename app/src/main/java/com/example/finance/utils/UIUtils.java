package com.example.finance.utils;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.finance.R;
import com.example.finance.common.MyApplication;

/**
 * Created by Administrator on 2018.8.18.
 */

public class UIUtils {

    public static Context getContext()
    {
        return MyApplication.context;
    }

    public static Handler getHandler()
    {
        return MyApplication.handler;
    }

    /**
     * 设置碎片所使用的layout
     * @param layoutId
     * @return
     */
    public static View getXmlView(int layoutId)
    {
        return View.inflate(getContext(), layoutId,null);
    }


    /**
     * 将dp根据屏幕分辨率转化为px
     * +0.5的原因是减少float转int造成的误差
     * @param dp
     * @return
     */
    public static int dpTOpx(int dp)
    {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int)(dp*density+0.5);
    }

    /**
     * 因为Android中的子线程不安全所以所有更新UI的操作都要在主线程中进行
     * @param runnable
     */
    public static void RunOnUIThread(Runnable runnable)
    {
        if (isMainThread())
            runnable.run();
        else
            getHandler().post(runnable);
    }

    /**
     * 判断当前线程是否是主线程
     * @return
     */
    private static boolean isMainThread()
    {
        int tid = android.os.Process.myTid();
        if (tid == MyApplication.mainThreadId)
            return true;
        return false;
    }

    public static void Toast(String text,boolean islong)
    {
        Toast.makeText(getContext(),text,islong==true? Toast.LENGTH_LONG:Toast.LENGTH_SHORT).show();
    }

    public static String[] getStringArray(int arrId)
    {
        return getContext().getResources().getStringArray(arrId);
    }

}
