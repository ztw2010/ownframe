package com.ebest.frame.baselib.okhttp.rx2.adapter;

import com.ebest.frame.baselib.okhttp.adapter.AdapterParam;
import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.adapter.CallAdapter;
import com.ebest.frame.baselib.okhttp.model.Response;
import com.ebest.frame.baselib.okhttp.model.Result;
import com.ebest.frame.baselib.okhttp.rx2.observable.ResultObservable;

import io.reactivex.Observable;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class ObservableResult<T> implements CallAdapter<T, Observable<Result<T>>> {
    @Override
    public Observable<Result<T>> adapt(Call<T> call, AdapterParam param) {
        Observable<Response<T>> observable = AnalysisParams.analysis(call, param);
        return new ResultObservable<>(observable);
    }
}
