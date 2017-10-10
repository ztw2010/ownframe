package com.ebest.frame.commnetservicve.base;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.ebest.frame.annomationapilib.parama.Parceler;
import com.ebest.frame.annomationlib.parama.annomation.Arg;
import com.ebest.frame.annomationlib.parama.annomation.Converter;
import com.ebest.frame.annomationlib.parama.annomation.FastJsonConverter;

import butterknife.ButterKnife;

/**
 * Created by ztw on 2017/9/26.
 */

/**
 * 继承于BaseActivity你可以：
 * 1、完成参数的自动注入
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener{

    protected String TAG = null;

    @Converter(FastJsonConverter.class)
    @Arg
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        Parceler.toEntity(this,getIntent());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parceler.toBundle(this,outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Parceler.toEntity(this,savedInstanceState);
    }

    @Override
    public void onClick(View view) {

    }
}
