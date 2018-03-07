package me.zwh.mvvm.aurora.mvvm.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvvm.RetryCallback;

import me.zwh.mvvm.aurora.R;
import me.zwh.mvvm.aurora.app.constants.Constants;
import me.zwh.mvvm.aurora.databinding.FragmentVideoListBinding;
import me.zwh.mvvm.aurora.mvvm.view.adapter.VideoAdapter;
import me.zwh.mvvm.aurora.mvvm.viewmodel.VideoViewModel;

/**
 * @author zwh
 * @date 2018\3\6 0006
 */

public class VideoListFragment extends BaseFragment<FragmentVideoListBinding,VideoViewModel> {

    public final static String VIDEOTYPE = "type";

    private VideoAdapter adapter ;
    private String type ="";

    public static VideoListFragment newInstance(String type) {
        VideoListFragment videoListFragment = new VideoListFragment();
        Bundle args = new Bundle();
        args.putString(VIDEOTYPE, type);
        videoListFragment.setArguments(args);
        return videoListFragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("type", type);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (String) getArguments().get(Constants.TYPE);
        if (savedInstanceState != null){
            type =  savedInstanceState.getString("type");
        }
    }



    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(VideoViewModel.class);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_list, container, false);
        mBinding.setCallback(new RetryCallback() {
            @Override
            public void retry() {
                mViewModel.setIndexVideoList(type);
            }
        });
        mViewModel.getResults().observe(this, result -> {
            mBinding.setResource(result);
            if (result.data != null){
                if (result.data.size()<10){
                    adapter.setEnableLoadMore(false);
                }
                if (adapter == null){
//                    mBinding.swipeTarget.setAdapter(new VideoAdapter(R.layout.item_video_list,result.data));
                    adapter = new VideoAdapter(R.layout.item_video_list,result.data);
                    mBinding.swipeTarget.setAdapter(adapter);
                    adapter.setOnLoadMoreListener(() -> mBinding.swipeTarget.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mViewModel.setLoadMoreVideoList(adapter.getData().size());
                        }
                    },300), mBinding.swipeTarget);
                }else {
                    adapter.notifyDataSetChanged();
                }
            }
        });

        mViewModel.getLoadMoreResults().observe(this, result -> {
            if (result.data != null){
                adapter.addData(result.data);
                adapter.loadMoreComplete();
                if (result.data.size()<10){
                    adapter.setEnableLoadMore(false);
                }
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setData(Object data) {

    }

    @Override
    protected void loadData() {
        mViewModel.setIndexVideoList(type);
    }
}
