package com.base.core.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * An utility class to work with Android's keyboard
 */
public class KeyboardUtils {

    /**
     * Forces the device's soft keyboard (that means the software keyboard, not a physical one)
     * to show for a specific {@link EditText}
     *
     * @param context  an instance of {@link Context}
     * @param editText an instance of an attached {@link EditText}
     */
    public static void showKeyboard(@NonNull Context context, @NonNull EditText editText) {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Forces the device's soft keyboard (that means the software keyboard, not a physical one)
     * to hide, meant to be called from inside a {@link Fragment}
     *
     * @param context an instance of {@link Context}
     * @param view    an instance of {@link Fragment
     */
    public static void hideKeyboard(@NonNull Context context, @NonNull View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Forces the device's soft keyboard (that means the software keyboard, not a physical one)
     * to hide, meant to be called from inside an {@link Activity}
     *
     * @param activity an instance of the currently displayed {@link Activity}
     */
    public static void hideKeyboard(@NonNull Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.
                getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        // If no view currently has focus, create a new one,
        // just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}