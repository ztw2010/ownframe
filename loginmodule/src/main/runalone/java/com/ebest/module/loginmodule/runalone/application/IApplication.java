package com.ebest.module.loginmodule.runalone.application;

import android.app.Application;

<<<<<<< HEAD
=======
import com.ebest.frame.annomationapilib.route.RouterConfiguration;
import com.ebest.frame.loginmodule.RouterRuleCreator;

>>>>>>> 3c5d16009d3cab28d3ade4279235a6ad14971534
/**
 * Created by ztw on 2017/9/22.
 */
public class IApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
<<<<<<< HEAD
=======
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());
>>>>>>> 3c5d16009d3cab28d3ade4279235a6ad14971534
    }
}
