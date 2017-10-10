package com.ebest.frame.annomationapilib.route.protocol;

import android.content.Context;
import android.os.Bundle;

import com.ebest.frame.annomationapilib.route.module.RouteRule;


/**
 * <p>
 *     This factory used to create and provide a remote bundle data.
 * </p>
 *
 * <p>When need to register the routing rules to remote bridge service via {@link RouterConfiguration#startHostService(String, Context)},
 * the factory will be called to create a bundle and pass it to remote service from aidl interface.
 */
public interface IRemoteFactory {
    /**
     * Create a extra bundle data so that others process or plugin could compat.
     * @param application The application context.
     * @param rule The routing rule
     * @return new extra bundle or null.
     */
    Bundle createRemote(Context application, RouteRule rule);
}
