package com.zhang.permission.setting.support;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.zhang.permission.setting.ISetting;

/**
 * Author: zhang_quan
 * Email:  zhang_quan_888@163.com
 * Date:   2019/3/2
 */

public class HuaWei implements ISetting {
    private Context mContext;

    public HuaWei(Context context) {
        mContext = context;
    }

    @Override
    public Intent getSetting() {
        Intent intent = new Intent(mContext.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(comp);
        return intent;
    }
}
