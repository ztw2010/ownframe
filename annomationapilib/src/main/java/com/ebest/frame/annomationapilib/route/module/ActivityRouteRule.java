package com.ebest.frame.annomationapilib.route.module;


import android.app.Activity;

import com.ebest.frame.annomationapilib.route.launcher.ActivityLauncher;


public class ActivityRouteRule extends RouteRule<ActivityRouteRule, ActivityLauncher> {

    public <T extends Activity> ActivityRouteRule(Class<T> clz) {
        super(clz.getCanonicalName());
    }

    public ActivityRouteRule(String clzName) {
        super(clzName);
    }
}
