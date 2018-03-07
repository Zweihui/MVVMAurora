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
import me.zwh.mvvm.aurora.mvvm.model.VideoModel;
import me.zwh.mvvm.aurora.mvvm.model.entry.VideoListInfo;

/**
 * @author zwh
 * @date 2018\3\6 0006
 */

public class VideoViewModel extends BaseViewModel<VideoModel> {
    private final MutableLiveData<String> type = new MutableLiveData<>();
    private final MutableLiveData<Integer> startId = new MutableLiveData<>();

    private final LiveData<Resource<List<VideoListInfo.Video>>> results;
    private final LiveData<Resource<List<VideoListInfo.Video>>> loadMoreResults;

    @Inject
    public VideoViewModel(Application application, VideoModel model) {
        super(application, model);
        results = Transformations.switchMap(type, input -> {
            if (input == null) {
                return AbsentLiveData.create();
            } else {
                return mModel.getIndexVideoList(0,type+"1", type.getValue(),true);
            }
        });
        loadMoreResults = Transformations.switchMap(startId, input -> {
            if (input == null) {
                return AbsentLiveData.create();
            } else {
                return mModel.getIndexVideoList(startId.getValue(),type+getLastIdQueried (), type.getValue(),false);
            }
        });
    }

    public LiveData<Resource<List<VideoListInfo.Video>>> getResults() {
        return results;
    }
    public LiveData<Resource<List<VideoListInfo.Video>>> getLoadMoreResults() {
        return loadMoreResults;
    }

    public void setIndexVideoList(String type) {
        this.type.setValue(type);
    }

    public void setLoadMoreVideoList(int startId) {
        String value = type.getValue();
        if (value == null ) {
            return;
        }
        this.startId.setValue(startId);
    }

    private String getLastIdQueried (){
        String lastIdQueried = "";
        try {
            lastIdQueried = results.getValue().data.get(results.getValue().data.size()-1).getData().getId() + "";
        }catch (Exception e){

        }
        return lastIdQueried;
    }

}
