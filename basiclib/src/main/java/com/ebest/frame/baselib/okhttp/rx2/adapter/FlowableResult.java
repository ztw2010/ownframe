package com.ebest.frame.baselib.okhttp.rx2.adapter;


import com.ebest.frame.baselib.okhttp.adapter.AdapterParam;
import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.adapter.CallAdapter;
import com.ebest.frame.baselib.okhttp.model.Result;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class FlowableResult<T> implements CallAdapter<T, Flowable<Result<T>>> {
    @Override
    public Flowable<Result<T>> adapt(Call<T> call, AdapterParam param) {
        ObservableResult<T> observable = new ObservableResult<>();
        return observable.adapt(call, param).toFlowable(BackpressureStrategy.LATEST);
    }
}
