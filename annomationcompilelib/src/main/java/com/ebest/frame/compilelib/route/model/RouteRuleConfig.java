package com.ebest.frame.compilelib.route.model;

import com.ebest.frame.annomationlib.router.annomation.ActionLauncher;
import com.ebest.frame.annomationlib.router.annomation.ActivityLauncher;
import com.ebest.frame.annomationlib.router.annomation.RouteInterceptors;
import com.ebest.frame.annomationlib.router.annomation.RouterRule;
import com.ebest.frame.compilelib.UtilMgr;
import com.ebest.frame.compilelib.route.Constants;
import com.ebest.frame.compilelib.route.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

/**
 * The configuration class links to {@link RouterRule}.
 *
 * <p>
 *     This class are one-to-one correspondence to {@link RouterRule}
 * </p>
 */
public class RouteRuleConfig {
    private String[] routes;
    private String pack;
    private ClassName launcher;
    private TypeName[] interceptors;

    public static RouteRuleConfig create(RouterRule rule, BasicConfigurations basicConfigurations, TypeElement type) {
        RouteRuleConfig config = new RouteRuleConfig();
        config.routes = config.combineRoute(rule, basicConfigurations);
        config.pack = config.combinePack(rule, basicConfigurations);
        config.interceptors = config.combineInterceptors(type);
        if (Utils.isSuperClass(type, Constants.CLASSNAME_ACTIVITY)) {
            config.launcher = config.combineActivityLauncher(type.getAnnotation(ActivityLauncher.class));
        } else if (Utils.isSuperClass(type, Constants.CLASSNAME_ACTION_SUPPORT)) {
            config.launcher = config.combineActionLauncher(type.getAnnotation(ActionLauncher.class));
        }
        return config;
    }

    private TypeName[] combineInterceptors(TypeElement type) {
        ArrayList<TypeName> interceptors = new ArrayList<>();
        RouteInterceptors routeInterceptors = type.getAnnotation(RouteInterceptors.class);
        if (routeInterceptors != null) {
            try {
                Class[] value = routeInterceptors.value();
                for (Class item : value) {
                    interceptors.add(ClassName.get(item));
                }
            } catch (MirroredTypesException mirrored) {
                List<? extends TypeMirror> typeMirrors = mirrored.getTypeMirrors();
                for (TypeMirror mirror : typeMirrors) {
                    interceptors.add(ClassName.get(mirror));
                }
            }
        }
        return interceptors.toArray(new TypeName[interceptors.size()]);
    }

    private String[] combineRoute(RouterRule rule, BasicConfigurations configurations) {
        String[] routes = rule.value();
        for (int i = 0; i < routes.length; i++) {
            String route = routes[i];
            if (Utils.isEmpty(route)) {
                throw new IllegalArgumentException("values of annotation RouteRule can not be null!");
            }
            String baseUrl = configurations.baseUrl;
            URI uri = URI.create(route);
            String scheme = uri.getScheme();
            if (Utils.isEmpty(scheme)) {
                if (Utils.isEmpty(baseUrl)) {
                    throw new IllegalArgumentException("Could not find baseUrl set by RouteConfig to join with the route:" + route);
                }
                route = baseUrl + route;
            }
            routes[i] = route;
        }
        return routes;
    }

    private String combinePack(RouterRule rule, BasicConfigurations configurations) {
        return Utils.isEmpty(rule.pack())
                ? Utils.isEmpty(configurations.pack)
                ? "com.ebest.frame.router" : configurations.pack : rule.pack();
    }

    private ClassName combineActivityLauncher(ActivityLauncher rule) {
        ClassName launcher = null;
        try {
            if (rule != null) {
                launcher = ClassName.get(rule.value());
            }
        } catch (MirroredTypeException mirrored) {
            TypeMirror typeMirror = mirrored.getTypeMirror();
            launcher = ClassName.get((TypeElement) UtilMgr.getMgr().getTypeUtils().asElement(typeMirror));
        }

        Elements utils = UtilMgr.getMgr().getElementUtils();
        if (launcher != null
                && !Utils.isSuperClass(utils.getTypeElement(launcher.toString()), Constants.CLASSNAME_ACTIVITY_LAUNCHER)) {
            throw new IllegalArgumentException(String.format(
                    "The class you set via ActivityLauncher should be a subclass of %s", Constants.CLASSNAME_ACTIVITY_LAUNCHER));
        }

        return launcher;
    }

    private ClassName combineActionLauncher(ActionLauncher rule) {
        ClassName launcher = null;
        try {
            if (rule != null) {
                launcher = ClassName.get(rule.value());
            }
        } catch (MirroredTypeException mirrored) {
            TypeMirror typeMirror = mirrored.getTypeMirror();
            launcher = ClassName.get((TypeElement) UtilMgr.getMgr().getTypeUtils().asElement(typeMirror));
        }

        Elements utils = UtilMgr.getMgr().getElementUtils();
        if (launcher != null
                && !Utils.isSuperClass(utils.getTypeElement(launcher.toString()), Constants.CLASSNAME_ACTION_LAUNCHER)) {
            throw new IllegalArgumentException(String.format(
                    "The class you set via ActivityLauncher should be a subclass of %s", Constants.CLASSNAME_ACTION_LAUNCHER));
        }

        return launcher;
    }



    public String[] getRoute() {
        return routes == null ? new String[0] : routes;
    }

    public String getPack() {
        return pack;
    }

    public ClassName getLauncher() {
        return launcher;
    }

    public TypeName[] getInterceptors () {
        return interceptors;
    }
}
