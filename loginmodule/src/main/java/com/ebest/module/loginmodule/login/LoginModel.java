package com.ebest.module.loginmodule.login;

import android.util.Log;

import com.ebest.frame.baselib.okhttp.OkGo;
import com.ebest.frame.baselib.okhttp.convert.XMLBeanConvert;
import com.ebest.frame.baselib.okhttp.model.Response;
import com.ebest.frame.baselib.okhttp.rx2.adapter.FlowableResponse;
import com.ebest.frame.baselib.xml.XmlBean;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * Created by ztw on 2017/10/10.
 */

public class LoginModel implements LoginContract.Model {

    private final String TAG = "LoginModel";

    @Override
    public Flowable<XmlBean> getDownLoadTableData(String tableName) {
        Log.d("LoginPresenter", "getDownLoadTableData 所在线程=" + Thread.currentThread().getName());
        return OkGo.<XmlBean>post("http://114.80.203.147:1458/frisosyncserver_business/download.aspx?user_code=1010009&pass=11111&version=V2&Gzip=0")
                .tag(tableName)
                .upString(tableName + ",0")
                .converter(new XMLBeanConvert())
                .adapt(new FlowableResponse<XmlBean>())
                .map(new Function<Response<XmlBean>, XmlBean>() {
                    @Override
                    public XmlBean apply(@NonNull Response<XmlBean> xmlBeanResponse) throws Exception {
                        return xmlBeanResponse.body();
                    }
                });
    }
}
