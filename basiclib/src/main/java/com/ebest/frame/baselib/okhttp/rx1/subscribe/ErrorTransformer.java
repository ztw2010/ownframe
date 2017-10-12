package com.ebest.frame.baselib.okhttp.rx1.subscribe;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by ztw on 2017/10/10.
 */

public class ErrorTransformer<T> implements Observable.Transformer<T, T> {

    public static <T> ErrorTransformer<T> create() {
        return new ErrorTransformer<>();
    }

    private static ErrorTransformer instance = null;

    private ErrorTransformer() {
    }

    public static <T> ErrorTransformer<T> getInstance() {
        if (instance == null) {
            synchronized (ErrorTransformer.class) {
                if (instance == null) {
                    instance = new ErrorTransformer();
                }
            }
        }
        return instance;
    }

    @Override
    public Observable<T> call(Observable<T> tObservable) {

        return tObservable.onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                throwable.printStackTrace();
                return Observable.error(ExceptionHandle.handleException(throwable));
            }
        });
    }
}
