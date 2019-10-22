package com.zhang.permission.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;

import com.zhang.permission.MyPermission;
import com.zhang.permission.activity.PermissionRequestActivity;
import com.zhang.permission.listener.PermissionListener;


/**
 * Author: zhang_quan
 * Email:  zhang_quan_888@163.com
 * Date:   2019/3/2
 */
public class PermissionUtils {

    /**
     * 检查是否都赋予权限
     *
     * @param grantResults grantResults
     * @return 所有都同意返回true 否则返回false
     */
    public static boolean verifyPermissions(int... grantResults) {
        if (grantResults.length == 0) return true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public static boolean checkPermission(String permission) {
        return checkPermission(MyPermission.getInstance().getContext(), permission);

    }

    public static boolean checkPermissions(Context context, String... permissions) {
        if (permissions == null || permissions.length == 0) {
            throw new NullPointerException("The permission requested is empty");
        }
        for (String permission : permissions) {
            if (!checkPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkPermission(Context context, String permission) {
        return context.checkPermission(permission, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions(Context context,String[] permissions, int requestCode, PermissionListener listener) {
        if (permissions == null || permissions.length == 0) {
            throw new NullPointerException("The permission requested is empty");
        }
        PermissionRequestActivity.start(context, permissions, requestCode, listener);
    }

}
