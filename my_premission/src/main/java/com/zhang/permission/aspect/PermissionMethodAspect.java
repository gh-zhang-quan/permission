package com.zhang.permission.aspect;

import android.content.Context;
import android.text.TextUtils;

import com.zhang.permission.MyPermission;
import com.zhang.permission.annotation.NeedPermission;
import com.zhang.permission.bean.PermissionCanceledBean;
import com.zhang.permission.bean.PermissionDeniedBean;
import com.zhang.permission.listener.PermissionListener;
import com.zhang.permission.util.PermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Author: zhang_quan
 * Email:  zhang_quan_888@163.com
 * Date:   2019/3/2
 */
@Aspect
public class PermissionMethodAspect extends PermissionBaseAspect {
    @Pointcut("execution(@com.zhang.permission.annotation.NeedPermission * * (..)) " +
            "&& @annotation(com.zhang.permission.annotation.NeedPermission)")
    public void requestPermissionPointcut() {
    }

    @SuppressWarnings("deprecation")
    @Around("requestPermissionPointcut()")
    public void requestPermissionAround(final ProceedingJoinPoint joinPoint) {
        mJoinPoint = joinPoint;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        mNeedPermission = method.getAnnotation(NeedPermission.class);
        if (mNeedPermission == null) {
            throw new NullPointerException("There is no NeedPermission annotation");
        }
        mObject = joinPoint.getThis();
        if (mObject == null) {
            throw new NullPointerException("object is null");
        }

        if (mObject instanceof Context) {
            mContext = (Context) mObject;
        } else {
            mContext = MyPermission.getInstance().getContext();
        }
        String[] permissions = mNeedPermission.value();
        if (PermissionUtils.checkPermissions(mContext, permissions)) {
            proceed(joinPoint);
            return;
        }

        Class<?> aClass = mObject.getClass();
        mMethods = aClass.getMethods();
        String beforeKey = mNeedPermission.requestBefore();
        boolean isExecuteBefore;
        isExecuteBefore = executeBefore(mContext, mObject, mMethods, mNeedPermission, beforeKey);
        if (!isExecuteBefore && !TextUtils.isEmpty(beforeKey)) {
            Object configObject = MyPermission.getInstance().getConfigObject();
            if (configObject != null) {
                Class<?> aClass1 = configObject.getClass();
                Method[] methods1 = aClass1.getMethods();
                isExecuteBefore = executeBefore(mContext, configObject, methods1, mNeedPermission, beforeKey);
            }
        }
        if (!isExecuteBefore) {
            requestPermission(mContext, joinPoint, mNeedPermission, mObject, mMethods);
        }

    }


    public void proceed(boolean isRequest) {
        if (isRequest) {
            requestPermission();
        } else if (mNeedPermission.isAllowExecution()) {
            proceed(mJoinPoint);
        }
    }

    protected void requestPermission(final Context context, final ProceedingJoinPoint joinPoint, final NeedPermission needPermission, final Object object, final Method[] methods) {
        PermissionUtils.requestPermissions(context, needPermission.value(), needPermission.requestCode(), new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                proceed(joinPoint);
            }

            @Override
            public void onPermissionCanceled(PermissionCanceledBean bean) {
                if (needPermission.isAllowExecution()) {
                    proceed(joinPoint);
                    return;
                }
                String canceledKey = needPermission.permissionCanceled();
                boolean isExecuteCanceled;
                isExecuteCanceled = executeCanceled(context, object, methods, bean, canceledKey);
                if (!isExecuteCanceled) {
                    Object configObject = MyPermission.getInstance().getConfigObject();
                    if (configObject == null) return;
                    Class<?> aClass1 = configObject.getClass();
                    Method[] methods1 = aClass1.getMethods();
                    executeCanceled(context, configObject, methods1, bean, canceledKey);
                }
            }

            @Override
            public void onPermissionDenied(PermissionDeniedBean bean) {
                if (needPermission.isAllowExecution()) {
                    proceed(joinPoint);
                    return;
                }
                String deniedKey = needPermission.permissionDenied();
                boolean isExecuteDenied;
                isExecuteDenied = executeDenied(context, object, methods, bean, deniedKey);
                if (!isExecuteDenied) {
                    Object configObject = MyPermission.getInstance().getConfigObject();
                    if (configObject == null) return;
                    Class<?> aClass1 = configObject.getClass();
                    Method[] methods1 = aClass1.getMethods();
                    executeDenied(context, configObject, methods1, bean, deniedKey);
                }
            }
        });
    }
}
