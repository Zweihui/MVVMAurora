package me.zwh.mvvm.aurora.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.AppScope;

import dagger.Component;
import me.zwh.mvvm.aurora.app.App;
import me.zwh.mvvm.aurora.di.module.AuroraModule;

/**
 * @author zwh
 * @date 2018\2\27 0027
 */

@AppScope
@Component(dependencies = AppComponent.class,modules = AuroraModule.class)
public interface AuroraComponent {
    void inject(App app);
}
