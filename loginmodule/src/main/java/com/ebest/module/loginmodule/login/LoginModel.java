package com.ebest.module.loginmodule.login;

import com.ebest.frame.baselib.okhttp.OkGo;
import com.ebest.frame.baselib.okhttp.convert.XMLBeanConvert;
import com.ebest.frame.baselib.okhttp.model.Response;
import com.ebest.frame.baselib.okhttp.rx1.adapter.ObservableResponse;
import com.ebest.frame.baselib.okhttp.rx1.subscribe.DefaultTransformer;
import com.ebest.frame.baselib.xml.XmlBean;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ztw on 2017/10/10.
 */

public class LoginModel implements LoginContract.Model {

    private final String TAG = "LoginModel";

    @Override
    public Observable<Response<XmlBean>> getDownLoadTableData(String tableName) {
        return OkGo.<XmlBean>post("http://114.80.203.147:1458/frisosyncserver_business/download.aspx?user_code=1010009&pass=11111&version=V2&Gzip=0")
                .tag(tableName)
                .upString(tableName + ",0")
                .converter(new XMLBeanConvert())
                .adapt(new ObservableResponse<XmlBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new DefaultTransformer<Response<XmlBean>>());
    }
}
