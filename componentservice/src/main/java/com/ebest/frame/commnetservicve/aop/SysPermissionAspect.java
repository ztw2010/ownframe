package com.ebest.frame.commnetservicve.aop;

import android.app.Activity;

import com.ebest.frame.annomationapilib.aop.Permission;
import com.ebest.frame.baselib.activitymanager.ActivityStackManager;
import com.ebest.frame.baselib.util.MPermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


/**
 * Created by ztw on 17/1/31.
 * 申请系统权限切片，根据注解值申请所需运行权限
 */
@Aspect
public class SysPermissionAspect {

    @Around("execution(@com.ebest.frame.annomationapilib.aop.Permission * *(..)) && @annotation(permission)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, final Permission permission) throws Throwable {
        final Activity ac = ActivityStackManager.getInstance().currentActivity();
        MPermissionUtils.requestPermissionsResult(ac, 1, permission.value()
                , new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        try {
                            joinPoint.proceed();//获得权限，执行原方法
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onPermissionDenied() {
                        MPermissionUtils.showTipsDialog(ac);
                    }
                });
    }
}


