package com.base.core.util;

import android.content.Context;

import androidx.annotation.NonNull;

import com.base.core.BaseApplication;

/**
 * An utility class to work with Android's {@link Context}
 */
public class ContextUtils {

    /**
     * Returns a singleton {@link Context} from the running application
     *
     * @return a singleton instance of {@link Context} that can be used for almost everything
     */
    @NonNull
    public static Context getAppContext() {
        return BaseApplication.getInstance().getApplicationContext();
    }

}
