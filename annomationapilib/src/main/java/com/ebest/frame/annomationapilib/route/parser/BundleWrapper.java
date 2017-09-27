package com.ebest.frame.annomationapilib.route.parser;

import android.os.Bundle;


/**
 * A wrapper class for bundle
 */
public abstract class BundleWrapper {
    final int type;
    /**
     * Create a wrapper instance form specially type.
     * @param type The type value defined in {@link RouteRule}
     */
    BundleWrapper(int type) {
        this.type = type;
    }

    /**
     * add a extra data to wrapper class
     * @param data extra data
     */
    public abstract void set(String data);

    /**
     * Put value in extras you had already set before pass by {@link BundleWrapper#set(String)}
     * @param extras The bundle instance to put in
     * @param key The key to put value
     */
    public abstract void put(Bundle extras, String key);
}
