package com.ebest.frame.baselib.okhttp.rx1.adapter;

import com.ebest.frame.baselib.okhttp.adapter.AdapterParam;
import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.adapter.CallAdapter;

import rx.Single;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class SingleBody<T> implements CallAdapter<T, Single<T>> {
    @Override
    public Single<T> adapt(Call<T> call, AdapterParam param) {
        ObservableBody<T> body = new ObservableBody<>();
        return body.adapt(call, param).toSingle();
    }
}
