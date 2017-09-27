package com.ebest.frame.annomationapilib.route;

import android.content.Context;
import android.net.Uri;

import com.ebest.frame.annomationapilib.route.extras.RouteBundleExtras;
import com.ebest.frame.annomationapilib.route.interceptors.RouteInterceptor;
import com.ebest.frame.annomationapilib.route.launcher.ActionLauncher;
import com.ebest.frame.annomationapilib.route.launcher.ActivityLauncher;
import com.ebest.frame.annomationapilib.route.launcher.DefaultActionLauncher;
import com.ebest.frame.annomationapilib.route.launcher.DefaultActivityLauncher;
import com.ebest.frame.annomationapilib.route.module.RouteCreator;
import com.ebest.frame.annomationapilib.route.protocol.HostServiceWrapper;
import com.ebest.frame.annomationapilib.route.protocol.IRemoteFactory;
import com.ebest.frame.annomationapilib.route.route.ActionRoute;
import com.ebest.frame.annomationapilib.route.route.ActivityRoute;
import com.ebest.frame.annomationapilib.route.route.InternalCallback;
import com.ebest.frame.annomationapilib.route.route.RouteCallback;
import com.ebest.frame.annomationapilib.route.tools.Cache;

import java.util.concurrent.Executor;

/**
 * Entrance class to store router configurations.
 */
public final class RouterConfiguration {

    private RouteInterceptor interceptor;
    private RouteCallback callback;

    private IRemoteFactory remoteFactory = null;
    private Class<? extends ActivityLauncher> activityLauncher;
    private Class<? extends ActionLauncher> actionLauncher;


    public RouteInterceptor getInterceptor() {
        return interceptor;
    }

    /**
     * Set a default routing interceptor to used. it will be called by all the routes.
     * @param interceptor the default interceptor
     * @return config itself
     * @see RouteInterceptor
     */
    public RouterConfiguration setInterceptor(RouteInterceptor interceptor) {
        this.interceptor = interceptor;
        return this;
    }

    public RouteCallback getCallback() {
        return callback;
    }

    /**
     * Set a default routing callback to used. it will be called by all the routes.
     * @param callback The default callback
     * @return config itself
     * @see RouteCallback
     */
    public RouterConfiguration setCallback(RouteCallback callback) {
        this.callback = callback;
        return this;
    }

    public IRemoteFactory getRemoteFactory() {
        return remoteFactory;
    }

    /**
     * Set a default remote factory to used. the factory must contains a default empty construction.
     * @param remoteFactory The remote factory class
     * @return config itself
     * @see IRemoteFactory
     */
    public RouterConfiguration setRemoteFactory(IRemoteFactory remoteFactory) {
        this.remoteFactory = remoteFactory;
        return this;
    }

    public Class<? extends ActivityLauncher> getActivityLauncher() {
        return activityLauncher == null ? DefaultActivityLauncher.class : activityLauncher;
    }

    /**
     * Set a default activity launcher to used.
     * @param activityLauncher The launcher class for {@link ActivityRoute}
     * @return config itself
     * @see ActivityLauncher
     */
    public RouterConfiguration setActivityLauncher(Class<? extends ActivityLauncher> activityLauncher) {
        this.activityLauncher = activityLauncher;
        return this;
    }

    public Class<? extends ActionLauncher> getActionLauncher() {
        return actionLauncher == null ? DefaultActionLauncher.class : actionLauncher;
    }

    /**
     * Set a default action launcher to used.
     * @param actionLauncher The launcher class for {@link ActionRoute}
     * @return config itself
     * @see ActionLauncher
     */
    public RouterConfiguration setActionLauncher(Class<? extends ActionLauncher> actionLauncher) {
        this.actionLauncher = actionLauncher;
        return this;
    }

    /**
     * Add a route rule creator and register it for remote service if is launched.
     * @param creator Route rules creator.can't be null
     */
    public void addRouteCreator(RouteCreator creator) {
        Cache.addCreator(creator);
        HostServiceWrapper.registerRulesToHostService();
    }

    /**
     * To register an executor that
     * @param key The class of Executor
     * @param value The Executor instance associate with the key.
     * @see Cache#registerExecutors(Class, Executor)
     */
    public void registerExecutors(Class<? extends Executor> key, Executor value) {
        Cache.registerExecutors(key, value);
    }

    /**
     * @see RouterConfiguration#startHostService(String, Context, String)
     * @param hostPackage The package name of host. it to launch a remote service of host.
     * @param context The valid context
     */
    public void startHostService(String hostPackage, Context context) {
        startHostService(hostPackage, context, null);
    }

    /**
     * start a remote host service
     * @param hostPackage The package name of host. it to launch a remote service of host.
     * @param context The valid context
     * @param pluginName The unique identifier plugin name. or null to use the plugin-package name for it.
     */
    public void startHostService(String hostPackage, Context context, String pluginName) {
        HostServiceWrapper.startHostService(hostPackage, context, pluginName);
    }

    /**
     * Check if the specified plug-in names have been registered to the remote service.
     * @param pluginName The specified plug-in names
     * @return True if it has been registered.
     */
    public boolean isRegister(String pluginName) {
        return HostServiceWrapper.isRegister(pluginName);
    }

    /**
     * Restore a {@link RouteBundleExtras} by uri. this method should only be called in lifecycle of {@link RouteCallback}.
     * otherwise it will be null cause it is cleaned.
     * @param uri The uri that you open
     * @return The {@link RouteBundleExtras} instance that you may set before before you open the routing by uri.
     */
    public RouteBundleExtras restoreExtras(Uri uri) {
        return InternalCallback.findExtrasByUri(uri);
    }

    private static RouterConfiguration config = new RouterConfiguration();
    private RouterConfiguration() {}
    public static RouterConfiguration get() {
        return config;
    }
}
