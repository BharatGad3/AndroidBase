package com.base.networking.retrofit.callback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An adapter thar converts Retrofit's {@link Callback} methods to other, more specific, ones
 * depending on network responses or failures.
 *
 * @param <T> the type of object expected to be returned from the API call
 */
public abstract class NetworkCallback<T> implements Callback<T> {

    /**
     * NetworkCallback's implementation of Retrofit's onResponse() callback
     * Try using NetworkCallback's onResponseSuccessful() and onResponseFailed() in your project.
     */
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (isAuthError(response)) {
            handleAuthError(response);
        } else if (response.isSuccessful()) {
            onResponseSuccessful(response.body());
        } else {
            onResponseFailed(response.errorBody(), response.code());
        }
    }

    /**
     * NetworkCallback's implementation of Retrofit's onFailure() callback
     * Try using NetworkCallback's onCallFailure() in your project.
     */
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onCallFailure(t);
    }

    /**
     * Checks whether the response is an auth error or not.
     * <p>
     * You should override this method and check if the response is an auth error, then return <b>true</b> if it is.
     * By default, this method returns <b>false</b>.
     *
     * @param response Retrofit response
     * @return <b>true</b> if the response is an auth error, <b>false</b> otherwise
     */
    protected boolean isAuthError(Response<T> response) {
        return false;
    }

    /**
     * Handles the auth error response.
     * This method is only called when there is an auth error. (<i>isAuthError() returns true</i>)
     * You should remove tokens and do the corresponding cleaning inside this method.
     * By default, this method does nothing.
     *
     * @param response Retrofit response
     */
    protected void handleAuthError(Response<T> response) {
    }

    /**
     * Successful HTTP response from the server.
     * The server received the request, answered it and the response is not of an error type.
     *
     * @param response the API JSON response converted to a Java object.
     *                 The API response code is included in the response object.
     */
    public abstract void onResponseSuccessful(T response);

    /**
     * Successful HTTP response from the server, but has an error body.
     * The server received the request, answered it and reported an error.
     *
     * @param responseBody The error body
     * @param code         The error code
     */
    public abstract void onResponseFailed(ResponseBody responseBody, int code);

    /**
     * The HTTP request to the server failed on the local device, no data was transmitted.
     * Invoked when a network or unexpected exception occurred during the HTTP request, meaning
     * that the request couldn't be executed.
     *
     * @param t A Throwable with the cause of the call failure
     */
    public abstract void onCallFailure(Throwable t);

}