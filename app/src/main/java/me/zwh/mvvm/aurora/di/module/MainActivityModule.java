package me.zwh.mvvm.aurora.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.zwh.mvvm.aurora.mvvm.view.activity.MainActivity;

/**
 * @author zwh
 * @date 2018\3\1 0001
 */


@Module
public abstract class MainActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();
}
