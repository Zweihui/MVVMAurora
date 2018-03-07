package me.zwh.mvvm.aurora.mvvm.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import me.zwh.mvvm.aurora.mvvm.model.entry.Category;
import me.zwh.mvvm.aurora.mvvm.view.fragment.VideoListFragment;

/**
 * Created by zwh on 2017/8/20.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Category> mCategories;

    public static FragmentAdapter newInstance(FragmentManager fm, List<Category> categories) {
        FragmentAdapter mFragmentAdapter = new FragmentAdapter(fm);
        mFragmentAdapter.mCategories = categories;
        return mFragmentAdapter;
    }

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        return mFragments.get(position);
        return VideoListFragment.newInstance(mCategories.get(position).getName());
    }

    @Override
    public int getCount() {
        return mCategories.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCategories.get(position).getName();
    }
}
