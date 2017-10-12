package com.ebest.frame.baselib.okhttp.rx1.subscribe;


import com.ebest.frame.baselib.okhttp.model.Response;
import com.ebest.frame.baselib.okhttp.model.Result;

import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;
import rx.exceptions.OnCompletedFailedException;
import rx.exceptions.OnErrorFailedException;
import rx.exceptions.OnErrorNotImplementedException;
import rx.plugins.RxJavaHooks;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public final class ResultOnSubscribe<T> implements OnSubscribe<Result<T>> {
    private final OnSubscribe<Response<T>> upstream;

    public ResultOnSubscribe(OnSubscribe<Response<T>> upstream) {
        this.upstream = upstream;
    }

    @Override
    public void call(Subscriber<? super Result<T>> subscriber) {
        upstream.call(new ResultSubscriber<T>(subscriber));
    }

    private static class ResultSubscriber<R> extends Subscriber<Response<R>> {

        private final Subscriber<? super Result<R>> subscriber;

        ResultSubscriber(Subscriber<? super Result<R>> subscriber) {
            super(subscriber);
            this.subscriber = subscriber;
        }

        @Override
        public void onNext(Response<R> response) {
            subscriber.onNext(Result.response(response));
        }

        @Override
        public void onError(Throwable throwable) {
            try {
                subscriber.onNext(Result.<R>error(throwable));
            } catch (Throwable t) {
                try {
                    subscriber.onError(t);
                } catch (OnCompletedFailedException | OnErrorFailedException | OnErrorNotImplementedException e) {
                    RxJavaHooks.getOnError().call(e);
                } catch (Throwable inner) {
                    Exceptions.throwIfFatal(inner);
                    RxJavaHooks.getOnError().call(new CompositeException(t, inner));
                }
                return;
            }
            subscriber.onCompleted();
        }

        @Override
        public void onCompleted() {
            subscriber.onCompleted();
        }
    }
}
