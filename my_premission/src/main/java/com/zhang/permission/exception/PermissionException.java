package com.zhang.permission.exception;

import android.annotation.TargetApi;
import android.os.Build;

/**
 * author:  zhouchaoxiang
 * date:    2019/9/29
 * explain:
 */
public class PermissionException extends RuntimeException {
    public PermissionException() {
    }

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionException(Throwable cause) {
        super(cause);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public PermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
