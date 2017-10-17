package com.ebest.frame.baselib.okhttp.rx2.process;

import com.ebest.frame.baselib.okhttp.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ztw on 2017/10/17.
 * 带进度的输入流
 */
public class ProgressInputStream extends InputStream {

    private final InputStream stream;

    private final ProgressListener listener;

    private long total;

    ProgressInputStream(InputStream stream, ProgressListener listener, long total) {
        this.stream = stream;
        this.listener = listener;
        this.total = total;
    }


    @Override
    public int read() throws IOException {
        int read = this.stream.read();
        if (this.total < 0) {
            this.listener.onProgressChanged(-1, -1);
            return read;
        }
        if (read >= 0) {
            this.listener.onProgressChanged(read, this.total);
        }
        return read;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int read = this.stream.read(b, off, len);
        if (this.total < 0) {
            this.listener.onProgressChanged(-1, -1);
            return read;
        }
        if (read >= 0) {
            this.listener.onProgressChanged(read, this.total);
        }
        return read;
    }

    @Override
    public void close() throws IOException {
        IOUtils.closeQuietly(stream);
    }
}
