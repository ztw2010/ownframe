package com.ebest.frame;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ebest.frame.annomationapilib.aop.Permission;
import com.ebest.frame.annomationapilib.parama.BundleFactory;
import com.ebest.frame.annomationapilib.parama.Parceler;
import com.ebest.frame.annomationapilib.route.Router;
import com.ebest.frame.annomationlib.parama.annomation.FastJsonConverter;
import com.ebest.frame.bean.Book;
import com.ebest.frame.bean.BundleInfo;
import com.ebest.frame.bean.Info;
import com.ebest.frame.commnetservicve.base.BaseActivity;
import com.ebest.frame.commnetservicve.base.ComponentService;
import com.ebest.frame.commnetservicve.loginmoduleservice.IFragmentService;
import com.ebest.frame.coverter.AutoJsonConverter;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends BaseActivity {

    private Button goLoginBtn, goLoginBtn2, toBundleCommon, toBundleConverter, toEntityConverter, toBundleActivity,
            toBundleWithArrayList, toBundleWithSpareArray, route_btn;

    private FrameLayout container;

    private Fragment otherModuleFragment;

    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parceler.setDefaultConverter(AutoJsonConverter.class);
        goLoginBtn = (Button) findViewById(R.id.go_login_tv);
        goLoginBtn.setOnClickListener(this);
        goLoginBtn2 = (Button) findViewById(R.id.multi_login_tv);
        goLoginBtn2.setOnClickListener(this);
        container = (FrameLayout) findViewById(R.id.container);
        toBundleCommon = (Button) findViewById(R.id.toBundleCommon);
        toBundleCommon.setOnClickListener(this);
        toBundleConverter = (Button) findViewById(R.id.toBundleConverter);
        toBundleConverter.setOnClickListener(this);
        toEntityConverter = (Button) findViewById(R.id.toEntityConverter);
        toEntityConverter.setOnClickListener(this);
        toBundleActivity = (Button) findViewById(R.id.toBundleActivity);
        toBundleActivity.setOnClickListener(this);
        toBundleWithArrayList = (Button) findViewById(R.id.toBundleWithArrayList);
        toBundleWithArrayList.setOnClickListener(this);
        toBundleWithSpareArray = (Button) findViewById(R.id.toBundleWithSpareArray);
        toBundleWithSpareArray.setOnClickListener(this);
        route_btn = (Button) findViewById(R.id.route_btn);
        route_btn.setOnClickListener(this);
        showFragment();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.go_login_tv:
                gotoOtherModule();
                break;
            case R.id.multi_login_tv:
                Bundle extras = new Bundle();
                extras.putString("username", "zzzz");
                extras.putString("password", "lzh");
                extras.putString("usertype", "VIP");
                Router.create("loginmodule://main/LoginActivity")
                        .getActivityRoute()
                        .addExtras(extras)// 添加额外参数
                        .requestCode(100)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .setAnim(R.anim.basicres_anim_fade_in, R.anim.basicres_anim_fade_out)
                        .open(this);
                break;
            case R.id.toBundleCommon:
                Bundle bundle = Parceler.toBundle(new BundleInfo(), new Bundle());
                printBundle(bundle);
                break;
            case R.id.toBundleConverter:
                Book book = new Book("颈椎病康复指南", 2.50f);
                Bundle bundle2 = Parceler.createFactory(new Bundle())
                        .setConverter(FastJsonConverter.class)
                        .put("book", book)
                        .getBundle();
                printBundle(bundle2);
                break;
            case R.id.toEntityConverter:
                Book book3 = new Book("颈椎病康复指南", 2.50f);
                int age = 3;
                ArrayList<Book> books = new ArrayList<>();
                books.add(book3);
                Bundle bundle3 = Parceler.createFactory(new Bundle())
                        .put("book", book3)
                        .put("books", books)
                        .put("age", age)
                        .getBundle();
                BundleFactory factory = Parceler.createFactory(bundle3);
                book3 = factory.get("book", Book.class);
                System.out.println("book = " + book3);
                break;
            case R.id.toBundleActivity:
                Bundle bundle4 = Parceler.createFactory(new Bundle())
                        .put("builder", new StringBuilder("StringBuilder"))
                        .put("buffer", new StringBuffer("StringBuffer"))
                        .put("books", new Book[]{new Book(), new Book()})
                        .put("bytes", new Byte[]{1, 2, 3, 4})
                        .put("uri", Uri.parse("http://www.baidu.com"))
                        .put("age", 1)
                        .getBundle();
                Intent intent = new Intent(this, BundleActivity.class);
                intent.putExtras(bundle4);
                startActivity(intent);
                break;
            case R.id.toBundleWithArrayList:
                // 可被直接放入bundle中的ArrayList
                ArrayList emptyList = new ArrayList();
                ArrayList<Integer> integerArrayList = new ArrayList<>();
                integerArrayList.add(1);
                integerArrayList.add(2);
                ArrayList<CharSequence> charSequenceArrayList = new ArrayList<>();
                charSequenceArrayList.add(new StringBuffer("buffer"));
                charSequenceArrayList.add(new StringBuilder("builder"));
                ArrayList<Parcelable> parcelableArrayList = new ArrayList<>();
                parcelableArrayList.add(new Info());
                ArrayList<String> stringArrayList = new ArrayList<>();
                stringArrayList.add("hello");
                stringArrayList.add("world");
                // 不可被直接放入bundle中的ArrayList
                ArrayList<Book> booksa = new ArrayList<>();
                booksa.add(new Book("时间简史", 25.3f));

                Bundle bundlea = Parceler.createFactory(null)
                        .put("empty", emptyList)
                        .put("integers", integerArrayList)
                        .put("charSequences", charSequenceArrayList)
                        .put("parcelabes", parcelableArrayList)
                        .put("strings", stringArrayList)
                        .put("books", booksa)
                        .getBundle();
                printBundle(bundlea);
                break;
            case R.id.toBundleWithSpareArray:
                // 可被直接放入bundle中的SpareArray
                SparseArray empty = new SparseArray();
                SparseArray<Parcelable> parcelableSparseArray = new SparseArray<>();
                parcelableSparseArray.put(3, new Info());
                SparseArray<Book> booksb = new SparseArray<>();
                booksb.put(1, new Book("时间简史", 25.3f));
                Bundle bundleb = Parceler.createFactory(null)
                        .put("empty", empty)
                        .put("parcelables", parcelableSparseArray)
                        .put("books", booksb)
                        .getBundle();
                printBundle(bundleb);
                break;
            case R.id.route_btn:
                Router.create("mainmodule://main/BundleActivity").open(this);
                break;
        }
    }

    @Permission(Manifest.permission.CAMERA)
    private void gotoOtherModule() {
        Router.create("loginmodule://main/LoginActivity?username=ztw2010&address=徐汇区").open(this);
    }

    private void showFragment() {
        if (otherModuleFragment != null) {
            ft = getSupportFragmentManager().beginTransaction();
            ft.remove(otherModuleFragment).commit();
            otherModuleFragment = null;
        }
        ComponentService componentService = ComponentService.getInstance();
        if (componentService.getService(IFragmentService.class.getSimpleName()) != null) {
            IFragmentService service = (IFragmentService) componentService.getService(IFragmentService.class.getSimpleName());
            otherModuleFragment = service.getFragment();
            ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container, otherModuleFragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                Toast.makeText(this, "requestCode=100", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void printBundle(Bundle bundle) {
        StringBuilder builder = new StringBuilder("\r\n");
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            builder.append("key=").append(key).append("  &  ").append("value=").append(bundle.get(key)).append("\r\n");
        }
        Log.d(TAG, "Bundle's info --->  " + builder.toString());
    }
}
