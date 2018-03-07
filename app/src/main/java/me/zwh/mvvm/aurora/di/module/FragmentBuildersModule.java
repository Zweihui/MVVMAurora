package me.zwh.mvvm.aurora.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.zwh.mvvm.aurora.mvvm.view.fragment.VideoListFragment;

/**
 * @author zwh
 * @date 2018\3\6 0006
 */

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract VideoListFragment contributeRepoFragment();
}