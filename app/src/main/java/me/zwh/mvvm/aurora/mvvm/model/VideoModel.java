package me.zwh.mvvm.aurora.mvvm.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvvm.BaseModel;
import com.jess.arms.mvvm.vo.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.zwh.mvvm.aurora.app.constants.Constants;
import me.zwh.mvvm.aurora.mvvm.model.api.cache.CommonCache;
import me.zwh.mvvm.aurora.mvvm.model.api.service.VideoService;
import me.zwh.mvvm.aurora.mvvm.model.entry.IndextVideoListInfo;
import me.zwh.mvvm.aurora.mvvm.model.entry.VideoListInfo;

/**
 * @author zwh
 * @date 2018\3\6 0006
 */

public class VideoModel extends BaseModel {

    private RxErrorHandler mErrorHandler;

    @Inject
    public VideoModel(IRepositoryManager mRepositoryManager, RxErrorHandler mErrorHandler) {
        super(mRepositoryManager);
        this.mErrorHandler = mErrorHandler;
    }

    public LiveData<Resource<List<VideoListInfo.Video>>> getIndexVideoList(int start,String lastIdQueried,String type,boolean update) {
        MutableLiveData<Resource<List<VideoListInfo.Video>>> mResult = new MutableLiveData<>();
        Observable<VideoListInfo> videoInfo = mRepositoryManager.obtainRetrofitService(VideoService.class)
                .getVideoList(start, Constants.HOME_VIDEO_LIST_PAGE_SIZE,type);
        mRepositoryManager.obtainCacheService(CommonCache.class)
                .getVideoList(videoInfo
                        , new DynamicKey(lastIdQueried)
                        , new EvictDynamicKey(update))
                .flatMap(listReply -> Observable.just(listReply.getData()))
                .subscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleSubscriber<VideoListInfo>(mErrorHandler) {

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        mResult.postValue(Resource.loading(null));
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mResult.postValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(VideoListInfo info) {
                        mResult.postValue(Resource.success(info.getItemList()));
                    }
                });

        return mResult;
    }

    private List<VideoListInfo.Video> getIndexVideoList(IndextVideoListInfo info) {
        List<VideoListInfo.Video> list = new ArrayList<>();
        for (IndextVideoListInfo.ItemList itemList: info.getItemList()){
            if (!itemList.getType().equals("followCard")){
                continue;
            }
            if (itemList.getData().getContent() ==null){
                continue;
            }
            if (itemList.getData().getContent().getType().equals("video")){
                list.add(itemList.getData().getContent());
            }
        }
        return list;
    }
}
