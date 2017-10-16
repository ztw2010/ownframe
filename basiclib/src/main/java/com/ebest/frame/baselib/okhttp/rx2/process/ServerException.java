package com.ebest.frame.baselib.okhttp.rx2.process;

/**
 * Created by ztw on 2017/10/11.
 */

public class ServerException extends RuntimeException {
    public int code;
    public String message;

    public ServerException(String message, int code) {
        this.message=message;
        this.code = code;
    }
}
