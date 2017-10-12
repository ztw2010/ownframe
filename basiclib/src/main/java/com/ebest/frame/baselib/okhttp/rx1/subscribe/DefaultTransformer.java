package com.ebest.frame.baselib.okhttp.rx1.subscribe;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ztw on 2017/10/10.
 * 类描述：  预处理异常信息
 */

public class DefaultTransformer<T> implements Observable.Transformer<T, T> {

    @Override
    public Observable<T> call(Observable<T> httpResponseObservable) {
        return httpResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .compose(ErrorTransformer.<T>getInstance())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
