package com.base.networking.utils;

/**
 * Common HTTP responses codes, useful when making API calls using Retrofit's
 * {@link retrofit2.Callback} or
 * {@link com.base.networking.retrofit.callback.NetworkCallback}
 */
public class NetworkCodes {

    /**
     * 2XX Success
     */
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    public static final int OK_NO_CONTENT = 204;

    /**
     * 4XX Client errors
     */
    public static final int ERROR_BAD_REQUEST = 400;
    public static final int ERROR_UNAUTHORIZED = 401;
    public static final int ERROR_FORBIDDEN = 403;
    public static final int ERROR_NOT_FOUND = 404;
    public static final int ERROR_PRECONDITION_FAILED = 412;

    /**
     * 5XX Server errors
     */
    public static final int ERROR_INTERNAL = 500;

}
