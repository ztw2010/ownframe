package com.ebest.frame.annomationapilib.route.launcher;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.ebest.frame.annomationapilib.route.module.ActivityRouteRule;


/**
 * The base class of <i><b>Activity Launcher</b></i>
 *
 * <p>
 *     The default impl is {@link DefaultActivityLauncher}
 * </p>
 */
public abstract class ActivityLauncher extends Launcher<ActivityRouteRule>{

    /**
     * This method will be invoked when you call {@link com.ebest.frame.annomationapilib.route.route.ActivityRoute#createIntent(Context)}
     *
     * @param context The context instance.
     * @return The new intent that created by the launcher
     */
    public abstract Intent createIntent(Context context);

    /**
     * The launch method for Fragment: {@link Fragment#startActivityForResult(Intent, int)}
     * @param fragment The fragment instance
     * @throws Exception a error occurs
     */
    public abstract void open(Fragment fragment) throws Exception;

    /**
     * The launch method for Support-v4 Fragment: {@link android.support.v4.app.Fragment#startActivityForResult(Intent, int)}
     * @param fragment The fragment instance
     * @throws Exception a error occurs
     */
    public void open(android.support.v4.app.Fragment fragment) throws Exception {}
}
