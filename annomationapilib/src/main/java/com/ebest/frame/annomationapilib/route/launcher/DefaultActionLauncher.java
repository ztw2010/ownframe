package com.ebest.frame.annomationapilib.route.launcher;

import android.content.Context;
import android.os.Bundle;

import com.ebest.frame.annomationapilib.parama.Parceler;
import com.ebest.frame.annomationapilib.route.route.ActionRoute;
import com.ebest.frame.annomationapilib.route.route.ActionSupport;
import com.ebest.frame.annomationapilib.route.tools.Utils;

/**
 * Default Action Launcher for {@link ActionRoute}
 */
public class DefaultActionLauncher extends ActionLauncher{

    @Override
    public void open(Context context) throws Exception {
        final ActionSupport support = newInstance(rule.getRuleClz());
        final Bundle data = new Bundle();
        data.putAll(bundle);
        data.putAll(extras.getExtras());
        if (Utils.PARCELER_SUPPORT) {
            Parceler.toEntity(support, data);// inject data
        }
        getExecutor().execute(new ActionRunnable(support, context, data));
    }

    private static class ActionRunnable implements Runnable {

        ActionSupport support;
        Context context;
        Bundle data;

        ActionRunnable(ActionSupport support, Context context, Bundle data) {
            this.support = support;
            this.context = context;
            this.data = data;
        }

        @Override
        public void run() {
            support.onRouteTrigger(context, data);
        }
    }

    private ActionSupport newInstance(String name) {
        try {
            return (ActionSupport) Class.forName(name).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(String.format("create instance of %s failed", name), e);
        }
    }
}
