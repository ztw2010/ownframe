package com.ebest.frame.annomationapilib.route.launcher;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.ebest.frame.annomationapilib.route.extras.RouteBundleExtras;
import com.ebest.frame.annomationapilib.route.route.ActivityRoute;


/**
 * Default Activity Launcher for {@link ActivityRoute}
 */
public class DefaultActivityLauncher extends ActivityLauncher{

    @Override
    public Intent createIntent(Context context) {
        Intent intent = new Intent();
        intent.setClassName(context, rule.getRuleClz());
        intent.putExtras(bundle);
        intent.putExtras(extras.getExtras());
        intent.addFlags(extras.getFlags());
        return intent;
    }

    @Override
    public void open(Fragment fragment) throws Exception {
        Intent intent = createIntent(fragment.getActivity());
        fragment.startActivityForResult(intent, extras.getRequestCode());
        overridePendingTransition(fragment.getActivity(), extras);
    }

    @Override
    public void open(android.support.v4.app.Fragment fragment) throws Exception {
        Intent intent = createIntent(fragment.getContext());
        fragment.startActivityForResult(intent, extras.getRequestCode());
        overridePendingTransition(fragment.getActivity(), extras);
    }

    @Override
    public void open(Context context) throws Exception {
        Intent intent = createIntent(context);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent,extras.getRequestCode());
            overridePendingTransition((Activity) context, extras);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    protected void overridePendingTransition(Activity activity, RouteBundleExtras extras) {
        if (activity == null || extras == null) {
            return;
        }

        int inAnimation = extras.getInAnimation();
        int outAnimation = extras.getOutAnimation();
        if (inAnimation >= 0 && outAnimation >= 0) {
            activity.overridePendingTransition(inAnimation,outAnimation);
        }
    }
}
