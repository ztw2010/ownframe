package com.ebest.module.loginmodule.interceptor;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.ebest.frame.annomationapilib.route.extras.RouteBundleExtras;
import com.ebest.frame.annomationapilib.route.interceptors.RouteInterceptor;


public class ToastInterceptor implements RouteInterceptor {

    @Override
    public boolean intercept(Uri uri, RouteBundleExtras extras, Context context) {
        Toast.makeText(context, "拦截器吐司：每次进入LoginActivity时都被拦截器拦截一下", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onIntercepted(Uri uri, RouteBundleExtras extras, Context context) {

    }
}
