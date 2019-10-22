package com.zhang.permission.bean;

import android.content.Context;

import com.zhang.permission.annotation.NeedPermission;
import com.zhang.permission.aspect.PermissionBaseAspect;


/**
 * Author: zhang_quan
 * Email:  zhang_quan_888@163.com
 * Date:   2019/3/2
 */
public class PermissionBaseBean {
    private Context mContext;
    private PermissionBaseAspect mAspect;
    private NeedPermission mNeedPermission;

    public PermissionBaseBean(Context context, PermissionBaseAspect aspect, NeedPermission needPermission) {
        mContext = context;
        mAspect = aspect;
        mNeedPermission = needPermission;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public PermissionBaseAspect getAspect() {
        return mAspect;
    }

    public void setAspect(PermissionBaseAspect aspect) {
        mAspect = aspect;
    }

    public NeedPermission getNeedPermission() {
        return mNeedPermission;
    }

    public void setNeedPermission(NeedPermission needPermission) {
        mNeedPermission = needPermission;
    }

    public String[] getPermissions() {
        return mNeedPermission.value();
    }
}
