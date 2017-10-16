package com.ebest.frame.baselib.okhttp.rx2.adapter;


import com.ebest.frame.baselib.okhttp.adapter.AdapterParam;
import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.adapter.CallAdapter;
import com.ebest.frame.baselib.okhttp.model.Result;

import io.reactivex.Single;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class SingleResult<T> implements CallAdapter<T, Single<Result<T>>> {
    @Override
    public Single<Result<T>> adapt(Call<T> call, AdapterParam param) {
        ObservableResult<T> observable = new ObservableResult<>();
        return observable.adapt(call, param).singleOrError();
    }
}
