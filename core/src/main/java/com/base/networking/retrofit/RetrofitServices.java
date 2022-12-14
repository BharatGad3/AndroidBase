package com.base.networking.retrofit;

import androidx.annotation.NonNull;

import com.base.networking.retrofit.serializer.BaseGsonBuilder;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class handles {@link Retrofit} main class initialization and services instances to perform
 * API calls to several endpoints.
 */
public abstract class RetrofitServices {

    private Retrofit mRetrofit;
    private Map<Class, Object> mServices;

    /**
     * This method must be called to start using this class. It initializes required variables
     * and Retrofit.
     * Please note that calling this method on an already initialized class will reset it to a
     * clean state, configuring Retrofit to work with the endpoint provided in getApiEndpoint()
     */
    public void init() {
        mServices = new HashMap<>();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(getApiEndpoint())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(getOkHttpClient())
                .build();
    }

    /**
     * Returns the API endpoint.
     *
     * @return URL endpoint
     */
    @NonNull
    public abstract String getApiEndpoint();

    /**
     * Returns an instance of Gson to use for conversion.
     * This method calls <i>initGson(builder)</i> to configure the Gson Builder.
     *
     * @return A configured Gson instance
     */
    @NonNull
    protected Gson getGson() {
        com.google.gson.GsonBuilder builder = BaseGsonBuilder.getBaseGsonBuilder();
        initGson(builder);
        return builder.create();
    }

    /**
     * Override if needed to configure a gson builder.
     * You should add serializers and/or deserializers inside this method.
     *
     * @param builder Builder to configure
     */
    protected void initGson(@NonNull com.google.gson.GsonBuilder builder) {
    }

    /**
     * Returns an OkHttpClient.
     * This method calls <i>initClient(builder)</i> to configure the builder for OkHttpClient.
     *
     * @return A configured instance of OkHttpClient.
     */
    @NonNull
    protected OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        initClient(builder);
        return builder.build();
    }

    /**
     * Configures an <i>OkHttpClient.Builder</i>.
     * You must add interceptors and configure the builder inside this method.
     */
    protected void initClient(@NonNull OkHttpClient.Builder builder) {
        HttpLoggingInterceptor loggerInterceptor = new HttpLoggingInterceptor();
        loggerInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(loggerInterceptor);
    }

    /**
     * Checks if the {@link Retrofit} client has been initialized at least once.
     *
     * @return Returns <code>True</code> if the Retrofit client has been initialized and is ready to
     * be used, <code>False</code> otherwise.
     */
    private boolean isInitialized() {
        return mRetrofit != null;
    }

    /**
     * Builds and returns a Retrofit Service.
     * If the service wasn't accessed, it'll be created and cached internally.
     * On successive requests, the already created instance will be returned.
     * <p>
     * Usage:
     * Override this class and define the services like this:
     * public static UserService user() {
     * return getService(UserService.class);
     * }
     *
     * @param clazz RetrofitService Class
     * @param <T>   Service class
     * @return service
     */
    public <T> T getService(@NonNull Class<T> clazz) {
        if (!isInitialized()) throw new RuntimeException("RetrofitServices is not initialized! " +
                "Must call init() at least once before calling getService(clazz)");

        T service = (T) mServices.get(clazz);
        if (service != null) return service;
        service = mRetrofit.create(clazz);
        mServices.put(clazz, service);
        return service;
    }
}