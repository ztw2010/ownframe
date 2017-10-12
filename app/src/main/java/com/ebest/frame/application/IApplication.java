package com.ebest.frame.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Environment;

import com.ebest.frame.annomationapilib.route.RouterConfiguration;
import com.ebest.frame.baselib.activitymanager.ActivityStackManager;
import com.ebest.frame.baselib.excutor.SmartExecutor;
import com.ebest.frame.baselib.greendao.base.DaoManager;
import com.ebest.frame.baselib.greendao.base.MobileBeanFactory;
import com.ebest.frame.baselib.okhttp.OkGo;
import com.ebest.frame.baselib.okhttp.cache.CacheEntity;
import com.ebest.frame.baselib.okhttp.cache.CacheMode;
import com.ebest.frame.mainmodule.RouterRuleCreator;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by abc on 2017/9/21.
 */

public class IApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initDB();
        initOkHttp();
        initExcutor();
        initBeanFactory();
        initActivityStack();
    }

    private void initDB() {
        File dbFile = new File(Environment.getExternalStorageDirectory().getPath() + "/ebest.db");
        if (!dbFile.exists()) {
            try {
                dbFile.createNewFile();
            } catch (IOException e) {

            }
        }
        DaoManager daoManager = DaoManager.getInstance();
        daoManager.setDebug(true);
        daoManager.init(getApplicationContext(), dbFile.getAbsolutePath());
    }

    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(0);                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());
    }

    private void initExcutor() {
        SmartExecutor smartExecutor = SmartExecutor.getInstance();
        smartExecutor.init();
        smartExecutor.setDebug(true);
    }

    private void initBeanFactory() {
        MobileBeanFactory.getInstance().initAdvanceBeans();
    }

    private void initActivityStack() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityStackManager.getInstance().pushActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityStackManager.getInstance().popActivity(activity);
            }
        });
    }
}
