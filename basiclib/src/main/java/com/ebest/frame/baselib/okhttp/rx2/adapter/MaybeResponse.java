package com.ebest.frame.baselib.okhttp.rx2.adapter;


import com.ebest.frame.baselib.okhttp.adapter.AdapterParam;
import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.adapter.CallAdapter;
import com.ebest.frame.baselib.okhttp.model.Response;

import io.reactivex.Maybe;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class MaybeResponse<T> implements CallAdapter<T, Maybe<Response<T>>> {
    @Override
    public Maybe<Response<T>> adapt(Call<T> call, AdapterParam param) {
        ObservableResponse<T> observable = new ObservableResponse<>();
        return observable.adapt(call, param).singleElement();
    }
}
