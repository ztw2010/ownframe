package com.ebest.module.loginmodule.runalone.application;

import android.app.Application;

import com.ebest.frame.annomationapilib.route.RouterConfiguration;
import com.ebest.frame.loginmodule.RouterRuleCreator;

/**
 * Created by ztw on 2017/9/22.
 */
public class IApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());
    }
}
