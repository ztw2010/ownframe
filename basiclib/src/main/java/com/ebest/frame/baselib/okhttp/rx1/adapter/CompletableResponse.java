package com.ebest.frame.baselib.okhttp.rx1.adapter;


import com.ebest.frame.baselib.okhttp.adapter.AdapterParam;
import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.adapter.CallAdapter;

import rx.Completable;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class CompletableResponse<T> implements CallAdapter<T, Completable> {
    @Override
    public Completable adapt(Call<T> call, AdapterParam param) {
        ObservableResponse<T> body = new ObservableResponse<>();
        return body.adapt(call, param).toCompletable();
    }
}
