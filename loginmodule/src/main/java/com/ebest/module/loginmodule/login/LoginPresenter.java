package com.ebest.module.loginmodule.login;

import android.Manifest;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ebest.frame.annomationapilib.aop.Permission;
import com.ebest.frame.baselib.excutor.SmartExecutor;
import com.ebest.frame.baselib.handler.WeakHandler;
import com.ebest.frame.baselib.okhttp.model.Response;
import com.ebest.frame.baselib.okhttp.rx1.subscribe.ResponeThrowable;
import com.ebest.frame.baselib.okhttp.rx1.subscribe.RxSubscriber;
import com.ebest.frame.baselib.xml.XmlBean;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ztw on 2017/10/10.
 */

public class LoginPresenter extends LoginContract.Presenter {

    private AtomicBoolean isFirstDown = new AtomicBoolean(true);

    private AtomicBoolean isDownFinish = new AtomicBoolean(false);

    private AtomicLong firstDownTime = new AtomicLong(0l);

    private AtomicLong lastDownTime = new AtomicLong(0l);

    private List<XmlBean> resultXmlBeen = new CopyOnWriteArrayList<>();

    private SmartExecutor mainExecutor;

    private List<String> tables = null;

    private final int DOWN_TABLE = 10, DOWN_HAS_ERROR = 11, DOWN_TABLE_ONE_SUCCESS = 12, DOWN_TABLES_SUCCESS = 13, DOWN_BEGIN = 14;

    private WeakHandler handler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_TABLE:
                    downloadTable((String) msg.obj);
                    break;
                case DOWN_TABLES_SUCCESS:
                    mView.onTableDownLoadSuccess((Long) msg.obj);
                    break;
            }
            return true;
        }
    });

    private void restorState(){
        isFirstDown.set(true);
        isDownFinish.set(false);
        firstDownTime.set(0l);
        lastDownTime.set(0l);
        resultXmlBeen.clear();
        unSubscribe();
    }

    @Permission(Manifest.permission.CAMERA)
    @Override
    public void getDownLoadTableData(final List<String> tables) {
        this.tables = tables;
        this.restorState();
        mainExecutor = SmartExecutor.getInstance();
        if (tables != null && !tables.isEmpty()) {
            for (String name : tables) {
                Message message = new Message();
                message.what = DOWN_TABLE;
                message.obj = name;
                handler.sendMessage(message);
            }
        }
        mainExecutor.execute(new Runnable() {
            @Override
            public void run() {
                while (!isDownFinish.get()) {
                    if (resultXmlBeen.size() == tables.size() && resultXmlBeen.size() != 0) {
                        isDownFinish.set(true);
                        Long b = System.currentTimeMillis();
                        lastDownTime.set(b);
                        Long diffTime = (b - firstDownTime.get());
                        Log.d(TAG, "所有表数据下载完成,下载耗时=" + diffTime);
                        Message message = new Message();
                        message.what = DOWN_TABLES_SUCCESS;
                        message.obj = diffTime;
                        handler.sendMessage(message);
                    }
                }
            }
        });
    }

    private void downloadTable(final String tableName) {
        addSubscribe(mModel.getDownLoadTableData(tableName).subscribe(new RxSubscriber<Response<XmlBean>>() {

            Long beginTime = 0l;

            @Override
            public void onStart() {
                super.onStart();
                beginTime = System.currentTimeMillis();
                Log.d(TAG, "表" + tableName + "开始下载,开始时间=" + beginTime);
                if (isFirstDown.get()) {
                    isFirstDown.set(false);
                    Log.d(TAG, "表数据开始下载");
                    firstDownTime.set(beginTime);
                    mView.onShowDialog(0);
                }
            }

            @Override
            public void onSuccess(Response<XmlBean> response) {
                Long endTime = System.currentTimeMillis();
                Log.d(TAG, "表" + tableName + "下载结束,结束时间=" + endTime + ",下载耗时=" + (endTime - beginTime));
                resultXmlBeen.add(response.body());
                mView.onShowDialog(100 / tables.size() * resultXmlBeen.size());
                //mView.onTableDownLoadSuccess(response.body());
            }

            @Override
            protected void onError(ResponeThrowable ex) {
                Long endTime = System.currentTimeMillis();
                Log.d(TAG, "表" + tableName + "下载失败,结束时间=" + endTime + ",下载耗时=" + (endTime - beginTime) + ",失败原因=" + ex.message);
                if (!isDownFinish.get()) {
                    isDownFinish.set(true);
                    mView.showErrorWithStatus(ex.message);
                }
            }
        }));
    }
}
