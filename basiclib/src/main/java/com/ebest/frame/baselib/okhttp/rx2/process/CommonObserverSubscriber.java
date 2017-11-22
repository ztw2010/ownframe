package com.ebest.frame.baselib.okhttp.rx2.process;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.ResourceObserver;

/**
 * Created by abc on 2017/11/1.
 */

public abstract class CommonObserverSubscriber<T> extends ResourceObserver<T> {

    @Override
    protected void onStart() {
        super.onStart();
        doOnStart();
    }

    @Override
    public void onNext(@NonNull T t) {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        ResponeThrowable responeThrowable = ExceptionHandle.handleException(e);
        doOnError(responeThrowable);
    }

    @Override
    public void onComplete() {

    }

    protected abstract void doOnStart();

    protected abstract void doOnError(ResponeThrowable responeThrowable);
}
