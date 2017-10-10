package com.ebest.module.loginmodule;

import android.os.Bundle;

import com.ebest.frame.annomationlib.router.annomation.RouterRule;
import com.ebest.frame.commnetservicve.base.BaseActivity;

@RouterRule({"main/LoginSecondActivity"})
public class LoginSecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_second);
    }
}
