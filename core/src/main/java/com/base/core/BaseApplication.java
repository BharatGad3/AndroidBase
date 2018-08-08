package com.base.core;

import android.app.Application;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

/**
 * An extension of Android's native {@link Application} class that is intended to be used as
 * a Singleton
 */
public abstract class BaseApplication extends Application {

    private static BaseApplication sApplication;

    @Override
    @CallSuper
    public void onCreate() {
        super.onCreate();
        sApplication = this; // Singleton instance
        onInit();
    }

    /**
     * Provides an entry point that gets executed after the {@link Application} has been created.
     * Useful to initialize libraries and other dependencies.
     */
    public abstract void onInit();


    /**
     * Gets the singleton instance of the class
     *
     * @return A singleton instance of {@link BaseApplication}
     */
    @NonNull
    public static BaseApplication getInstance() {
        return sApplication;
    }
}
