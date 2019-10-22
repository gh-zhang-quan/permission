package com.zhang.permission.bean;

import android.content.Context;

import com.zhang.permission.annotation.NeedPermission;
import com.zhang.permission.aspect.PermissionBaseAspect;

/**
 * Author: zhang_quan
 * Email:  zhang_quan_888@163.com
 * Date:   2019/3/2
 */
public class PermissionBeforeBean extends PermissionBaseBean {

    public PermissionBeforeBean(Context context, PermissionBaseAspect aspect, NeedPermission needPermission) {
        super(context, aspect, needPermission);
    }

    public void proceed(boolean isRequest) {
        getAspect().proceed(isRequest);
    }

}
