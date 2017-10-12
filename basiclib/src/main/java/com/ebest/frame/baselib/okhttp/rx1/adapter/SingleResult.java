package com.ebest.frame.baselib.okhttp.rx1.adapter;


import com.ebest.frame.baselib.okhttp.adapter.AdapterParam;
import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.adapter.CallAdapter;
import com.ebest.frame.baselib.okhttp.model.Result;

import rx.Single;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class SingleResult<T> implements CallAdapter<T, Single<Result<T>>> {
    @Override
    public Single<Result<T>> adapt(Call<T> call, AdapterParam param) {
        ObservableResult<T> body = new ObservableResult<>();
        return body.adapt(call, param).toSingle();
    }
}
