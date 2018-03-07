/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jess.arms.base;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.FragmentLifecycleable;
import com.jess.arms.mvvm.IViewModel;
import com.jess.arms.utils.ArmsUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * ================================================
 * 因为 Java 只能单继承,所以如果要用到需要继承特定 @{@link Fragment} 的三方库,那你就需要自己自定义 @{@link Fragment}
 * 继承于这个特定的 @{@link Fragment},然后再按照 {@link BaseFragment} 的格式,将代码复制过去,记住一定要实现{@link IFragment}
 * <p>
 * Created by JessYan on 22/03/2016
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public abstract class BaseFragment<DB extends ViewDataBinding, VM extends IViewModel> extends Fragment implements IFragment, FragmentLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;
    //是否可见
    protected boolean mIsFirstVisible=true;
    // 标志位，标志Fragment已经初始化完成。
    public boolean isPrepared = false;
    private View mRootView;

    /**
     * ViewDataBinding
     */
    protected DB mBinding;

    /**
     * MVVM ViewModel ViewModelProvider.Factory
     */
    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;
    /**
     * instance in subclass; 自动销毁
     */
    protected VM mViewModel;

    public BaseFragment() {
        //必须确保在 Fragment 实例化时 setArguments()
        setArguments(new Bundle());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onPrepareStates();
    }

    public void onVisible() {
        if (mIsFirstVisible) {
            mIsFirstVisible = false;
            onPrepareStates();
        }
    }

    private synchronized void onPrepareStates() {
        if (isPrepared) {
            loadData();
        } else {
            isPrepared = true;
        }
    }

    protected abstract void loadData();

    protected void onInVisible() {}

    /**
     * 实现Fragment数据的缓加载
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = initView(inflater, container, savedInstanceState);
        if (mViewModel != null) {
            getLifecycle().addObserver((LifecycleObserver) mViewModel);
        }
        return mRootView;
    }

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(getActivity()).cacheFactory().build(CacheType.FRAGMENT_CACHE);
        }
        return mCache;
    }


    @NonNull
    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mBinding = null;
        this.mRootView = null;
        this.mViewModelFactory = null;
        //移除LifecycleObserver
        if (mViewModel != null) {
            getLifecycle().removeObserver((LifecycleObserver) mViewModel);
        }
        this.mViewModel = null;
    }


    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    @Override
    public boolean useEventBus() {
        return true;
    }

}
