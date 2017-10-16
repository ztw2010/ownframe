package com.ebest.module.loginmodule.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.ebest.frame.baselib.greendao.base.MobileBeanFactory;
import com.ebest.frame.baselib.greendao.dbmanager.FormDaoManager;
import com.ebest.frame.baselib.greendao.dbmanager.MeasureDaoManager;
import com.ebest.frame.baselib.okhttp.OkGo;
import com.ebest.frame.baselib.table.Form;
import com.ebest.frame.baselib.table.Measure;
import com.ebest.frame.baselib.xml.ParseXml;
import com.ebest.frame.baselib.xml.XmlBean;
import com.ebest.frame.commnetservicve.mvp.BaseFragment;
import com.ebest.frame.commnetservicve.mvp.BaseView;
import com.ebest.module.loginmodule.R;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by abc on 2017/9/27.
 */

public class LoginModuleFragment extends BaseFragment<LoginModel, LoginPresenter> implements View.OnClickListener, LoginContract.View {

    private Button insertDbBtn;

    private final String TAG = "LoginModuleFragment";

    private Map<String, String> resultMap = new ConcurrentHashMap<String, String>();

    private List<String> tables = new ArrayList<>();

    private ProgressDialog progressDialog;

    private void writeDataToTable() {
        List<String> allTables = MobileBeanFactory.getInstance().getDaoManagerByBeanClass(Measure.class).getDbTables();
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!TextUtils.isEmpty(value) && allTables.contains(key)) {
                XmlBean xmlBean = ParseXml.toXmlBean(value);
                String standardXML = xmlBean.getStandardXML();
                if (key.equalsIgnoreCase(Measure.class.getSimpleName())) {
                    XStream xStream = new XStream();
                    xStream.processAnnotations(Measure.class);
                    Measure baseXML = (Measure) xStream.fromXML(standardXML);
                    MeasureDaoManager measureDaoManager = MobileBeanFactory.getInstance().getDaoManagerByBeanClass(Measure.class);
                    measureDaoManager.insertMultObject(baseXML.getValues());
                } else if (key.equalsIgnoreCase(Form.class.getSimpleName())) {
                    XStream xStream = new XStream();
                    xStream.processAnnotations(Form.class);
                    Form baseXML = (Form) xStream.fromXML(standardXML);
                    FormDaoManager formDaoManager = MobileBeanFactory.getInstance().getDaoManagerByBeanClass(Form.class);
                    formDaoManager.insertMultObject(baseXML.getValues());
                }
            }
        }
    }

    private void showDialog(final String msg, final boolean isCancleTag) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("提示")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isCancleTag) {
                            for (String tag : tables) {
                                OkGo.getInstance().cancelTag(tag);
                            }
                        }
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_loginmodule;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        insertDbBtn = (Button) rootView.findViewById(R.id.insert_db);
        insertDbBtn.setOnClickListener(this);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onEvent() {

    }

    @Override
    protected BaseView getViewImp() {
        return this;
    }

    @Override
    protected void lazyFetchData() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (R.id.insert_db == id) {
            initDownLoadTable();
            mPresenter.getDownLoadTableData(tables);
        }
    }


    private List<String> initDownLoadTable() {
        tables.clear();
        tables.add("CUSTOMERCONTACT");
//        tables.add("CUSTOMER");
//        tables.add("ROUTEDETAILS");
//        tables.add("DICTIONARYITEMS");
//        tables.add("PRODUCT");
//        tables.add("CUSTOMER_PROD_LIST_ITEMS");
//        tables.add("CUSTOMER_PRODUCT_LISTS");
//        tables.add("MEASURE");
//        tables.add("MEASUREDETAIL");
//        tables.add("MEASUREPROFILEDETAIL");
//        tables.add("PRODUCT_UOMS");
//        tables.add("CATEGORY");
//        tables.add("BRAND");
//        tables.add("FORM");
//        tables.add("MOBILEUSER");
//        tables.add("OUT_MESSAGE");
//        tables.add("MEASURE_PROFILE_ROLE");
//        tables.add("SYS_USER_ROLES");
//        tables.add("SystemConfig");
//        tables.add("CPR_Config");
//        tables.add("UOMS");
//        tables.add("ORG_CONFIG");
//        tables.add("USER_SCORECARDS");
//        tables.add("MDM_WHITE_LIST");
//        tables.add("SELLING_STORY");
//        tables.add("CUSTOMERCALLREVIEW");
//        tables.add("MobileHomePage");
//        tables.add("AttendanceLog");
//        tables.add("SpecialAttendance");
//        tables.add("Customer_SummaryInfo");
//        tables.add("PersonOrganization");
//        tables.add("PersonRelationship");
//        tables.add("PersonRelationshipTask");
//        tables.add("TaskPlan");
//        tables.add("TaskDelegate");
//        tables.add("ROUTECUSTOMERSDeleteInfo");
//        tables.add("AuditRoute");
//        tables.add("ExaminationTaskPlan");
//        tables.add("ExaminationAttendance");
//        tables.add("PersonRelationshipALL");
//        tables.add("ManagerMeasureTransactionsReview");
//        tables.add("TaskPlanTemp");
//        tables.add("PersonRelationshipInspection");
//        tables.add("PersonRelationshipMyteam");
//        tables.add("PersonRelationshipExamination");
//        tables.add("GuidanceInfoRelationship");
//        tables.add("EXAMINATION");
//        tables.add("EXAMINATION_DETAILS");
//        tables.add("PersonRelationshipExaminationZS");
//        tables.add("GuidanceStatistics");
//        tables.add("SyncPerformanceTrackingForLeader");
//        tables.add("SyncPerformanceTracking");
//        tables.add("Ele_GradeRule");
//        tables.add("Ele_Learning");
//        tables.add("Ele_Ranking");
//        tables.add("Ele_TestPaper");
//        tables.add("Ele_PersonGold_Register");
//        tables.add("Ele_LearningTracking");
//        tables.add("Ele_ActivityStateDetailed");
//        tables.add("Ele_ActivityReachRate");
//        tables.add("Ele_ActivityManagerDetailed");
//        tables.add("ActivityCustomerSummaryInfo");
//        tables.add("Verystar_User");
//        tables.add("customer_manager_summaryInfo");
//        tables.add("Verystar_Ranking");
//        tables.add("Verystar_Reward");
//        tables.add("Verystar_Achievements");
//        tables.add("Verystar_RewardInfo");
//        tables.add("Verystar_RewardDetailed");
//        tables.add("Verystar_RewardImg");
//        tables.add("Verystar_ShowInfo");
//        tables.add("Verystar_Img");
//        tables.add("Verystar_ShowVote");
//        tables.add("LoveSharingInfo");
//        tables.add("Verystar_ArticleInfo");
//        tables.add("Verystar_ArticleVote");
//        tables.add("UserNoticeInfo");
//        tables.add("Verystar_ArticleComment");
//        tables.add("Verystar_RuleInfo");
//        tables.add("ANSWERSHEET");
//        tables.add("ANSWERSHEETDETAIL");
        return tables;
    }

    @Override
    public void showErrorWithStatus(String msg) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        showDialog(msg, true);
    }

    @Override
    public void onShowDialog(int progress) {
        if (0 == progress) {
            progressDialog.show();
        }
        progressDialog.setMessage("表数据下载中 " + progress + "%");
    }

    @Override
    public void onTableDownLoadSuccess(Long diffTime) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        showDialog("表数据下载完成,耗时:" + diffTime + "ms", false);
    }
}
