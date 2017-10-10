package com.ebest.frame.annomationapilib.route.module;


import com.ebest.frame.annomationapilib.route.executors.MainThreadExecutor;
import com.ebest.frame.annomationapilib.route.launcher.ActionLauncher;
import com.ebest.frame.annomationapilib.route.route.ActionSupport;

import java.util.concurrent.Executor;

public class ActionRouteRule extends RouteRule<ActionRouteRule, ActionLauncher> {

    private Class<? extends Executor> executor = MainThreadExecutor.class;

    public <T extends ActionSupport> ActionRouteRule(Class<T> clz) {
        super(clz.getCanonicalName());
    }

    public ActionRouteRule(String clzName) {
        super(clzName);
    }

    public ActionRouteRule setExecutorClass(Class<? extends Executor> executor) {
        if (executor != null) {
            this.executor = executor;
        }
        return this;
    }

    public Class<? extends Executor> getExecutor() {
        return executor;
    }
}
