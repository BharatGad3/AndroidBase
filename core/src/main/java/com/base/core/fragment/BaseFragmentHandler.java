package com.base.core.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.core.R;
import com.base.core.presenter.BasePresenter;
import com.base.core.util.ToastUtils;


/**
 * This class is used to separate BaseFragment logic so that different subclasses of
 * Fragment can implement MVP without re-writing this.
 */
class BaseFragmentHandler<T extends BasePresenter> {

    private static final String TAG = "BaseFragmentHandler";

    private final Fragment mFragment;
    private final IBaseFragment<T> mBaseFragment;
    private boolean mCreated;
    private T mPresenter;
    private boolean mMenuVisible;
    private boolean mVisible;
//    private Unbinder mUnbinder;

    BaseFragmentHandler(@NonNull IBaseFragment<T> baseFragment) {
        if (!(baseFragment instanceof Fragment)) {
            throw new IllegalArgumentException("BaseFragment should be a Fragment instance");
        }
        mFragment = (Fragment) baseFragment;
        mBaseFragment = baseFragment;
    }

    /**
     * Method called from {@link BaseFragment#onCreate(Bundle)}, it calls to {@link
     * BaseFragment#handleArguments(Bundle)}
     * to check if the fragment has the correct arguments and creates a presenter calling {@link
     * BaseFragment#createPresenter()}.
     *
     * @param savedInstanceState Saved instance state
     */
    void onCreate(@Nullable Bundle savedInstanceState) {
        if (mBaseFragment.handleArguments(mFragment.getArguments())) {
            mPresenter = mBaseFragment.createPresenter();
        } else {
            Log.e(TAG, mFragment.getClass().getSimpleName() +
                    " - The fragment's handleArguments returned false.");
            ToastUtils.show(R.string.unknown_error);
            mFragment.getActivity().finish();
        }
    }

    /**
     * Method called from {@link BaseFragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}, it
     * creates the view defined in {@link BaseFragment#layout()}
     * Then it calls the following methods and returns the {@link View} created:
     * <p><ul>
     * <li>{@link BaseFragment#setUi(View)}
     * <li>{@link BaseFragment#init()}
     * <li>{@link BaseFragment#populate()}
     * <li>{@link BaseFragment#setListeners()}
     * </ul><p>
     */
    View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                      @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(mBaseFragment.layout(), container, false);
        mBaseFragment.setUi(v);
        mBaseFragment.init();
        mBaseFragment.populate();
        mBaseFragment.setListeners();

        mCreated = true;
        return v;
    }

    /**
     * Method called from {@link BaseFragment#onViewCreated(View, Bundle)}, it notifies the {@link
     * BasePresenter} that the view was created calling {@link BasePresenter#onViewCreated()}.
     */
    void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (getPresenter() != null) {
            getPresenter().onViewCreated();
        }
    }

    /**
     * Returns the presenter {@link T} for this fragment
     *
     * @return presenter
     */
    @Nullable
    T getPresenter() {
        return mPresenter;
    }

    private void onVisibilityChanged() {
        if (!mCreated) return;
        if (mFragment.isResumed() && mMenuVisible && !mVisible) {
            mBaseFragment.onVisible();
            mVisible = true;
        } else if ((!mMenuVisible || !mFragment.isResumed()) && mVisible) {
            mBaseFragment.onHide();
            mVisible = false;
        }
    }

    /**
     * Called from {@link BaseFragment#onResume()}, checks visibility of the fragment
     * and calls {@link BaseFragment#onVisible()} or {@link BaseFragment#onHide()} accordingly.
     */
    void onResume() {
        onVisibilityChanged();
    }

    /**
     * Called from {@link BaseFragment#onPause()}, checks visibility of the fragment
     * and calls {@link BaseFragment#onVisible()} or {@link BaseFragment#onHide()} accordingly.
     */
    void onPause() {
        onVisibilityChanged();
    }

    /**
     * Called from {@link BaseFragment#setMenuVisibility(boolean)}, checks visibility of the
     * fragment's menu and calls {@link BaseFragment#onVisible()} or
     * {@link BaseFragment#onHide()} accordingly.
     * For mor info {@see Fragment#setMenuVisibility(boolean)}.
     */
    void setMenuVisibility(boolean visible) {
        mMenuVisible = visible;
        onVisibilityChanged();
    }

    /**
     * Called from {@link BaseFragment#onDestroyView()}, it notifies the {@link BasePresenter} that
     * the view is destroyed, calling {@link BasePresenter#onViewDestroyed()}
     */
    void onDestroyView() {
        if (getPresenter() != null) {
            getPresenter().onViewDestroyed();
        }
//        mUnbinder.unbind();
    }

    /**
     * Called from {@link BaseFragment#onDestroy()}. It calls {@link
     * BasePresenter#detachView()}
     */
    void onDestroy() {
        if (getPresenter() != null) getPresenter().detachView();
    }
}
