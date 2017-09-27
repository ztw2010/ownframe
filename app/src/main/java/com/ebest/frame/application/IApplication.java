package com.ebest.frame.application;

import android.app.Application;

import com.ebest.frame.annomationapilib.route.RouterConfiguration;
import com.ebest.frame.mainmodule.RouterRuleCreator;

/**
 * Created by abc on 2017/9/21.
 */

public class IApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());
    }
}
