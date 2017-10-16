package com.ebest.frame.baselib.okhttp.rx2.adapter;


import com.ebest.frame.baselib.okhttp.adapter.AdapterParam;
import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.adapter.CallAdapter;

import io.reactivex.Maybe;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class MaybeBody<T> implements CallAdapter<T, Maybe<T>> {
    @Override
    public Maybe<T> adapt(Call<T> call, AdapterParam param) {
        ObservableBody<T> observable = new ObservableBody<>();
        return observable.adapt(call, param).singleElement();
    }
}
