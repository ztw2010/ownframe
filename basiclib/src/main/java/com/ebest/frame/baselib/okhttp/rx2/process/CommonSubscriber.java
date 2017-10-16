package com.ebest.frame.baselib.okhttp.rx2.process;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by ztw on 2017/10/16.
 */

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {

    @Override
    protected void onStart() {
        super.onStart();
        doOnStart();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        ResponeThrowable responeThrowable = ExceptionHandle.handleException(e);
        doOnError(responeThrowable);
    }

    @Override
    public void onNext(T t) {

    }

    protected abstract void doOnStart();

    protected abstract void doOnError(ResponeThrowable responeThrowable);
}
