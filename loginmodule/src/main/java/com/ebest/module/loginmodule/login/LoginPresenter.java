package com.ebest.module.loginmodule.login;

import android.Manifest;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;

import com.ebest.frame.annomationapilib.aop.Permission;
import com.ebest.frame.baselib.okhttp.model.Progress;
import com.ebest.frame.baselib.okhttp.rx2.process.ExceptionHandle;
import com.ebest.frame.baselib.okhttp.rx2.process.ResponeThrowable;
import com.ebest.frame.baselib.xml.XmlBean;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by ztw on 2017/10/10.
 */

public class LoginPresenter extends LoginContract.Presenter {

    private List<XmlBean> resultXmlBeen = new CopyOnWriteArrayList<>();

    private List<String> tables = null;

    private Long tableStartTime = 0l;

    private Map<String, Long> tableDownTimeMap = new ConcurrentHashMap<>();

    private void restorState() {
        resultXmlBeen.clear();
    }

    @Permission(Manifest.permission.CAMERA)
    @Override
    public void getDownLoadTableData(final List<String> tables) {
        this.tables = tables;
        this.restorState();
        downLoad();
    }

    @Override
    public void downFile(String fileUrl) {
        if (!TextUtils.isEmpty(fileUrl)) {
            mView.onDownFile("文件下载中....");
            addDisposables(mModel.downFile(fileUrl).subscribe(new Consumer<Progress>() {
                @Override
                public void accept(@NonNull Progress progress) throws Exception {
                    String downloadLength = Formatter.formatFileSize(mContext, progress.currentSize);
                    String totalLength = Formatter.formatFileSize(mContext, progress.totalSize);
                    String speed = Formatter.formatFileSize(mContext, progress.speed);
                    String msg = String.format("文件总大小为:%s,已下载:%s,速率:%s/s", totalLength, downloadLength, speed);
                    Log.d(TAG, msg);
                    mView.onDownFile(msg);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    ResponeThrowable responeThrowable = ExceptionHandle.handleException(throwable);
                    mView.onDownFileError(responeThrowable.message);
                }
            }, new Action() {
                @Override
                public void run() throws Exception {
                    mView.onDownFileSuccess("文件下载完成");
                }
            }));
        }
    }

    private void downLoad() {
        addDisposables(Flowable.fromIterable(tables)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, Flowable<XmlBean>>() {
                    @Override
                    public Flowable<XmlBean> apply(@NonNull String s) throws Exception {
                        Long tableBeginTime = System.currentTimeMillis();
                        tableDownTimeMap.put(s, tableBeginTime);
                        Log.d(TAG, "表" + s + "开始下载，开始时间=" + tableBeginTime + ",flatMap apply, Thread Name=" + Thread.currentThread().getName());
                        return mModel.getDownLoadTable(s);
                    }
                })
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(@NonNull Subscription subscription) throws Exception {
                        tableStartTime = System.currentTimeMillis();
                        Log.d(TAG, "doOnSubscribe accept, Thread Name=" + Thread.currentThread().getName());
                        mView.onShowDialog(0);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<XmlBean>() {
                    @Override
                    public void onNext(XmlBean xmlBean) {
                        Long tableEndTime = System.currentTimeMillis();
                        String tableName = xmlBean.getTableName();
                        if (tableDownTimeMap.containsKey(tableName)) {
                            Log.d(TAG, "表" + tableName + "下载完成，结束时间=" + tableEndTime + "，耗时=" + (tableEndTime - tableDownTimeMap.get(tableName)) + "ms" + ",所在线程=" + Thread.currentThread().getName());
                        }
                        resultXmlBeen.add(xmlBean);
                        mView.onShowDialog(100 / tables.size() * resultXmlBeen.size());
                    }

                    @Override
                    public void onError(Throwable t) {
                        ResponeThrowable res = ExceptionHandle.handleException(t);
                        Long tableEndTime = System.currentTimeMillis();
                        Log.d(TAG, "所有表数据下载完成，耗时=" + (tableEndTime - tableStartTime) + ",subscribeWith onError, 所在线程=" + Thread.currentThread().getName());
                        mView.showErrorWithStatus(res.message);
                    }

                    @Override
                    public void onComplete() {
                        Long tableEndTime = System.currentTimeMillis();
                        Long diffTime = tableEndTime - tableStartTime;
                        Log.d(TAG, "所有表数据下载完成，耗时=" + diffTime + ",subscribeWith onComplete, 所在线程=" + Thread.currentThread().getName());
                        mView.onTableDownLoadSuccess(diffTime);
                    }
                })
        );
    }
}
