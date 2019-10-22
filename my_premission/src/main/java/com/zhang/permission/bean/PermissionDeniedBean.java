package com.zhang.permission.bean;

import android.content.Context;

import com.zhang.permission.annotation.NeedPermission;
import com.zhang.permission.aspect.PermissionBaseAspect;
import com.zhang.permission.util.SettingUtils;

import java.util.List;

/**
 * Author: zhang_quan
 * Email:  zhang_quan_888@163.com
 * Date:   2019/3/2
 */
public class PermissionDeniedBean extends PermissionBaseBean {

    private List<String> mCancelList;
    private List<String> mDeniedList;

    public PermissionDeniedBean(Context context, PermissionBaseAspect aspect, NeedPermission needPermission, List<String> cancelList, List<String> deniedList) {
        super(context, aspect, needPermission);
        mCancelList = cancelList;
        mDeniedList = deniedList;
    }

    public void againRequest() {
        getAspect().requestPermission();
    }

    public void toSettingActivity() {
        SettingUtils.go2Setting(getContext());
    }

    public int getRequestCode() {
        return getNeedPermission().requestCode();
    }

    public List<String> getCancelList() {
        return mCancelList;
    }

    public List<String> getDeniedList() {
        return mDeniedList;
    }
}
