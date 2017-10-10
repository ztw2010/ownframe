package com.ebest.frame.baselib.okhttp.request;


import com.ebest.frame.baselib.okhttp.model.HttpMethod;
import com.ebest.frame.baselib.okhttp.request.base.NoBodyRequest;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class HeadRequest<T> extends NoBodyRequest<T, HeadRequest<T>> {

    public HeadRequest(String url) {
        super(url);
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.HEAD;
    }

    @Override
    public Request generateRequest(RequestBody requestBody) {
        Request.Builder requestBuilder = generateRequestBuilder(requestBody);
        return requestBuilder.head().url(url).tag(tag).build();
    }
}
