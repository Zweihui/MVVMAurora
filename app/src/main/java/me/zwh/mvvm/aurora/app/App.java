package me.zwh.mvvm.aurora.app;

import com.jess.arms.base.BaseApplication;

import me.zwh.mvvm.aurora.di.component.AuroraComponent;
import me.zwh.mvvm.aurora.di.component.DaggerAuroraComponent;

/**
 * @author zwh
 * @date 2018\2\27 0027
 */

public class App extends BaseApplication {

    private AuroraComponent mAuroraComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAuroraComponent = DaggerAuroraComponent
                .builder()
                .appComponent(getAppComponent())
                .build();
        mAuroraComponent.inject(this);
    }
    public AuroraComponent getAuroraComponent() {
        return this.mAuroraComponent;
    }

}
