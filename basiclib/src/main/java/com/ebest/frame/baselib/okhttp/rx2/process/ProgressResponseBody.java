package com.ebest.frame.baselib.okhttp.rx2.process;

import com.ebest.frame.baselib.okhttp.utils.IOUtils;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by ztw on 2017/10/16.
 */

public class ProgressResponseBody extends ResponseBody {

    private final ResponseBody responseBody;

    private final ProgressListener progressListener;

    private BufferedSource progressSource;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }


    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }


    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }


    @Override
    public BufferedSource source() {
        if (progressListener == null) {
            return responseBody.source();
        }
        ProgressInputStream progressInputStream = new ProgressInputStream(responseBody.source().inputStream(), progressListener, contentLength());
        progressSource = Okio.buffer(Okio.source(progressInputStream));
        return progressSource;
    }

    @Override
    public void close() {
        super.close();
        IOUtils.closeQuietly(progressSource);
    }
}