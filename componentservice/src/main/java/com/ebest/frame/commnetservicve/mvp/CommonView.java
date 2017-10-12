package com.ebest.frame.commnetservicve.mvp;

/**
 * Created by ztw on 2017/10/10.
 */
public interface CommonView {
    /**
     *  提示成功信息
     * @param msg
     */
    void showSuccessWithStatus(String msg);

    /**
     *  提示错误信息
     * @param msg
     */
    void showErrorWithStatus(String msg);

    /**
     *  提示消息
     * @param msg
     */
    void showsInfoWithStatus(String msg);
    /**
     *  进度框
     * @param msg
     */
    void showWithProgress(String msg);

    /**
     *
     */
    void dismiss();
}
