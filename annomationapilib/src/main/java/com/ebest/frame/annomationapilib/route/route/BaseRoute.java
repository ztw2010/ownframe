package com.ebest.frame.annomationapilib.route.route;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.ebest.frame.annomationapilib.route.Router;
import com.ebest.frame.annomationapilib.route.RouterConfiguration;
import com.ebest.frame.annomationapilib.route.extras.RouteBundleExtras;
import com.ebest.frame.annomationapilib.route.interceptors.RouteInterceptor;
import com.ebest.frame.annomationapilib.route.interceptors.RouteInterceptorAction;
import com.ebest.frame.annomationapilib.route.launcher.Launcher;
import com.ebest.frame.annomationapilib.route.module.RouteRule;
import com.ebest.frame.annomationapilib.route.parser.URIParser;
import com.ebest.frame.annomationapilib.route.tools.Cache;
import com.ebest.frame.annomationapilib.route.tools.Utils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class BaseRoute<T extends IBaseRoute> implements IRoute, IBaseRoute<T>, RouteInterceptorAction<T> {
    protected Bundle bundle;
    InternalCallback callback;
    protected Uri uri;
    protected Bundle remote;
    protected RouteRule routeRule = null;
    protected Launcher launcher;

    public final IRoute create(Uri uri, RouteRule rule, Bundle remote, InternalCallback callback) {
        try {
            this.uri = uri;
            this.remote = remote;
            this.callback = callback;
            this.routeRule = rule;
            this.bundle = Utils.parseRouteMapToBundle(new URIParser(uri), routeRule);
            this.bundle.putParcelable(Router.RAW_URI, uri);
            this.launcher = obtainLauncher();
            return this;
        } catch (Throwable e) {
            callback.onOpenFailed(e);
            return new EmptyRoute(callback);
        }
    }

    // =========Unify method of IBaseRoute
    @Override
    public final void open(Context context) {
        try {
            Utils.checkInterceptor(uri, callback.getExtras(), context,getInterceptors());
            launcher.set(uri, bundle, callback.getExtras(), routeRule, remote);
            launcher.open(context);
//            realOpen(context);
            callback.onOpenSuccess(routeRule);
        } catch (Throwable e) {
            callback.onOpenFailed(e);
        }

        callback.invoke();
    }

    @Override
    public T addExtras(Bundle extras) {
        this.callback.getExtras().addExtras(extras);
        return (T) this;
    }

    // =============RouteInterceptor operation===============
    public T addInterceptor(RouteInterceptor interceptor) {
        if (callback.getExtras() != null) {
            callback.getExtras().addInterceptor(interceptor);
        }
        return (T) this;
    }

    @Override
    public T removeInterceptor(RouteInterceptor interceptor) {
        if (callback.getExtras() != null) {
            callback.getExtras().removeInterceptor(interceptor);
        }
        return (T) this;
    }

    @Override
    public T removeAllInterceptors() {
        if (callback.getExtras() != null) {
            callback.getExtras().removeAllInterceptors();
        }
        return (T) this;
    }

    @Override
    public List<RouteInterceptor> getInterceptors() {

        List<RouteInterceptor> interceptors = new ArrayList<>();
        // add global interceptor
        if (RouterConfiguration.get().getInterceptor() != null) {
            interceptors.add(RouterConfiguration.get().getInterceptor());
        }

        // add extra interceptors
        if (callback.getExtras() != null) {
            interceptors.addAll(callback.getExtras().getInterceptors());
        }

        // add interceptors in rule
        for (Class<RouteInterceptor> interceptor : routeRule.getInterceptors()) {
            if (interceptor != null) {
                try {
                    interceptors.add(interceptor.newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(String.format("The interceptor class [%s] should provide a default empty construction", interceptor));
                }
            }
        }

        return interceptors;
    }

    // ========getter/setter============
    public void replaceExtras(RouteBundleExtras extras) {
        this.callback.setExtras(extras);
    }

    public static RouteRule findRule(Uri uri, int type) {
        return Cache.getRouteMapByUri(new URIParser(uri), type);
    }

    // ============abstract methods============
    protected abstract Launcher obtainLauncher() throws Exception;

}
