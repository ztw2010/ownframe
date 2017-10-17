package com.ebest.module.loginmodule.login;

import com.ebest.frame.baselib.okhttp.model.Progress;
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

        public void onDownFile(String msg);

        public void onDownFileError(String errorMsg);

        public void onDownFileSuccess(String msg);
    }

    interface Model extends BaseModel {
        Flowable<Progress> downFile(String fileUrl);

        Flowable<XmlBean> getDownLoadTable(String tableName);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getDownLoadTableData(List<String> tables);

        public abstract void downFile(String fileUrl);
    }
}
