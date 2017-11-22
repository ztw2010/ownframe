package com.ebest.frame;

import android.os.Bundle;
import android.widget.TextView;

import com.ebest.frame.annomationlib.parama.annomation.Arg;
import com.ebest.frame.annomationlib.parama.annomation.Converter;
import com.ebest.frame.annomationlib.parama.annomation.FastJsonConverter;
import com.ebest.frame.annomationlib.router.annomation.RouterRule;
import com.ebest.frame.bean.Book;
import com.ebest.frame.bean.Info;
import com.ebest.frame.commnetservicve.base.BaseActivity;


/**
 * Created by haoge on 2017/6/8.
 */

@RouterRule({"main/BundleActivity","http://ebest.cn"})
public class BundleActivity extends BaseActivity {

    public TextView textView;

    @Arg
    StringBuilder builder;
    @Arg
    StringBuffer buffer;
    @Arg
    Info[] books;
    @Arg
    Byte[] bytes;

    @Converter(FastJsonConverter.class)
    @Arg
    Book book;

    @Arg
    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle);
        textView = (TextView) findViewById(R.id.textView);
        if(builder != null){
            textView.setText("builder=" + builder.toString() + "\nbuffer=" + buffer.toString() + "\nbooks=" + books + "\nbook=" + book + "\nage=" + age);
        }
    }
}
