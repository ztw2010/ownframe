package com.ebest.frame.baselib.okhttp.convert;

import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.ebest.frame.baselib.okhttp.utils.IOUtils;
import com.ebest.frame.baselib.xml.XmlBean;
import com.thoughtworks.xstream.XStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by ztw on 2017/10/10.
 * 将服务端返回的xml字符串转为bean
 */

public class XMLBeanConvert implements Converter<XmlBean> {

    private final Charset UTF8 = Charset.forName("UTF-8");

    private final String TAG = "XMLBeanConvert";

    /**
     * XmlPullParser解析方式
     * @param xmlStr
     * @return
     */
    private XmlBean parseByPull(String xmlStr) {
        xmlStr = xmlStr.replaceAll("&", "&amp;");
        XmlPullParser parser = Xml.newPullParser();
        XmlBean xmlBean = null;
        try {
            InputStream inputStream = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
            parser.setInput(inputStream, "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        xmlBean = new XmlBean();
                        break;
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();
                        if (name.equals("t")) {
                            String tableName = parser.getAttributeValue(0);
                            xmlBean.setTableName(tableName);
                            String tableFields = parser.getAttributeValue(1);
                            xmlBean.setTableFields(tableFields);
                            String tableTime = parser.getAttributeValue(2);
                            xmlBean.setTableTime(tableTime);
                        } else if (name.equals("r")) {
                            if (xmlBean.getValues() == null) {
                                xmlBean.setValues(new ArrayList<String>());
                            }
                            String text = parser.nextText();
                            Log.d("LoginPresenter", "--内容--" + text);
                            xmlBean.getValues().add(text);
                        }
                        break;
                    case XmlPullParser.END_TAG:

                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            Log.e("LoginPresenter", "XmlPullParserException", e);
        } catch (IOException e) {
            Log.e("LoginPresenter", "IOException", e);
        }
        List<String> fields = Arrays.asList(xmlBean.getTableFields().split(","));
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s%s%s%s%s%s", "<t n=\"", xmlBean.getTableName(), "\" ", "ts=\"", xmlBean.getTableTime(), "\">"));
        for (String str : xmlBean.getValues()) {
            builder.append("<r>");
            List<String> values = Arrays.asList(str.split("\\|", -1));
            if (fields.size() != values.size()) {
                throw new RuntimeException("table fields not equals field values");
            }
            int size = values.size();
            for (int i = 0; i < size; i++) {
                String key = fields.get(i);
                builder.append("<");
                builder.append(key);
                builder.append(">");
                builder.append(values.get(i));
                builder.append("</");
                builder.append(key);
                builder.append(">");
            }
            builder.append("</r>");
        }
        builder.append("</t>");
        xmlBean.setStandardXML(builder.toString());
        return xmlBean;
    }

    @Override
    public XmlBean convertResponse(Response response) throws Throwable {
        ResponseBody responseBody = response.body();
        InputStream bodyStream = null;
        ByteArrayOutputStream baos = null;
        try {
            bodyStream = responseBody.byteStream();
            byte[] buffer = new byte[8192];
            baos = new ByteArrayOutputStream();
            int len = 0;
            while (-1 != (len = bodyStream.read(buffer))) {
                baos.write(buffer, 0, len);
                baos.flush();
            }
        } finally {
            IOUtils.closeQuietly(bodyStream);
            IOUtils.closeQuietly(baos);
        }
        String body = baos.toString("utf-8").replaceAll("&", "&amp;");
        if (!TextUtils.isEmpty(body)) {
            XStream xStream = new XStream();
            xStream.processAnnotations(XmlBean.class);
            XmlBean xmlBean = (XmlBean) xStream.fromXML(body);
            List<String> fields = Arrays.asList(xmlBean.getTableFields().split(","));
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("%s%s%s%s%s%s", "<t n=\"", xmlBean.getTableName(), "\" ", "ts=\"", xmlBean.getTableTime(), "\">"));
            for (String str : xmlBean.getValues()) {
                builder.append("<r>");
                List<String> values = Arrays.asList(str.split("\\|", -1));
                if (fields.size() != values.size()) {
                    throw new RuntimeException("table fields not equals field values");
                }
                int size = values.size();
                for (int i = 0; i < size; i++) {
                    String key = fields.get(i);
                    builder.append("<");
                    builder.append(key);
                    builder.append(">");
                    builder.append(values.get(i));
                    builder.append("</");
                    builder.append(key);
                    builder.append(">");
                }
                builder.append("</r>");
            }
            builder.append("</t>");
            xmlBean.setStandardXML(builder.toString());
            return xmlBean;
        } else {
            XmlBean xmlBean = new XmlBean();
            String tableName = getTableName(response.request());
            xmlBean.setTableName(tableName);
            return xmlBean;
        }
    }

    private String getTableName(Request request) {
        Request copy = request.newBuilder().build();
        RequestBody body = copy.body();
        if (body == null)
            return "";
        Buffer buffer = new Buffer();
        try {
            body.writeTo(buffer);
            Charset charset = getCharset(body.contentType());
            String str = buffer.readString(charset);
            if (str.contains(",")) {
                return str.split(",")[0];
            }
            return str;
        } catch (IOException e) {
            Log.e(TAG, "getTableName has error", e);
        }
        return "";
    }

    private Charset getCharset(MediaType contentType) {
        Charset charset = contentType != null ? contentType.charset(UTF8) : UTF8;
        if (charset == null) charset = UTF8;
        return charset;
    }
}
