package com.zhang.permission.bean;

import android.content.Context;

import com.zhang.permission.annotation.NeedPermission;
import com.zhang.permission.aspect.PermissionBaseAspect;

import java.util.List;

/**
 * Author: zhang_quan
 * Email:  zhang_quan_888@163.com
 * Date:   2019/3/2
 */
public class PermissionCanceledBean extends PermissionBaseBean {
    private List<String> mCancelList;

    public PermissionCanceledBean(Context context, PermissionBaseAspect aspect, NeedPermission needPermission, List<String> cancelList) {
        super(context, aspect, needPermission);
        mCancelList = cancelList;
    }

    public void againRequest() {
        getAspect().requestPermission();
    }

    public int getRequestCode() {
        return getNeedPermission().requestCode();
    }

    public List<String> getCancelList() {
        return mCancelList;
    }
}
