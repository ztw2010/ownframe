package com.ebest.frame.annomationapilib.route.module;

import android.app.Activity;
import android.os.Bundle;

import com.ebest.frame.annomationapilib.route.interceptors.RouteInterceptor;
import com.ebest.frame.annomationapilib.route.launcher.Launcher;
import com.ebest.frame.annomationapilib.route.route.ActionSupport;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An entity to contains some data for route
 */
@SuppressWarnings("unchecked")
public class RouteRule<R extends RouteRule, L extends Launcher>{
    /** Associate with {@link Bundle#putString(String, String)}*/
    public static final int STRING = -1;
    /** Associate with {@link Bundle#putByte(String, byte)} */
    public static final int BYTE = 0;
    /** Associate with {@link Bundle#putShort(String, short)} */
    public static final int SHORT = 1;
    /** Associate with {@link Bundle#putInt(String, int)} */
    public static final int INT = 2;
    /** Associate with {@link Bundle#putLong(String, long)} */
    public static final int LONG = 3;
    /** Associate with {@link Bundle#putFloat(String, float)} */
    public static final int FLOAT = 4;
    /** Associate with {@link Bundle#putDouble(String, double)} */
    public static final int DOUBLE = 5;
    /** Associate with {@link Bundle#putBoolean(String, boolean)} */
    public static final int BOOLEAN = 6;
    /** Associate with {@link Bundle#putChar(String, char)} */
    public static final int CHAR = 7;

    /** Associate with {@link Bundle#putIntegerArrayList(String, ArrayList)} */
    public static final int INT_LIST = 8;
    /** Associate with {@link Bundle#putStringArrayList(String, ArrayList)} */
    public static final int STRING_LIST = 9;

    public RouteRule(String clzName) {
        this.clzName = clzName;
    }

    /** The class name must be subclass of {@link Activity} or {@link ActionSupport}*/
    private String clzName;
    private HashMap<String,Integer> params = new HashMap<>();
    private Class<? extends L> launcher;
    private Class<? extends RouteInterceptor>[] interceptors = new Class[0];

    public String getRuleClz() {
        return clzName;
    }

    public HashMap<String,Integer> getParams() {
        return params;
    }

    R setParams(HashMap<String,Integer> params) {
        if (params != null) {
            this.params = params;
        }
        return (R) this;
    }

    /**
     * Specify required type when parsing the Url parameters
     * @param key the key in Url params.
     * @param type the required type. default is {@link RouteRule#STRING}
     * @return RouteRule
     */
    public R addParam (String key, int type) {
        params.put(key,type);
        return (R) this;
    }

    /**
     * Set a serial of {@link RouteInterceptor} to used, it means when you launch this routing, the interceptors will be triggered.
     * @param classes The array of {@link RouteInterceptor}
     * @return RouteRule
     */
    public R setInterceptors(Class<? extends RouteInterceptor>... classes) {
        if (classes != null) {
            this.interceptors = classes;
        }
        return (R) this;
    }

    public Class<? extends RouteInterceptor>[] getInterceptors() {
        return interceptors;
    }

    public R setLauncher(Class<? extends L> launcher) {
        this.launcher = launcher;
        return (R) this;
    }

    public Class<? extends L> getLauncher() {
        return launcher;
    }
}
