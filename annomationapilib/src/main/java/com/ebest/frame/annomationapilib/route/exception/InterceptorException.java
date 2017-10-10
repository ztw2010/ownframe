package com.ebest.frame.annomationapilib.route.exception;


import com.ebest.frame.annomationapilib.route.interceptors.RouteInterceptor;

/**
 * Throw this exception when routing events were intercepted。
 */
public class InterceptorException extends RuntimeException {

    private final RouteInterceptor interceptor;
    public InterceptorException(RouteInterceptor interceptor) {
        super("This route action has been intercepted");
        this.interceptor = interceptor;
    }

    /**
     * Provide users with access to the interceptor
     * @return The interceptor who intercept the event
     */
    public RouteInterceptor getInterceptor() {
        return interceptor;
    }
}
