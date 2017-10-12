package com.ebest.frame.baselib.okhttp.rx1.subscribe;

import rx.Subscriber;

/**
 * Created by ztw on 2017/10/11.
 */

public abstract class ErrorSubscriber<T> extends Subscriber<T> {

    @Override
    public void onError(Throwable e) {
        if (e instanceof ResponeThrowable) {
            onError((ResponeThrowable) e);
        } else {
            onError(new ResponeThrowable(e, 1000));
        }
    }

    /**
     * 错误回调
     */
    protected abstract void onError(ResponeThrowable ex);
}
