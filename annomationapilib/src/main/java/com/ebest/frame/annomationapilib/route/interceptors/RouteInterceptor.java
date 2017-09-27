package com.ebest.frame.annomationapilib.route.interceptors;

import android.content.Context;
import android.net.Uri;

import com.ebest.frame.annomationapilib.route.Router;
import com.ebest.frame.annomationapilib.route.extras.RouteBundleExtras;


/**
 * An interceptor interface
 */
public interface RouteInterceptor{

    /**
     * Whether or not be interrupted when open activity by uri
     * @param uri uri the uri to open
     * @param context context
     * @param extras some extras data for route,
     *               sometimes is null when you not use
     *               {@link Router#getBaseRoute()} to set some extras data into it
     * @return true if should be intercepted
     */
    boolean intercept (Uri uri, RouteBundleExtras extras, Context context);

    /**
     * This method should be invoked when you has been intercepted
     * @param uri uri the uri to open
     * @param context context
     * @param extras some extras data for route,
     *               sometimes is null when you not use
     *               {@link Router#getBaseRoute()} to set some extras data into it
     */
    void onIntercepted(Uri uri, RouteBundleExtras extras, Context context);
}
