package com.ebest.module.loginmodule.login;

import android.util.Log;

import com.ebest.frame.baselib.constant.C;
import com.ebest.frame.baselib.okhttp.OkGo;
import com.ebest.frame.baselib.okhttp.callback.FileCallback;
import com.ebest.frame.baselib.okhttp.convert.XMLBeanConvert;
import com.ebest.frame.baselib.okhttp.model.Progress;
import com.ebest.frame.baselib.okhttp.model.Response;
import com.ebest.frame.baselib.okhttp.rx2.adapter.FlowableResponse;
import com.ebest.frame.baselib.okhttp.rx2.process.RxUtil;
import com.ebest.frame.baselib.xml.XmlBean;

import java.io.File;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * Created by ztw on 2017/10/10.
 */

public class LoginModel implements LoginContract.Model {

    private final String TAG = "LoginModel";

    @Override
    public Flowable<Progress> downFile(final String fileUrl) {
        return Flowable.create(new FlowableOnSubscribe<Progress>() {
            @Override
            public void subscribe(@NonNull final FlowableEmitter<Progress> e) throws Exception {
                OkGo.<File>get(fileUrl).execute(new FileCallback() {

                    @Override
                    public void onSuccess(Response<File> response) {
                        e.onComplete();
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        e.onError(response.getException());
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        Log.d(TAG, "downloadProgress progress=" + progress);
                        e.onNext(progress);
                    }
                });
            }
        }, BackpressureStrategy.LATEST)
                .compose(RxUtil.<Progress>rxSchedulerHelper());
    }

    @Override
    public Flowable<XmlBean> getDownLoadTable(String tableName) {
        return OkGo.<XmlBean>post(C.TABLE_URL)
                .tag(tableName)
                .upString(tableName + ",0")
                .converter(new XMLBeanConvert())
                .adapt(new FlowableResponse<XmlBean>())
                .map(new Function<Response<XmlBean>, XmlBean>() {
                    @Override
                    public XmlBean apply(@NonNull Response<XmlBean> xmlBeanResponse) throws Exception {
                        return xmlBeanResponse.body();
                    }
                });
    }
}
