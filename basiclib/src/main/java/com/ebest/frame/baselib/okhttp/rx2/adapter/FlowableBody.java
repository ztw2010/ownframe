package com.ebest.frame.baselib.okhttp.rx2.adapter;


import com.ebest.frame.baselib.okhttp.adapter.AdapterParam;
import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.adapter.CallAdapter;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class FlowableBody<T> implements CallAdapter<T, Flowable<T>> {
    @Override
    public Flowable<T> adapt(Call<T> call, AdapterParam param) {
        ObservableBody<T> observable = new ObservableBody<>();
        return observable.adapt(call, param).toFlowable(BackpressureStrategy.LATEST);
    }
}
