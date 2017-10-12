package com.ebest.frame.baselib.okhttp.rx1.adapter;


import com.ebest.frame.baselib.okhttp.adapter.AdapterParam;
import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.model.Response;
import com.ebest.frame.baselib.okhttp.rx1.subscribe.CallEnqueueOnSubscribe;
import com.ebest.frame.baselib.okhttp.rx1.subscribe.CallExecuteOnSubscribe;

import rx.Observable;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
class AnalysisParams {

    static <T> Observable.OnSubscribe<Response<T>> analysis(Call<T> call, AdapterParam param) {
        Observable.OnSubscribe<Response<T>> onSubscribe;
        if (param == null) param = new AdapterParam();
        if (param.isAsync) {
            onSubscribe = new CallEnqueueOnSubscribe<>(call);
        } else {
            onSubscribe = new CallExecuteOnSubscribe<>(call);
        }
        return onSubscribe;
    }
}
