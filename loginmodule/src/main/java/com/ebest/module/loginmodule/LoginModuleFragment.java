package com.ebest.module.loginmodule;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ebest.frame.baselib.excutor.SmartExecutor;
import com.ebest.frame.baselib.greendao.base.MobileBeanFactory;
import com.ebest.frame.baselib.greendao.dbmanager.FormDaoManager;
import com.ebest.frame.baselib.greendao.dbmanager.MeasureDaoManager;
import com.ebest.frame.baselib.greendao.dbmanager.StudentDaoManager;
import com.ebest.frame.baselib.handler.WeakHandler;
import com.ebest.frame.baselib.okhttp.OkGo;
import com.ebest.frame.baselib.okhttp.callback.StringCallback;
import com.ebest.frame.baselib.okhttp.model.Response;
import com.ebest.frame.baselib.okhttp.request.base.Request;
import com.ebest.frame.baselib.table.Form;
import com.ebest.frame.baselib.table.Measure;
import com.ebest.frame.baselib.table.Student;
import com.ebest.frame.baselib.xml.ParseXml;
import com.ebest.frame.baselib.xml.XmlBean;
import com.thoughtworks.xstream.XStream;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by abc on 2017/9/27.
 */

public class LoginModuleFragment extends Fragment implements View.OnClickListener {

    private View rootView;

    private Button insertDbBtn;

    private StudentDaoManager studentDBManager;

    private final int DOWN_TABLE = 10, DOWN_HAS_ERROR = 11, DOWN_TABLE_ONE_SUCCESS = 12, DOWN_TABLES_SUCCESS = 13, DOWN_BEGIN = 14;

    private final String TAG = "LoginModuleFragment";

    private Map<String, String> resultMap = new ConcurrentHashMap<String, String>();

    private List<String> tables = new CopyOnWriteArrayList<String>();

    private AtomicBoolean downFinish = new AtomicBoolean(false);

    private ProgressDialog progressDialog;

    private AtomicLong firstDown = new AtomicLong(0l);

    private AtomicLong lastDown = new AtomicLong(0l);

    private SmartExecutor mainExecutor;

    private AtomicBoolean hasError = new AtomicBoolean(false);

    private WeakHandler handler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_TABLE:
                    downLoadTable(msg.obj.toString());
                    break;
                case DOWN_HAS_ERROR:
                    if (hasError.get()) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        showDialog("同步数据失败", true);
                    }
                    break;
                case DOWN_TABLE_ONE_SUCCESS:
                    Bundle bundle = msg.getData();
                    if (bundle != null) {
                        String tableName = bundle.getString("tableName");
                        String tableResult = bundle.getString("responseBody");
                        progressDialog.setMessage("表数据下载中 " + 100 / tables.size() * resultMap.size() + "%");
                        resultMap.put(tableName, tableResult);
                    }
                    break;
                case DOWN_TABLES_SUCCESS:
                    if (downFinish.get()) {
                        progressDialog.dismiss();
                        showDialog("数据下载成功,耗时:" + (lastDown.get() - firstDown.get()), false);
                        writeDataToTable();
                    }
                    break;
                case DOWN_BEGIN:
                    if (!hasError.get() && !progressDialog.isShowing()) {
                        Long b = System.currentTimeMillis();
                        Log.d(TAG, "表第一次下载开始时间=" + b);
                        firstDown.set(b);
                        progressDialog.setMessage("表数据下载中 " + 0 + "%");
                        progressDialog.show();
                    }
                    break;
            }
            return true;
        }
    });

    private void writeDataToTable() {
        List<String> allTables = studentDBManager.getDbTables();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_loginmodule, container, false);
            studentDBManager = new StudentDaoManager();
            insertDbBtn = (Button) rootView.findViewById(R.id.insert_db);
            insertDbBtn.setOnClickListener(this);
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            mainExecutor = SmartExecutor.getInstance();
        }
        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (R.id.insert_db == id) {
            studentDBManager.deleteAll(Student.class);
            restoreState();
            initDownLoadTable();
            monitorResultMap();
            for (String table : tables) {
                Message msg = new Message();
                msg.what = DOWN_TABLE;
                msg.obj = table;
                handler.sendMessage(msg);
            }
        }
    }

    private void restoreState() {
        downFinish.set(false);
        tables.clear();
        resultMap.clear();
        hasError.set(false);
        firstDown.set(0l);
        lastDown.set(0l);
    }

    private void monitorResultMap() {
        mainExecutor.execute(new Runnable() {
            @Override
            public void run() {
                while (!downFinish.get()) {
                    if (resultMap.size() == tables.size() && resultMap.size() != 0) {
                        downFinish.set(true);
                        Long b = System.currentTimeMillis();
                        Log.d(TAG, "下载表数据结束时间=" + b);
                        lastDown.set(b);
                        Log.d(TAG, "所有表数据下载完成");
                        handler.sendEmptyMessage(DOWN_TABLES_SUCCESS);
                    }
                }
            }
        });
    }

    private void downLoadTable(final String tableName) {
        OkGo.<String>post("")
                .tag(tableName)
                .upString(tableName + ",0")
                .execute(new StringCallback() {

                    Long beginTime = 0l;

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        beginTime = System.currentTimeMillis();
                        Log.d(TAG, "表" + tableName + "开始下载,下载开始时间=" + beginTime);
                        handler.sendEmptyMessage(DOWN_BEGIN);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Long endTime = System.currentTimeMillis();
                        Log.d(TAG, "表" + tableName + "下载完成,时间=" + endTime + ",下载耗时=" + (endTime - beginTime));
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("responseBody", response.body());
                        bundle.putString("tableName", tableName);
                        message.setData(bundle);
                        message.what = DOWN_TABLE_ONE_SUCCESS;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Long endTime = System.currentTimeMillis();
                        Log.e(TAG, "表" + tableName + "下载失败,,时间=" + endTime + "下载耗时=" + (endTime - beginTime));
                        if (!hasError.get()) {
                            hasError.set(true);
                            handler.sendEmptyMessage(DOWN_HAS_ERROR);
                        }
                    }
                });
    }

    private List<String> initDownLoadTable() {
        tables.add("CUSTOMERCONTACT");
        tables.add("CUSTOMER");
        tables.add("ROUTEDETAILS");
        tables.add("DICTIONARYITEMS");
        tables.add("PRODUCT");
        tables.add("CUSTOMER_PROD_LIST_ITEMS");
        tables.add("CUSTOMER_PRODUCT_LISTS");
        tables.add("MEASURE");
        tables.add("MEASUREDETAIL");
        tables.add("MEASUREPROFILEDETAIL");
        tables.add("PRODUCT_UOMS");
        tables.add("CATEGORY");
        tables.add("BRAND");
        tables.add("FORM");
        tables.add("MOBILEUSER");
        tables.add("OUT_MESSAGE");
        tables.add("MEASURE_PROFILE_ROLE");
        tables.add("SYS_USER_ROLES");
        tables.add("SystemConfig");
        tables.add("CPR_Config");
        tables.add("UOMS");
        tables.add("ORG_CONFIG");
        tables.add("USER_SCORECARDS");
        tables.add("MDM_WHITE_LIST");
        tables.add("SELLING_STORY");
        tables.add("CUSTOMERCALLREVIEW");
        tables.add("MobileHomePage");
        tables.add("AttendanceLog");
        tables.add("SpecialAttendance");
        tables.add("Customer_SummaryInfo");
        tables.add("PersonOrganization");
        tables.add("PersonRelationship");
        tables.add("PersonRelationshipTask");
        tables.add("TaskPlan");
        tables.add("TaskDelegate");
        tables.add("ROUTECUSTOMERSDeleteInfo");
        tables.add("AuditRoute");
        tables.add("ExaminationTaskPlan");
        tables.add("ExaminationAttendance");
        tables.add("PersonRelationshipALL");
        tables.add("TeamVisit");
        tables.add("ManagerMeasureTransactionsReview");
        tables.add("TaskPlanTemp");
        tables.add("PersonRelationshipInspection");
        tables.add("PersonRelationshipMyteam");
        tables.add("PersonRelationshipExamination");
        tables.add("PerformanceInfo");
        tables.add("GuidanceInfoRelationship");
        tables.add("EXAMINATION");
        tables.add("EXAMINATION_DETAILS");
        tables.add("PersonRelationshipExaminationZS");
        tables.add("FMCAssess");
        tables.add("SyncInspectionVisitForLeader");
        tables.add("GuidanceInfoByDate");
        tables.add("GuidanceStatistics");
        tables.add("GuidanceInfoByDashboard");
        tables.add("SyncPerformanceTrackingForLeader");
        tables.add("SyncMyTeamForLeader");
        tables.add("SyncPerformanceTracking");
        tables.add("Ele_GradeRule");
        tables.add("Ele_Learning");
        tables.add("Ele_Ranking");
        tables.add("Ele_TestPaper");
        tables.add("Ele_PersonGold_Register");
        tables.add("Ele_LearningTracking");
        tables.add("Ele_Activity");
        tables.add("Ele_ActivityDetailed");
        tables.add("Ele_ActivityStateDetailed");
        tables.add("Ele_ActivityReachRate");
        tables.add("Ele_ActivityManagerDetailed");
        tables.add("ActivityCustomerSummaryInfo");
        tables.add("Verystar_User");
        tables.add("customer_manager_summaryInfo");
        tables.add("Verystar_Ranking");
        tables.add("Verystar_Reward");
        tables.add("Verystar_Achievements");
        tables.add("Verystar_RewardInfo");
        tables.add("Verystar_RewardDetailed");
        tables.add("Verystar_RewardImg");
        tables.add("Verystar_ShowInfo");
        tables.add("Verystar_Img");
        tables.add("Verystar_ShowVote");
        tables.add("LoveSharingInfo");
        tables.add("Verystar_ArticleInfo");
        tables.add("Verystar_ArticleVote");
        tables.add("UserNoticeInfo");
        tables.add("Verystar_ArticleComment");
        tables.add("Verystar_RuleInfo");
        tables.add("ANSWERSHEET");
        tables.add("ANSWERSHEETDETAIL");
        return tables;
    }
}
