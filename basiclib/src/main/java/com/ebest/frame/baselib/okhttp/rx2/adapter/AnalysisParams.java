package com.ebest.frame.baselib.okhttp.rx2.adapter;


import com.ebest.frame.baselib.okhttp.adapter.AdapterParam;
import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.model.Response;
import com.ebest.frame.baselib.okhttp.rx2.observable.CallEnqueueObservable;
import com.ebest.frame.baselib.okhttp.rx2.observable.CallExecuteObservable;

import io.reactivex.Observable;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
class AnalysisParams {

    static <T> Observable<Response<T>> analysis(Call<T> call, AdapterParam param) {
        Observable<Response<T>> observable;
        if (param == null) param = new AdapterParam();
        if (param.isAsync) {
            observable = new CallEnqueueObservable<>(call);
        } else {
            observable = new CallExecuteObservable<>(call);
        }
        return observable;
    }
}
