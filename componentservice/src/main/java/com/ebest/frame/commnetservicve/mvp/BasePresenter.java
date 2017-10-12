package com.ebest.frame.commnetservicve.mvp;

import android.content.Context;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ztw on 2017/10/10.
 */

public class BasePresenter<V extends BaseView, M extends BaseModel> implements Presenter<V, M> {

    protected Context mContext;

    protected V mView;

    protected M mModel;

    protected CompositeSubscription mCompositeSubscription;

    protected String TAG = "";

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    //    获取绑定View实例
    @Override
    public void attachView(V view) {
        this.mView = view;
        this.TAG = this.getClass().getName();
    }

    //    获取绑定Model层实例
    @Override
    public void attachModel(M m) {
        this.mModel = m;
    }


    public M getModel() {
        return mModel;
    }

    //    注销mModel实例
    @Override
    public void detachModel() {
        this.mModel = null;
    }

    //    注销View实例
    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }

    public V getView() {
        return mView;
    }

    public boolean isViewBind() {
        return mView != null;
    }
}
