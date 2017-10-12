package com.ebest.module.loginmodule.serviceimpl;

import android.support.v4.app.Fragment;

import com.ebest.frame.commnetservicve.loginmoduleservice.IFragmentService;
import com.ebest.module.loginmodule.login.LoginModuleFragment;

/**
 * Created by abc on 2017/9/27.
 */

public class IFragmenImpl implements IFragmentService {
    @Override
    public Fragment getFragment() {
        return new LoginModuleFragment();
    }
}
