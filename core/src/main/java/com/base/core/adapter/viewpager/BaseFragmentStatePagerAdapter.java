package com.base.core.adapter.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

/**
 * Basic {@link FragmentStatePagerAdapter} to manage fragments with title.
 */
public class BaseFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mPages;
    private ArrayList<String> mTitles;

    /**
     * Constructor. Requires an isntance of {@link FragmentManager}.
     *
     * @param fm An isntance of {@link FragmentManager}
     */
    public BaseFragmentStatePagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        mPages = new ArrayList<>();
        mTitles = new ArrayList<>();
    }

    /**
     * Returns the {@link Fragment} in the provided position. Can be null if the {@link Fragment}
     * isn't in the {@link ViewPager}
     *
     * @param position The position of the requested {@link Fragment}
     * @return The {@link Fragment} in the given position. Can be null.
     */
    @Override
    @NonNull
    public Fragment getItem(int position) {
        return mPages.get(position);
    }

    /**
     * Returns the amount of {@link Fragment}s in the {@link ViewPager}
     *
     * @return amount of {@link Fragment}s
     */
    @Override
    public int getCount() {
        return mPages.size();
    }

    /**
     * Returns the title of the {@link Fragment} in a certain position
     *
     * @param position The position of the {@link Fragment} whose title is requested
     * @return The title of the {@link Fragment} in the given position. Can be null.
     */
    @Override
    @Nullable
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    /**
     * Adds a fragment to the adapter with no title. The title will be set to <b>null</b>.
     *
     * @param fragment Fragment to add
     */
    public void addItem(@NonNull Fragment fragment) {
        addItem(fragment, null);
    }

    /**
     * Adds a fragment to the adapter with the given title.
     *
     * @param fragment Fragment to add
     * @param title    Title for the fragment
     */
    public void addItem(@NonNull Fragment fragment, @Nullable String title) {
        mPages.add(fragment);
        mTitles.add(title);
        notifyDataSetChanged();
    }
}
