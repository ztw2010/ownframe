// IService.aidl
package com.ebest.frame.annomationapilib.route.protocol;

import android.content.Intent;
import android.net.Uri;

import com.ebest.frame.annomationapilib.route.module.RemoteRule;

// Declare any non-default types here with import statements

interface IService {

    void register(String pluginName);

    boolean isRegister(String pluginName);

    void addActivityRules(in Map rules);

    void addActionRules(in Map rules);

    RemoteRule getActionRule(in Uri uri);

    RemoteRule getActivityRule(in Uri uri);
}
