package com.base.core.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.base.core.R;
import com.base.core.fragment.IBaseFragment;
import com.base.core.permission.PermissionManager;
import com.base.core.util.ToastUtils;

import java.util.List;

/**
 * A base {@link AppCompatActivity} that implements a custom lifecycle.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * Handles the custom lifecycle of this Activity. It provides a set of callbacks to structure
     * the different aspects of the Activities initialization.
     *
     * @param savedInstanceState a {@link Bundle} provided by Android's lifecycle.
     */
    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflate(getLayoutInflater());
        setContentView(view);
        if (handleArguments(getIntent().getExtras())) {
            setUi();
            init();
            populate();
            setListeners();
        } else {
            ToastUtils.show(R.string.unknown_error);
            finish();
        }
    }

    protected abstract View inflate(LayoutInflater layoutInflater);

    /**
     * Reads arguments sent as a Bundle and saves them as appropriate.
     *
     * @param args The bundle obtainable by the getExtras method of the intent.
     * @return true if arguments were read successfully, false otherwise.
     * Default implementation returns true.
     */
    protected boolean handleArguments(Bundle args) {
        return true;
    }

    /**
     * Associates variables to views inflated from the XML resource
     * provided in {@link BaseActivity#layout()}
     * Override if needed.
     */
    protected void setUi() {

    }

    /**
     * Entry point for the Activity's specific code.
     * Prefer this callback over {@link android.app.Activity#onCreate(Bundle)}.
     * Initializes any variables or behaviour that the activity needs.
     */
    protected abstract void init();

    /**
     * Populates the view elements of the activity.
     * Override if needed.
     */
    protected void populate() {

    }

    /**
     * Sets the listeners for the views of the activity.
     * Override if needed.
     */
    protected void setListeners() {

    }

    /**
     * Replaces the current {@link Fragment} in a given container layout with a new {@link Fragment}
     *
     * @param resId The ID of the layout that holds the current {@link Fragment}. It should be the
     *              same container that will be used for the new {@link Fragment}
     * @param f     An instance of a {@link Fragment} that will replace the older one.
     */
    // TODO We should delegate this methods to a helper
    protected void replaceFragment(int resId, Fragment f) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(resId, f)
                .commit();
    }

    /**
     * Replaces the current {@link Fragment} in a given container layout with a new {@link Fragment}
     * using a custom tag ({@link String}) that allows the fragment to be more easily located.
     *
     * @param resId The ID of the layout that holds the current {@link Fragment}. It should be the
     *              same container that will be used for the new {@link Fragment}
     * @param f     An instance of a {@link Fragment} that will replace the older one.
     * @param tag   A {@link String} that will be used to identify the {@link Fragment}
     */
    // TODO We should delegate this methods to a helper
    protected void replaceFragment(int resId, Fragment f, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(resId, f, tag)
                .commit();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @CallSuper
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.getInstance()
                .onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @CallSuper
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        // If activity has no child fragments, the list is null
        if (fragments != null) {
            for (Fragment childFragment : fragments) {
                if (childFragment instanceof IBaseFragment && childFragment.isVisible()) {
                    if (((IBaseFragment) childFragment).onBackPressed()) return;
                }
            }
        }

        super.onBackPressed();
    }
}