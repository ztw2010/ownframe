package com.ebest.frame.baselib.okhttp.rx2.process;

/**
 * Created by ztw on 2017/10/16.
 * 流读写进度
 */

public interface ProgressListener {
    /**
     * 进度发生了改变，如果numBytes，totalBytes都为-1，则表示总大小获取不到
     *
     * @param readByte   当次读取的长度（不是总读取长度）
     * @param totalBytes 总大小
     */
    void onProgressChanged(long readByte, long totalBytes);
}
