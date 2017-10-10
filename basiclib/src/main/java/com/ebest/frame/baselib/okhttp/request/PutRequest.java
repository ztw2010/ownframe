package com.ebest.frame.baselib.okhttp.request;


import com.ebest.frame.baselib.okhttp.model.HttpMethod;
import com.ebest.frame.baselib.okhttp.request.base.BodyRequest;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * ================================================
 * 描    述：
 * ================================================
 */
public class PutRequest<T> extends BodyRequest<T, PutRequest<T>> {

    public PutRequest(String url) {
        super(url);
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.PUT;
    }

    @Override
    public Request generateRequest(RequestBody requestBody) {
        Request.Builder requestBuilder = generateRequestBuilder(requestBody);
        return requestBuilder.put(requestBody).url(url).tag(tag).build();
    }
}
