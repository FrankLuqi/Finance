package com.example.finance.common;

import android.app.Activity;

import java.util.Stack;

/**
 * 创建一个活动管理器，使用单例模式
 * Created by Administrator on 2018.8.23.
 */

public class AppManager {

    //创建活动栈
    private Stack<Activity> activityStack = new Stack<>();

    //创建自身的实例
    public static AppManager appManager = null;

    //先对构造方法私有化
    private AppManager()
    {

    }

    public static AppManager getInstance()
    {
        if (appManager == null)
            appManager = new AppManager();
        return appManager;
    }

    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }


    public void removeActivity(Activity activity) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity1 = activityStack.get(i);
            if (activity1.getClass().equals(activity.getClass())) {
                activity1.finish();
                activityStack.remove(activity1);
                break;
            }
        }
    }

    public void removeCurrent() {
        Activity lastElement = activityStack.lastElement();
        lastElement.finish();
        activityStack.remove(lastElement);
    }

    public void removeAll() {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity1 = activityStack.get(i);
            activity1.finish();
            activityStack.remove(activity1);
        }
    }

    public int getSize()
    {
        return activityStack.size();
    }
}
