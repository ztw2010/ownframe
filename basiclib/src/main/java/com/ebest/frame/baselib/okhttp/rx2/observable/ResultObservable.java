package com.ebest.frame.baselib.okhttp.rx2.observable;


import com.ebest.frame.baselib.okhttp.model.Response;
import com.ebest.frame.baselib.okhttp.model.Result;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class ResultObservable<T> extends Observable<Result<T>> {
    private final Observable<Response<T>> upstream;

    public ResultObservable(Observable<Response<T>> upstream) {
        this.upstream = upstream;
    }

    @Override
    protected void subscribeActual(Observer<? super Result<T>> observer) {
        upstream.subscribe(new ResultObserver<T>(observer));
    }

    private static class ResultObserver<R> implements Observer<Response<R>> {
        private final Observer<? super Result<R>> observer;

        ResultObserver(Observer<? super Result<R>> observer) {
            this.observer = observer;
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            observer.onSubscribe(disposable);
        }

        @Override
        public void onNext(Response<R> response) {
            observer.onNext(Result.response(response));
        }

        @Override
        public void onError(Throwable throwable) {
            try {
                observer.onNext(Result.<R>error(throwable));
            } catch (Throwable t) {
                try {
                    observer.onError(t);
                } catch (Throwable inner) {
                    Exceptions.throwIfFatal(inner);
                    RxJavaPlugins.onError(new CompositeException(t, inner));
                }
                return;
            }
            observer.onComplete();
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }
    }
}
