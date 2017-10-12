package com.ebest.frame.baselib.okhttp.rx1.subscribe;

/**
 * Created by ztw on 2017/10/11.
 */

public  abstract class RxSubscriber<T> extends ErrorSubscriber<T> {

    /**
     *  开始请求网络,可以弹出提示框等需要在UI线程进行的操作
     */
    @Override
    public void onStart() {
        super.onStart();
    }
    /**
     *   请求网络完成
     */
    @Override
    public void onCompleted() {

    }

    /**
     *  获取网络数据
     * @param t
     */
    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    public abstract  void onSuccess(T t);

}
