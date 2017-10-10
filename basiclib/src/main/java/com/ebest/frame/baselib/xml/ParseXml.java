package com.ebest.frame.baselib.xml;

import com.thoughtworks.xstream.XStream;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ztw on 2017/9/30.
 */

public class ParseXml {

    private static final String TAG = "ParseXml";

    public static XmlBean toXmlBean(String content) {
        XStream xStream = new XStream();
        xStream.processAnnotations(XmlBean.class);
        XmlBean xmlBean = (XmlBean) xStream.fromXML(content);
        List<String> fields = Arrays.asList(xmlBean.getTableFields().split(","));
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s%s%s%s%s%s", "<t n=\"", xmlBean.getTableName(), "\" ", "ts=\"", xmlBean.getTableTime(), "\">"));
        for (String str : xmlBean.getValues()) {
            builder.append("<r>");
            List<String> values = Arrays.asList(str.split("\\|"));
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
}
