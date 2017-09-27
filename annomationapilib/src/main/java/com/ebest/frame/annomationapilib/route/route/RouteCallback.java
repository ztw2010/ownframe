package com.ebest.frame.annomationapilib.route.route;

import android.net.Uri;

import com.ebest.frame.annomationapilib.route.exception.NotFoundException;
import com.ebest.frame.annomationapilib.route.module.ActionRouteRule;
import com.ebest.frame.annomationapilib.route.module.ActivityRouteRule;
import com.ebest.frame.annomationapilib.route.module.RouteRule;


/**
 * The route callback to notify the status of routing event.
 */
public interface RouteCallback {

    /**
     * There are two types of not found exception:<br>
     *     <i><b>{@link NotFoundException#TYPE_SCHEMA}: </b>This uri can't match the corresponding routing</i><br>
     *     <i><b>{@link NotFoundException#TYPE_CLZ}: </b>The special routing event that matched with uri does not exist.</i>
     * @param uri uri the uri to open
     * @param e {@link NotFoundException}
     */
    void notFound(Uri uri, NotFoundException e);

    /**
     * This method will be invoked when the routing task opened successful
     *
     * @param uri the uri to open
     * @param rule The uri matching rule, it could be {@link ActionRouteRule} or {@link ActivityRouteRule}
     */
    void onOpenSuccess(Uri uri, RouteRule rule);

    /**
     * A callback method to notice that you occurs some exception.<br>
     *     exclude <i>{@link NotFoundException}</i>
     * @param uri the uri to open
     * @param e the exception
     */
    void onOpenFailed(Uri uri, Throwable e);


}
