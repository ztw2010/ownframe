package com.ebest.module.loginmodule.applike;

import com.ebest.frame.annomationapilib.route.RouterConfiguration;
import com.ebest.frame.baselib.excutor.SmartExecutor;
import com.ebest.frame.commnetservicve.applike.IApplicationLike;
import com.ebest.frame.commnetservicve.base.ComponentService;
import com.ebest.frame.commnetservicve.loginmoduleservice.IFragmentService;
import com.ebest.frame.loginmodule.RouterRuleCreator;
import com.ebest.module.loginmodule.serviceimpl.IFragmenImpl;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by ztw on 2017/9/22.
 */

public class LoginModuleAppLike implements IApplicationLike {

    private ComponentService componentService = ComponentService.getInstance();

    @Override
    public void onCreate() {
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());
        componentService.addService(IFragmentService.class.getSimpleName(), new IFragmenImpl());
        if(SmartExecutor.getInstance().isInit()){
            SmartExecutor.getInstance().init();
        }
    }

    @Override
    public void onStop() {
        componentService.removeService(IFragmentService.class.getSimpleName());
    }

}
