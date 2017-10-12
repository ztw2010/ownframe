package com.ebest.frame.baselib.okhttp.rx1.adapter;


import com.ebest.frame.baselib.okhttp.adapter.AdapterParam;
import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.adapter.CallAdapter;
import com.ebest.frame.baselib.okhttp.model.Response;
import com.ebest.frame.baselib.okhttp.rx1.subscribe.BodyOnSubscribe;

import rx.Observable;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class ObservableBody<T> implements CallAdapter<T, Observable<T>> {
    @Override
    public Observable<T> adapt(Call<T> call, AdapterParam param) {
        Observable.OnSubscribe<Response<T>> subscribe = AnalysisParams.analysis(call, param);
        BodyOnSubscribe<T> bodySubscribe = new BodyOnSubscribe<>(subscribe);
        return Observable.create(bodySubscribe);
    }
}
