package com.dayang.dycmmedit.http;

import android.content.Context;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 冯傲 on 2017/5/2.
 * e-mail 897840134@qq.com
 */

public class BaseInterceptor implements Interceptor {

    private Map<String, String> headers;
    private Context context;

    public BaseInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    public BaseInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request()
                .newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }
        return chain.proceed(builder.build());
    }
}
