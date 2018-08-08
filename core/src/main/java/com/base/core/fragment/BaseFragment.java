package com.base.core.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.base.core.permission.PermissionManager;
import com.base.core.presenter.BasePresenter;

/**
 * Base implementation for {@link IBaseFragment}. This is in charge of inflating the view returned
 * by {@link #layout()}. The presenter is created on
 * {@link #onCreate(Bundle)} if {@link #handleArguments(Bundle)} returns true.
 * This class defines default implementations for most of the methods on {@link IBaseFragment}.
 *
 * @param <T> Presenter for this fragment. It should extend {@link BasePresenter}
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment
        implements IBaseFragment<T> {

    private BaseFragmentHandler<T> mFragmentHandler = new BaseFragmentHandler<>(this);

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentHandler.onCreate(savedInstanceState);
    }

    @Override
    @CallSuper
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return mFragmentHandler.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentHandler.onViewCreated(view, savedInstanceState);
    }

    @Override
    @CallSuper
    public void setMenuVisibility(boolean visible) {
        super.setMenuVisibility(visible);
        mFragmentHandler.setMenuVisibility(visible);
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        mFragmentHandler.onResume();
    }

    @Override
    @CallSuper
    public void onPause() {
        super.onPause();
        mFragmentHandler.onPause();
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        mFragmentHandler.onDestroyView();
        super.onDestroyView();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        mFragmentHandler.onDestroy();
        super.onDestroy();
    }

    /**
     * Delegates permission handling to {@link PermissionManager}.
     */
    @Override
    @CallSuper
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.getInstance()
                .onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Reads arguments sent as a Bundle extras.
     *
     * @param arguments The bundle obtainable by the getExtras method of the intent.
     * @return true if arguments were read successfully, false otherwise.
     * Default implementation returns true.
     */
    @Override
    public boolean handleArguments(@Nullable Bundle arguments) {
        return true;
    }

    /**
     * Associates variables to views inflated from the XML resource
     * provided in {@link IBaseFragment#layout()}
     * Override if needed.
     */
    @Override
    public void setUi(View v) {
    }

    /**
     * Sets the listeners for the views of the fragment.
     * Override if needed.
     */
    @Override
    public void setListeners() {
    }

    /**
     * Populates the view elements of the fragment.
     * Override if needed.
     */
    @Override
    public void populate() {
    }

    /**
     * Callback called when the fragment becomes visible to the user.
     * Override if needed.
     */
    @Override
    public void onVisible() {
    }

    /**
     * Callback called when the fragment becomes hidden to the user.
     * Override if needed.
     */
    @Override
    public void onHide() {
    }

    /**
     * Returns the instance of the presenter for this fragment.
     *
     * @return Presenter for this fragment
     */
    protected T getPresenter() {
        return mFragmentHandler.getPresenter();
    }

    /**
     * @see IBaseFragment#onBackPressed()
     * <p>
     * Beware, when overriding, that returning 'true' will prevent default navigation behaviour such
     * as {@link FragmentManager#popBackStackImmediate()} or {@link Activity#finish()}, but not
     * dismissing the keyboard, for example.
     */
    @Override
    public boolean onBackPressed() {
        return false;
    }
}