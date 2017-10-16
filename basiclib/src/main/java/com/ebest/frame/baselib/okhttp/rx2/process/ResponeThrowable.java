package com.ebest.frame.baselib.okhttp.rx2.process;

/**
 * Created by ztw on 2017/10/11.
 */

public class ResponeThrowable extends Exception {
    public int code;
    public String message;

    public ResponeThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
