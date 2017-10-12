package com.ebest.frame.baselib.okhttp.rx1.adapter;

import com.ebest.frame.baselib.okhttp.adapter.AdapterParam;
import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.adapter.CallAdapter;
import com.ebest.frame.baselib.okhttp.model.Response;

import rx.Observable;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class ObservableResponse<T> implements CallAdapter<T, Observable<Response<T>>> {
    @Override
    public Observable<Response<T>> adapt(Call<T> call, AdapterParam param) {
        Observable.OnSubscribe<Response<T>> subscribe = AnalysisParams.analysis(call, param);
        return Observable.create(subscribe);
    }
}
