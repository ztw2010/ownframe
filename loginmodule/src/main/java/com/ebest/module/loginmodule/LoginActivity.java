package com.ebest.module.loginmodule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ebest.frame.annomationapilib.route.Router;
import com.ebest.frame.annomationlib.parama.annomation.Arg;
import com.ebest.frame.annomationlib.router.annomation.RouteInterceptors;
import com.ebest.frame.annomationlib.router.annomation.RouterRule;
import com.ebest.frame.commnetservicve.base.BaseActivity;
import com.ebest.module.loginmodule.interceptor.ToastInterceptor;

@RouterRule({"main/LoginActivity"})
@RouteInterceptors({ToastInterceptor.class})
public class LoginActivity extends BaseActivity {

    private TextView msgTv, goBackTv, jmpTv;

    @Arg
    public String username;

    @Arg
    public String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginmodule_activity_login);
        msgTv = findViewById(R.id.msg_tv);
        goBackTv = findViewById(R.id.goback_tv);
        jmpTv = findViewById(R.id.jmp_tv);
        jmpTv.setOnClickListener(this);
        goBackTv.setOnClickListener(this);
        msgTv.setText("username=" + username + ",address=" + address);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if(id == R.id.goback_tv){
            setResult(Activity.RESULT_OK);
            finish();
        }else if(id == R.id.jmp_tv){
            Router.create("loginmodule://main/LoginSecondActivity").open(this);
        }
    }
}
