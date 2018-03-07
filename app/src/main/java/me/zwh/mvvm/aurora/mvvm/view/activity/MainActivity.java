package me.zwh.mvvm.aurora.mvvm.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;

import java.util.ArrayList;

import me.zwh.mvvm.aurora.R;
import me.zwh.mvvm.aurora.app.constants.Constants;
import me.zwh.mvvm.aurora.databinding.ActivityMainBinding;
import me.zwh.mvvm.aurora.mvvm.model.entry.Category;
import me.zwh.mvvm.aurora.mvvm.view.adapter.FragmentAdapter;
import me.zwh.mvvm.aurora.mvvm.view.adapter.VideoAdapter;
import me.zwh.mvvm.aurora.mvvm.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private VideoAdapter adapter;
    private ArrayList<Category> list = new ArrayList<>();
    public int position;

    @Override
    public int initView(Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        return R.layout.activity_main;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.CATEGORY_DATA, list);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            list = savedInstanceState.getParcelableArrayList(Constants.CATEGORY_DATA);
        }
        mBinding.setViewModel(mViewModel);
        mViewModel.getResults().observe(this, result -> {
            mBinding.setResource(result);
            if (result.data != null) {
                list.addAll(result.data);
                initViewPager();
            }
        });
        initToolBar();
        mViewModel.getCategories();
        mBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initViewPager() {
        mBinding.viewpager.setAdapter(FragmentAdapter.newInstance(getSupportFragmentManager(), list));
        mBinding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MainActivity.this.position = position;
                ((BaseApplication) getApplication())
                        .getAppComponent()
                        .imageLoader().loadImage(MainActivity.this, ImageConfigImpl
                        .builder()
                        .url(list.get(position).getHeaderImage())
                        .imageView(mBinding.toolbarIvTarget)
                        .build());
                mBinding.collapsingToolbar.setTitle(list.get(MainActivity.this.position).getName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBinding.tlTabs.setupWithViewPager(mBinding.viewpager);
        ((BaseApplication) getApplication())
                .getAppComponent()
                .imageLoader().loadImage(MainActivity.this, ImageConfigImpl
                .builder()
                .url(list.get(0).getHeaderImage())
                .imageView(mBinding.toolbarIvTarget)
                .build());
        mBinding.viewpager.setOffscreenPageLimit(4);
        mBinding.viewpager.setCurrentItem(position);
        mBinding.collapsingToolbar.setTitle(list.get(MainActivity.this.position).getName());
    }


    private void initToolBar() {
        setSupportActionBar(mBinding.toolbar);
        setTitle("MVVMAurora");
    }
}
