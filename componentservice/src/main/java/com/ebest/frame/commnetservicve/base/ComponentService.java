package com.ebest.frame.commnetservicve.base;

import com.ebest.frame.annomationapilib.route.Router;

import java.util.HashMap;

/**
 * Created by ztw on 2017/9/27.
 * 模块服务注册器
 */

public class ComponentService {

    private HashMap<String, Object> services = new HashMap<>();

    private static volatile ComponentService instance;

    private ComponentService() {
    }

    public static ComponentService getInstance() {
        if (instance == null) {
            synchronized (Router.class) {
                if (instance == null) {
                    instance = new ComponentService();
                }
            }
        }
        return instance;
    }

    public synchronized void addService(String serviceName, Object serviceImpl) {
        if (serviceName == null || serviceImpl == null) {
            return;
        }
        services.put(serviceName, serviceImpl);
    }

    public synchronized Object getService(String serviceName) {
        if (serviceName == null) {
            return null;
        }
        return services.get(serviceName);
    }

    public synchronized void removeService(String serviceName) {
        if (serviceName == null) {
            return;
        }
        services.remove(serviceName);
    }

}
