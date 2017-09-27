package com.ebest.frame.annomationapilib.route.route;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.ebest.frame.annomationapilib.route.RouterConfiguration;
import com.ebest.frame.annomationapilib.route.launcher.ActivityLauncher;
import com.ebest.frame.annomationapilib.route.launcher.Launcher;
import com.ebest.frame.annomationapilib.route.module.ActivityRouteRule;
import com.ebest.frame.annomationapilib.route.tools.Utils;


/**
 * A route tool to check route rule by uri and launch activity
 * Created by lzh on 16/9/5.
 */
public class ActivityRoute extends BaseRoute<IActivityRoute> implements IActivityRoute {

    @Override
    public Intent createIntent(Context context) {
        ActivityLauncher activityLauncher = (ActivityLauncher) launcher;
        activityLauncher.set(uri, bundle, callback.getExtras(), (ActivityRouteRule) routeRule, remote);
        return activityLauncher.createIntent(context);
    }

    @Override
    public IActivityRoute requestCode(int requestCode) {
        this.callback.getExtras().setRequestCode(requestCode);
        return this;
    }

    @Override
    public IActivityRoute setAnim(int enterAnim, int exitAnim) {
        this.callback.getExtras().setInAnimation(enterAnim);
        this.callback.getExtras().setOutAnimation(exitAnim);
        return this;
    }

    @Override
    public IActivityRoute addFlags(int flag) {
        this.callback.getExtras().addFlags(flag);
        return this;
    }

    @Override
    public void open(Fragment fragment) {
        try {
            Utils.checkInterceptor(uri, callback.getExtras(), fragment.getActivity(), getInterceptors());
            ActivityLauncher activityLauncher = (ActivityLauncher) launcher;
            activityLauncher.set(uri, bundle, callback.getExtras(), (ActivityRouteRule) routeRule, remote);
            activityLauncher.open(fragment);
            callback.onOpenSuccess(routeRule);
        } catch (Throwable e) {
            callback.onOpenFailed(e);
        }

        callback.invoke();
    }

    @Override
    public void open(android.support.v4.app.Fragment fragment) {
        try {
            Utils.checkInterceptor(uri, callback.getExtras(), fragment.getActivity(), getInterceptors());
            ActivityLauncher activityLauncher = (ActivityLauncher) launcher;
            activityLauncher.set(uri, bundle, callback.getExtras(), (ActivityRouteRule) routeRule, remote);
            activityLauncher.open(fragment);
            callback.onOpenSuccess(routeRule);
        } catch (Throwable e) {
            callback.onOpenFailed(e);
        }
        callback.invoke();
    }

    @Override
    protected Launcher obtainLauncher() throws Exception {
        ActivityRouteRule rule = (ActivityRouteRule) routeRule;
        Class<? extends ActivityLauncher> launcher = rule.getLauncher();
        if (launcher == null) {
            launcher = RouterConfiguration.get().getActivityLauncher();
        }
        return launcher.newInstance();
    }
}
