package com.base.core.util;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * An utility class to work with Android's {@link Toast} messages
 */
public class ToastUtils {

    /**
     * Displays a text message from a resource ID inside a {@link Toast}, briefly
     *
     * @param resId A resource ID from a {@link String} with the message to be displayed
     */
    public static void show(@StringRes int resId) {
        Toast.makeText(ContextUtils.getAppContext(), resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays a given {@link String} inside a {@link Toast}, briefly
     *
     * @param message An {@link String} with the message to be displayed
     */
    public static void show(@NonNull String message) {
        Toast.makeText(ContextUtils.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays a text message from a resource ID inside a toast, during a longer than usual
     * interval
     *
     * @param resId A resource ID from a {@link String} with the message to be displayed
     */
    public static void showLong(@StringRes int resId) {
        Toast.makeText(ContextUtils.getAppContext(), resId, Toast.LENGTH_LONG).show();
    }

    /**
     * Displays a given {@link String} inside a {@link Toast}, during a longer than usual
     * interval
     *
     * @param message An {@link String} with the message to be displayed
     */
    public static void showLong(@NonNull String message) {
        Toast.makeText(ContextUtils.getAppContext(), message, Toast.LENGTH_LONG).show();
    }
}
