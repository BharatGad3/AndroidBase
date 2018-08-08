package com.base.networking.retrofit.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An implementation of OkHTTP's {@link Interceptor} that adds common headers to every API
 * request and provides helper methods to add custom ones.
 */
public abstract class ApiRestInterceptor implements Interceptor {

    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String ACCEPT_HEADER = "Accept";

    /**
     * Intercepts the API call and adds custom headers to the request. By default, it will
     * add both "Content-Type" and "Accept" headers.
     * If you wish to add more custom headers you may prefer using the method addHeaders() instead
     * of overwriting this one.
     *
     * @param chain an object provided by OkHTTP with data of the request being made
     * @return an instance of {@link Response} by OkHTTP
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder()
                .addHeader(CONTENT_TYPE_HEADER, "application/json")
                .addHeader(ACCEPT_HEADER, "application/json");
        addHeaders(requestBuilder);
        request = requestBuilder.build();
        return chain.proceed(request);
    }

    /**
     * A helper method to add custom headers to the network request.
     *
     * @param requestBuilder an instance of {@link Request.Builder} that you can use to add custom
     *                       headers.
     */
    public abstract void addHeaders(@NonNull Request.Builder requestBuilder);
}
