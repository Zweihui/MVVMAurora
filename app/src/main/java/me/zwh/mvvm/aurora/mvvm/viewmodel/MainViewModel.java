package me.zwh.mvvm.aurora.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.jess.arms.mvvm.BaseViewModel;
import com.jess.arms.mvvm.vo.Resource;

import java.util.List;

import javax.inject.Inject;

import me.zwh.mvvm.aurora.app.utils.AbsentLiveData;
import me.zwh.mvvm.aurora.mvvm.model.MainModel;
import me.zwh.mvvm.aurora.mvvm.model.entry.Category;

/**
 * @author zwh
 * @date 2018\3\1 0001
 */

public class MainViewModel extends BaseViewModel<MainModel> {
    private final MutableLiveData<Boolean> refresh = new MutableLiveData<>();

    private final LiveData<Resource<List<Category>>> results;

    @Inject
    public MainViewModel(Application application, MainModel model) {
        super(application, model);
        results = Transformations.switchMap(refresh, input -> {
            if (input == null ) {
                return AbsentLiveData.create();
            } else {
                return mModel.getCategories();
            }
        });
//        getCategories();
    }

    public LiveData<Resource<List<Category>>> getResults() {
        return results;
    }

    public void getCategories(){
//        if (refresh.getValue()!=null){
//            refresh.setValue(!refresh.getValue());
//        }else {
            refresh.setValue(true);
//        }
    }
}
