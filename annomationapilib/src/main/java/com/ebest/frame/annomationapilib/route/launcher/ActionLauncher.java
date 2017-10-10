package com.ebest.frame.annomationapilib.route.launcher;


import com.ebest.frame.annomationapilib.route.module.ActionRouteRule;
import com.ebest.frame.annomationapilib.route.tools.Cache;

import java.util.concurrent.Executor;

/**
 * The base class of <i><b>Action Launcher</b></i>
 *
 * <p>
 *     The default impl is {@link DefaultActionLauncher}
 * </p>
 */
public abstract class ActionLauncher extends Launcher<ActionRouteRule> {

    /**
     * @return returns a executor instance to switching thread.
     */
    protected Executor getExecutor() {
        return Cache.findOrCreateExecutor(rule.getExecutor());
    }
}
