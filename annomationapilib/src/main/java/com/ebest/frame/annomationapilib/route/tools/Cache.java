package com.ebest.frame.annomationapilib.route.tools;


import com.ebest.frame.annomationapilib.route.executors.MainThreadExecutor;
import com.ebest.frame.annomationapilib.route.module.ActionRouteRule;
import com.ebest.frame.annomationapilib.route.module.ActivityRouteRule;
import com.ebest.frame.annomationapilib.route.module.RouteCreator;
import com.ebest.frame.annomationapilib.route.module.RouteRule;
import com.ebest.frame.annomationapilib.route.parser.URIParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * The cache manager to holds all the cached instance.
 */
public final class Cache {

    private static boolean shouldReload;// if should be reload routeMap.
    /** A container to contains all of route rule creator,compat with some complex scene*/
    private static List<RouteCreator> creatorList = new ArrayList<>();
    /** A map to contains all of route rule created by creatorList*/
    private static Map<String,ActivityRouteRule> activityRouteMap = new HashMap<>();
    private static Map<String,ActionRouteRule> actionRouteMap = new HashMap<>();
    public static final int TYPE_ACTIVITY_ROUTE = 0;
    public static final int TYPE_ACTION_ROUTE = 1;

    private final static Map<Class<? extends Executor>, Executor> container = new HashMap<>();

    /**
     * <p>
     * Add a {@link RouteCreator} who contains some route rules to be used.
     * this method could be invoked multiple-times. so that you can put multiple route rules from difference modules
     * </p>
     * @param creator {@link RouteCreator}
     */
    public static void addCreator (RouteCreator creator) {
        if (creator == null) {
            throw new IllegalArgumentException("Route creator should not be null");
        }
        creatorList.add(creator);
        shouldReload = true;
    }

    public static Map<String, ActionRouteRule> getActionRules() {
        obtainRouteRulesIfNeed();
        return actionRouteMap;
    }

    public static Map<String,ActivityRouteRule> getActivityRules() {
        obtainRouteRulesIfNeed();
        return activityRouteMap;
    }

    public static RouteRule getRouteMapByUri(URIParser parser, int type) {
        String route = parser.getRoute();
        Map routes;
        if (type == TYPE_ACTIVITY_ROUTE) {
            routes = getActivityRules();
        } else {
            routes = getActionRules();
        }
        return (RouteRule) routes.get(route);
    }

    private static void obtainRouteRulesIfNeed() {
        if (shouldReload) {
            activityRouteMap.clear();
            actionRouteMap.clear();
            int count = creatorList == null ? 0 : creatorList.size();
            for (int i = 0; i < count; i++) {
                addAll(activityRouteMap, creatorList.get(i).createActivityRouteRules());
                addAll(actionRouteMap, creatorList.get(i).createActionRouteRules());
            }
            shouldReload = false;
        }
    }

    private static <R> void addAll(Map<String, R> src, Map<String, R> target) {
        if (target == null || src == null) {
            return;
        }
        for (Map.Entry<String, R> entry : target.entrySet()) {
            src.put(Utils.format(entry.getKey()), entry.getValue());
        }
    }

    public static Executor findOrCreateExecutor(Class<? extends Executor> key) {
        try {
            Executor executor = container.get(key);
            if (executor == null) {
                executor = key.newInstance();
                registerExecutors(key, executor);
            }
            return executor;
        } catch (Throwable t) {
            // provided MainThreadExecutor if occurs an error.
            return new MainThreadExecutor();
        }
    }

    /**
     * To register an Executor to be used.
     * @param key The class of Executor
     * @param value The Executor instance associate with the key.
     */
    @SuppressWarnings("WeakerAccess")
    public static void registerExecutors(Class<? extends Executor> key, Executor value) {
        if (key == null || value == null) {
            return;
        }

        container.remove(key);
        container.put(key, value);
    }
}
