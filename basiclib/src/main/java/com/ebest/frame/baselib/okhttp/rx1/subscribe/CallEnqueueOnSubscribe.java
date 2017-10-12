package com.ebest.frame.baselib.okhttp.rx1.subscribe;


import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.callback.Callback;
import com.ebest.frame.baselib.okhttp.model.Progress;
import com.ebest.frame.baselib.okhttp.model.Response;
import com.ebest.frame.baselib.okhttp.request.base.Request;

import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.exceptions.Exceptions;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public final class CallEnqueueOnSubscribe<T> implements OnSubscribe<Response<T>> {
    private final Call<T> originalCall;

    public CallEnqueueOnSubscribe(Call<T> originalCall) {
        this.originalCall = originalCall;
    }

    @Override
    public void call(final Subscriber<? super Response<T>> subscriber) {
        // Since Call is a one-shot type, clone it for each new subscriber.
        Call<T> call = originalCall.clone();
        final CallArbiter<T> arbiter = new CallArbiter<>(call, subscriber);
        subscriber.add(arbiter);
        subscriber.setProducer(arbiter);

        call.execute(new Callback<T>() {
            @Override
            public T convertResponse(okhttp3.Response response) throws Throwable {
                // okrx 使用converter转换，不需要这个解析方法
                return null;
            }

            @Override
            public void onStart(Request<T, ? extends Request> request) {
            }

            @Override
            public void onSuccess(Response<T> response) {
                arbiter.emitNext(response);
            }

            @Override
            public void onCacheSuccess(Response<T> response) {
                arbiter.emitNext(response);
            }

            @Override
            public void onError(Response<T> response) {
                Throwable throwable = response.getException();
                Exceptions.throwIfFatal(throwable);
                arbiter.emitError(throwable);
            }

            @Override
            public void onFinish() {
                arbiter.emitComplete();
            }

            @Override
            public void uploadProgress(Progress progress) {
            }

            @Override
            public void downloadProgress(Progress progress) {
            }
        });
    }
}
