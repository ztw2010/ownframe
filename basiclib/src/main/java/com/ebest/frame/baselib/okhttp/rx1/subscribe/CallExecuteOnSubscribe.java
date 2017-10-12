package com.ebest.frame.baselib.okhttp.rx1.subscribe;

import com.ebest.frame.baselib.okhttp.adapter.Call;
import com.ebest.frame.baselib.okhttp.model.Response;

import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.exceptions.Exceptions;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public final class CallExecuteOnSubscribe<T> implements OnSubscribe<Response<T>> {
    private final Call<T> originalCall;

    public CallExecuteOnSubscribe(Call<T> originalCall) {
        this.originalCall = originalCall;
    }

    @Override
    public void call(Subscriber<? super Response<T>> subscriber) {
        // Since Call is a one-shot type, clone it for each new subscriber.
        Call<T> call = originalCall.clone();
        CallArbiter<T> arbiter = new CallArbiter<>(call, subscriber);
        subscriber.add(arbiter);
        subscriber.setProducer(arbiter);

        Response<T> response;
        try {
            response = call.execute();
        } catch (Throwable t) {
            Exceptions.throwIfFatal(t);
            arbiter.emitError(t);
            return;
        }
        arbiter.emitNext(response);
        arbiter.emitComplete();
    }
}
