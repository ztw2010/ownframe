package com.ebest.frame.baselib.okhttp.rx1.adapter;


import com.ebest.frame.baselib.okhttp.adapter.AdapterParam;
import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.adapter.CallAdapter;
import com.ebest.frame.baselib.okhttp.model.Response;
import com.ebest.frame.baselib.okhttp.model.Result;
import com.ebest.frame.baselib.okhttp.rx1.subscribe.ResultOnSubscribe;

import rx.Observable;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class ObservableResult<T> implements CallAdapter<T, Observable<Result<T>>> {
    @Override
    public Observable<Result<T>> adapt(Call<T> call, AdapterParam param) {
        Observable.OnSubscribe<Response<T>> subscribe = AnalysisParams.analysis(call, param);
        ResultOnSubscribe<T> resultSubscribe = new ResultOnSubscribe<>(subscribe);
        return Observable.create(resultSubscribe);
    }
}
