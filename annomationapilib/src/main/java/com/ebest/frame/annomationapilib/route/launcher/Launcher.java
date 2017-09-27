package com.ebest.frame.annomationapilib.route.launcher;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.ebest.frame.annomationapilib.route.Router;
import com.ebest.frame.annomationapilib.route.extras.RouteBundleExtras;
import com.ebest.frame.annomationapilib.route.module.RouteRule;


/**
 * The base launcher class.
 * @param <T> The route rule
 */
public abstract class Launcher<T extends RouteRule> {
    protected Uri uri;
    protected Bundle bundle;
    protected RouteBundleExtras extras;
    protected T rule;
    protected Bundle remote;

    /**
     * Requires to open with this launcher.
     * @param context context
     * @throws Exception Some error occurs.
     */
    public abstract void open(Context context) throws Exception;

    /**
     * Set all of extras data to used.
     * @param uri The route uri.
     * @param bundle The bundle data that is parsed by uri params.
     * @param extras The extras data you set via {@link Router#getRoute()}
     * @param rule The rule that associate with the uri.
     */
    public final void set(Uri uri, Bundle bundle, RouteBundleExtras extras, T rule, Bundle remote) {
        this.uri = uri;
        this.bundle = bundle;
        this.extras = extras;
        this.rule = rule;
        this.remote = remote;
    }
}
