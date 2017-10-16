package com.ebest.frame.baselib.okhttp.rx2.adapter;


import com.ebest.frame.baselib.okhttp.adapter.AdapterParam;
import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.adapter.CallAdapter;

import io.reactivex.Completable;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class CompletableResponse<T> implements CallAdapter<T, Completable> {
    @Override
    public Completable adapt(Call<T> call, AdapterParam param) {
        ObservableResponse<T> observable = new ObservableResponse<>();
        return observable.adapt(call, param).ignoreElements();
    }
}
