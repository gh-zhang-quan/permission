package com.zhang.permission.aspect;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.zhang.permission.annotation.NeedPermission;
import com.zhang.permission.annotation.PermissionBefore;
import com.zhang.permission.annotation.PermissionCanceled;
import com.zhang.permission.annotation.PermissionDenied;
import com.zhang.permission.bean.PermissionBaseBean;
import com.zhang.permission.bean.PermissionBeforeBean;
import com.zhang.permission.bean.PermissionCanceledBean;
import com.zhang.permission.bean.PermissionDeniedBean;
import com.zhang.permission.exception.PermissionException;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Author: zhang_quan
 * Email:  zhang_quan_888@163.com
 * Date:   2019/3/2
 */

public abstract class PermissionBaseAspect {
    private static final String TAG = "PermissionBaseAspect";

    protected ProceedingJoinPoint mJoinPoint;
    protected Context mContext;
    protected Object mObject;
    protected Method[] mMethods;
    protected NeedPermission mNeedPermission;


    public void requestPermission() {
        requestPermission(mContext, mJoinPoint, mNeedPermission, mObject, mMethods);
    }

    protected abstract void requestPermission(final Context context, final ProceedingJoinPoint joinPoint, final NeedPermission needPermission, final Object object, final Method[] methods);

    protected boolean executeBefore(Context context, Object object, Method[] methods, NeedPermission needPermission, String beforeKey) {
        for (Method method : methods) {
            PermissionBefore annotation = method.getAnnotation(PermissionBefore.class);
            if (annotation != null) {
                String value = annotation.value();
                if (TextUtils.isEmpty(beforeKey) && TextUtils.isEmpty(value)) {
                    if (executeBeforeMethod(context, object, method, needPermission)) return true;
                } else if (!TextUtils.isEmpty(beforeKey) && beforeKey.equals(value)) {
                    if (executeBeforeMethod(context, object, method, needPermission)) return true;
                }
            }
        }
        return false;
    }

    protected boolean executeCanceled(Context context, Object object, Method[] methods, PermissionCanceledBean bean, String canceledKey) {
        for (Method method : methods) {
            PermissionCanceled annotation = method.getAnnotation(PermissionCanceled.class);
            if (annotation != null) {
                String value = annotation.value();
                if (TextUtils.isEmpty(canceledKey) && TextUtils.isEmpty(value)) {
                    if (executeCanceledMethod(bean, context, object, method)) return true;
                } else if (!TextUtils.isEmpty(canceledKey) && canceledKey.equals(value)) {
                    if (executeCanceledMethod(bean, context, object, method)) return true;
                }
            }
        }
        return false;
    }

    protected boolean executeDenied(Context context, Object object, Method[] methods, PermissionDeniedBean bean, String deniedKey) {
        for (Method method : methods) {
            PermissionDenied annotation = method.getAnnotation(PermissionDenied.class);
            if (annotation != null) {
                String value = annotation.value();
                if (TextUtils.isEmpty(deniedKey) && TextUtils.isEmpty(value)) {
                    if (executeDeniedMethod(bean, context, object, method)) return true;
                } else if (!TextUtils.isEmpty(deniedKey) && deniedKey.equals(value)) {
                    if (executeDeniedMethod(bean, context, object, method)) return true;
                }
            }
        }
        return false;
    }

    protected boolean executeBeforeMethod(Context context, Object object, Method method, NeedPermission needPermission) {
        PermissionBeforeBean beforeBean = new PermissionBeforeBean(context, this, needPermission);
        return executeMethod(beforeBean, object, method,
                "The method parameters decorated by the PermissionBefore annotation can only be the PermissionBeforeBean");
    }

    protected boolean executeCanceledMethod(PermissionCanceledBean bean, Context context, Object object, Method method) {
        bean.setContext(context);
        bean.setAspect(this);
        bean.setNeedPermission(mNeedPermission);
        return executeMethod(bean, object, method,
                "The method parameters decorated by the PermissionCanceled annotation can only be the PermissionCanceledBean");
    }

    protected boolean executeDeniedMethod(PermissionDeniedBean bean, Context context, Object object, Method method) {
        bean.setContext(context);
        bean.setAspect(this);
        bean.setNeedPermission(mNeedPermission);
        return executeMethod(bean, object, method,
                "The method parameters decorated by the PermissionDenied annotation can only be the PermissionDeniedBean");
    }

    protected <B extends PermissionBaseBean> boolean executeMethod(B bean, Object object, Method method, String exceptionPrompt) {
        Class<?>[] paramTypes = method.getParameterTypes();
        if (paramTypes.length != 1 || paramTypes[0] != bean.getClass()) {
            throw new PermissionException(exceptionPrompt);
        }
        try {
            method.invoke(object, bean);
            return true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected void proceed(ProceedingJoinPoint joinPoint) {
        try {
            Log.i(TAG, "proceed: 继续执行方法体");
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public abstract void proceed(boolean isRequest);

}
