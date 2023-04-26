package com.base.core.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.base.core.permission.PermissionManager;
import com.base.core.presenter.BasePresenter;

/**
 * Base implementation for {@link IBaseFragment} for dialog fragments. This is in charge of
 * inflating the view returned by {@link #layout()}.
 * The presenter is created on {@link #onCreate(Bundle)} if {@link #handleArguments(Bundle)} returns
 * true. This class defines default implementations for most of the methods on {@link
 * IBaseFragment}.
 *
 * @param <T> Presenter for this fragment. It should extend {@link BasePresenter}
 */
public abstract class BaseDialogFragment<T extends BasePresenter> extends DialogFragment
        implements IBaseFragment<T> {
    private BaseFragmentHandler<T> mFragmentHandler;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            Drawable backgroundDrawable = new ColorDrawable(Color.argb(1, 255, 0, 0));
            getDialog().getWindow().setBackgroundDrawable(backgroundDrawable);
            setOnBackPressedListener();
        }
    }

    /**
     * Sets a custom {@link android.content.DialogInterface.OnKeyListener} for the
     * {@link Dialog} returned by {@link #getDialog()} that calls {@link #onBackPressed()}
     * if the key is the back key.
     * <p>
     * Beware that, when clicking a key, the {@link android.content.DialogInterface.OnKeyListener}
     * is called before delegating the event to other structures. For example, the back is handled
     * here before sending it to an {@link Activity}.
     */
    private void setOnBackPressedListener() {
        if (getDialog() == null) return;
        getDialog().setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK && onBackPressed();
            }
        });
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentHandler = new BaseFragmentHandler<T>(this);
        mFragmentHandler.onCreate(savedInstanceState);
    }

    @Override
    @CallSuper
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return mFragmentHandler.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    @CallSuper
    public void setMenuVisibility(final boolean visible) {
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

    /**
     * Reads arguments sent as a Bundle extras.
     *
     * @param arguments The bundle obtainable by the getExtras method of the intent.
     * @return true if arguments were read successfully, false otherwise.
     * Default implementation returns true.
     */
    public boolean handleArguments(Bundle arguments) {
        return true;
    }

    /**
     * Associates variables to views inflated from the XML resource
     * provided in {@link IBaseFragment#layout()}
     * Override if needed.
     */
    public void setUi(View v) {
    }

    /**
     * Sets the listeners for the views of the fragment.
     * Override if needed.
     */
    public void setListeners() {
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
     * Populates the view elements of the fragment.
     * Override if needed.
     */
    @Override
    public void populate() {
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
     * Shows the {@link BaseDialogFragment} using the fragment.
     *
     * @param manager Fragment Manager to show the dialog fragment
     */
    public void show(FragmentManager manager) {
        super.show(manager, getClass().getName());
    }

    /**
     * @see IBaseFragment#onBackPressed()
     * <p>
     * Beware, when overriding, that returning 'true' will prevent default navigation behaviour such
     * as {@link Dialog#dismiss()}.
     */
    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.getInstance()
                .onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
