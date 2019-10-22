package com.zhang.permission;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Author: zhang_quan
 * Email:  zhang_quan_888@163.com
 * Date:   2019/3/2
 */
public class MyPermission {
    private Context mContext;
    private Object mConfigObject;

    private MyPermission() {
    }

    public static MyPermission getInstance() {
        return Instance.instance;
    }

    private static class Instance {
        @SuppressLint("StaticFieldLeak")
        private static MyPermission instance = new MyPermission();
    }

    public void init(Context context, Object configObject) {
        mContext = context;
        mConfigObject = configObject;
    }

    public Context getContext() {
        return mContext;
    }

    public Object getConfigObject() {
        return mConfigObject;
    }

    public static void register(Object obj) {

    }

    public static void onRegister(Object obj) {

    }
}
