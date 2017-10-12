package com.ebest.frame.baselib.okhttp.rx1.adapter;


import com.ebest.frame.baselib.okhttp.adapter.AdapterParam;
import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.adapter.CallAdapter;
import com.ebest.frame.baselib.okhttp.model.Response;

import rx.Single;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class SingleResponse<T> implements CallAdapter<T, Single<Response<T>>> {
    @Override
    public Single<Response<T>> adapt(Call<T> call, AdapterParam param) {
        ObservableResponse<T> body = new ObservableResponse<>();
        return body.adapt(call, param).toSingle();
    }
}
