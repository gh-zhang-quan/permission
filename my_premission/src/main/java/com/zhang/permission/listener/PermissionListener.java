package com.zhang.permission.listener;

import com.zhang.permission.bean.PermissionCanceledBean;
import com.zhang.permission.bean.PermissionDeniedBean;

/**
 * Author: zhang_quan
 * Email:  zhang_quan_888@163.com
 * Date:   2019/3/2
 */
public  interface PermissionListener  {
    void onPermissionGranted();

    void onPermissionCanceled(PermissionCanceledBean bean);

    void onPermissionDenied(PermissionDeniedBean bean);
}
