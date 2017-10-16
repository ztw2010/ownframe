package com.ebest.frame.commnetservicve.mvp;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by ztw on 2017/10/10.
 */

public class BasePresenter<V extends BaseView, M extends BaseModel> implements Presenter<V, M> {

    protected Context mContext;

    protected V mView;

    protected M mModel;

    protected CompositeDisposable compositeDisposable;

    protected String TAG = "";

    public void addDisposables(Disposable disposable) {
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void disposes() {
        if (compositeDisposable != null)
            compositeDisposable.clear();
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
        disposes();
    }

    public V getView() {
        return mView;
    }

    public boolean isViewBind() {
        return mView != null;
    }
}
