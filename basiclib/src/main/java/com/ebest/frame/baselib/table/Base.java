package com.ebest.frame.baselib.table;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by ztw on 2017/10/9.
 */
public class Base {

    @XStreamAsAttribute()
    @XStreamAlias("ts")
    protected String tableTime;



    public String getTableTime() {
        return tableTime;
    }

    public void setTableTime(String tableTime) {
        this.tableTime = tableTime;
    }

}
