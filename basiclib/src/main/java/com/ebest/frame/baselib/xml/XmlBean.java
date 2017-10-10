package com.ebest.frame.baselib.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by ztw on 2017/9/30.
 */

@XStreamAlias("t")
public class XmlBean {

    @XStreamAsAttribute()
    @XStreamAlias("n")
    private String tableName;

    @XStreamAsAttribute()
    @XStreamAlias("field")
    private String tableFields;

    @XStreamAsAttribute()
    @XStreamAlias("ts")
    private String tableTime;

    @XStreamImplicit(itemFieldName = "r")
    private List<String> values;

    private String standardXML;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableFields() {
        return tableFields;
    }

    public void setTableFields(String tableFields) {
        this.tableFields = tableFields;
    }

    public String getTableTime() {
        return tableTime;
    }

    public void setTableTime(String tableTime) {
        this.tableTime = tableTime;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getStandardXML() {
        return standardXML;
    }

    public void setStandardXML(String standardXML) {
        this.standardXML = standardXML;
    }
}

