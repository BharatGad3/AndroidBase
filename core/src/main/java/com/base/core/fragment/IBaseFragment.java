package com.base.core.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;

import com.base.core.presenter.BasePresenter;


/**
 * This interface defines a set of methods that compose the lifecycle of these fragments
 *
 * @param <T> an MVP Presenter, the class implementing this interface will act as an MVP View
 */
public interface IBaseFragment<T extends BasePresenter> {

    /**
     * This method provides a way for populating the view with a layout defined in an XML resource.
     * <p>
     * Example:
     * protected abstract int layout() {
     * return R.layout.my_layout_for_this_activity;
     * }
     *
     * @return The ID of the layout defined in an XML that will be used for populating the view.
     */
    @LayoutRes
    int layout();

    /**
     * Reads arguments sent as a Bundle extras.
     * <b>NOTE: </b>Returning <i>false</i> will end the execution of the fragment.
     *
     * @param args The bundle obtainable by the {@link Fragment#getArguments()} method.
     * @return <b>true</b> if this fragment contains the required values, <b>false</b> otherwise.
     * Default implementation returns true.
     */
    boolean handleArguments(Bundle args);

    /**
     * Create the presenter for this fragment
     */
    T createPresenter();

    /**
     * Loads the view elements for the fragment
     */
    void setUi(View v);

    /**
     * Initializes any variables that the fragment needs
     */
    void init();

    /**
     * Populates the view elements of the fragment
     */
    void populate();

    /**
     * Sets the listeners for the views of the fragment
     */
    void setListeners();

    /**
     * Override this method is you want to do anything when the fragment becomes visible
     */
    void onVisible();

    /**
     * Override this method is you want to do anything when the fragment hides
     */
    void onHide();

    /**
     * Custom back press handling method.
     *
     * @return 'true' if the back was handled and shouldn't propagate, 'false' otherwise
     */
    boolean onBackPressed();
}
