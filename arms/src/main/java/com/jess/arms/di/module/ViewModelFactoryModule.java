package com.jess.arms.di.module;

import android.arch.lifecycle.ViewModelProvider;

import com.jess.arms.mvvm.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

/**
 * @author zwh
 * @date 2018/2/27
 * Dagger ViewModelFactoryModule
 */
@Module
public abstract class ViewModelFactoryModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
