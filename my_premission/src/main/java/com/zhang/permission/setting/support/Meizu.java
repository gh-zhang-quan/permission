package com.zhang.permission.setting.support;

import android.content.Context;
import android.content.Intent;

import com.zhang.permission.setting.ISetting;

/**
 * Author: zhang_quan
 * Email:  zhang_quan_888@163.com
 * Date:   2019/3/2
 */
public class Meizu  implements ISetting {
    private Context mContext;

    public Meizu(Context context) {
        mContext = context;
    }

    @Override
    public Intent getSetting() {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", mContext.getPackageName());
        return intent;
    }
}
