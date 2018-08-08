package com.base.networking.retrofit;

import androidx.annotation.CallSuper;

import com.base.core.BaseApplication;


/**
 * This class extends {@link BaseApplication} to easily initialize and get and instance of
 * {@link RetrofitServices} to perform API calls.
 */
public abstract class NetworkingApplication extends BaseApplication {

    private static RetrofitServices sRetrofitServices;

    /**
     * Overrides the {@link android.app.Application} onCreate() method to initialize retrofit
     * services provided by the subclass.
     */
    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();
        sRetrofitServices = getRetrofitServices();
        sRetrofitServices.init();
    }

    /**
     * Must provide an instance of {@link RetrofitServices} that will be initialized and
     * used to perform API calls by the application. It's not necessary to call init() on the
     * {@link RetrofitServices} instance, it will be called by this class.
     *
     * @return an instance of {@link RetrofitServices} available to perform API requests
     */
    public abstract RetrofitServices getRetrofitServices();

}