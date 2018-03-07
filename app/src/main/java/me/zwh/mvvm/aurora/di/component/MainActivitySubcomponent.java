package me.zwh.mvvm.aurora.di.component;

import dagger.android.AndroidInjector;
import me.zwh.mvvm.aurora.mvvm.view.activity.MainActivity;

/**
 * @author zwh
 * @date 2018\3\1 0001
 */
//@ActivityScope
//@Subcomponent(modules = {MainModule.class, MainViewModelModule.class})//DataModule
public interface MainActivitySubcomponent extends AndroidInjector<MainActivity>{

    /**
     * AndroidInjector.Builder
     */
//    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {
    }

}
