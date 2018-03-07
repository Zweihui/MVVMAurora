package me.zwh.mvvm.aurora.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.jess.arms.di.scope.ViewModelScope;
import com.jess.arms.mvvm.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import me.zwh.mvvm.aurora.mvvm.viewmodel.MainViewModel;
import me.zwh.mvvm.aurora.mvvm.viewmodel.VideoViewModel;

/**
 * @author zwh
 * @date 2018\3\1 0001
 */

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelScope(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelScope(VideoViewModel.class)
    abstract ViewModel bindVideoViewModel(VideoViewModel mainViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
