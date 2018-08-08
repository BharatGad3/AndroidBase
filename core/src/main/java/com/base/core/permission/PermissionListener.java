package com.base.core.permission;

import androidx.annotation.NonNull;

/**
 * Callback used by {@link PermissionManager} when requesting permissions to the user
 */
public abstract class PermissionListener {

    /**
     * Called when the required permissions are granted by the user
     */
    public void onPermissionsGranted() {
    }

    /**
     * Called when all or some of the requested permissions are rejected by the user
     *
     * @param deniedPermissions Array of {@link String} that contains the denied permissions
     */
    public void onPermissionsDenied(@NonNull String[] deniedPermissions) {
    }
}