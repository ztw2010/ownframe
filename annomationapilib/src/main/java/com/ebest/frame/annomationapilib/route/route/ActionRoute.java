package com.ebest.frame.annomationapilib.route.route;


import com.ebest.frame.annomationapilib.route.RouterConfiguration;
import com.ebest.frame.annomationapilib.route.launcher.ActionLauncher;
import com.ebest.frame.annomationapilib.route.launcher.Launcher;
import com.ebest.frame.annomationapilib.route.module.ActionRouteRule;

public class ActionRoute extends BaseRoute<IActionRoute> implements IActionRoute {

    @Override
    protected Launcher obtainLauncher() throws Exception {
        ActionRouteRule rule = (ActionRouteRule) routeRule;
        Class<? extends ActionLauncher> launcher = rule.getLauncher();
        if (launcher == null) {
            launcher = RouterConfiguration.get().getActionLauncher();
        }
        return launcher.newInstance();
    }
}
