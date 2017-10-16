package com.ebest.module.loginmodule.login;

import com.ebest.frame.baselib.xml.XmlBean;
import com.ebest.frame.commnetservicve.mvp.BaseModel;
import com.ebest.frame.commnetservicve.mvp.BasePresenter;
import com.ebest.frame.commnetservicve.mvp.BaseView;

import java.util.List;

import io.reactivex.Flowable;


/**
 * Created by ztw on 2017/10/10.
 */

public interface LoginContract {

    interface View extends BaseView {

        public void onShowDialog(int progress);

        public void onTableDownLoadSuccess(Long diffTime);
    }

    interface Model extends BaseModel {
        Flowable<XmlBean> getDownLoadTableData(String tableName);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getDownLoadTableData(List<String> tables);
    }
}
