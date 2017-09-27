package com.ebest.frame.annomationapilib.route.route;

import android.net.Uri;

import com.ebest.frame.annomationapilib.route.RouterConfiguration;
import com.ebest.frame.annomationapilib.route.exception.NotFoundException;
import com.ebest.frame.annomationapilib.route.extras.RouteBundleExtras;
import com.ebest.frame.annomationapilib.route.module.RouteRule;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     Internal route callback.
 * </p>
 */
public final class InternalCallback {

    // store the map to provided find extras for uri.
    private static Map<Uri, RouteBundleExtras> cache = new HashMap<>();

    private Uri uri;
    private RouteBundleExtras extras = new RouteBundleExtras();
    private RouteRule rule;
    private Throwable error;

    public InternalCallback(Uri uri) {
        this.uri = uri;
    }

    public void setCallback(RouteCallback callback) {
        extras.setCallback(callback);
    }

    public RouteBundleExtras getExtras() {
        return extras;
    }

    public void setExtras(RouteBundleExtras extras) {
        if (extras != null) {
            this.extras = extras;
        }
    }

    public void onOpenSuccess(RouteRule rule) {
        this.rule = rule;
    }

    public void onOpenFailed(Throwable e) {
        this.error = e;
    }

    void invoke() {
        cache.put(uri, extras);
        RouteCallback global = RouterConfiguration.get().getCallback();
        invokeWithCallback(global);

        RouteCallback callback = extras.getCallback();
        invokeWithCallback(callback);
        cache.remove(uri);
    }

    private void invokeWithCallback(RouteCallback callback) {
        if (callback == null) {
            return;
        }
        if (error != null && error instanceof NotFoundException) {
            callback.notFound(uri, (NotFoundException) error);
        } else if (error != null) {
            callback.onOpenFailed(uri, error);
        } else if (rule != null) {
            callback.onOpenSuccess(uri, rule);
        } else {
            callback.onOpenFailed(uri, new RuntimeException("Unknown error"));
        }
    }

    public static RouteBundleExtras findExtrasByUri(Uri uri) {
        return cache.get(uri);
    }
}
