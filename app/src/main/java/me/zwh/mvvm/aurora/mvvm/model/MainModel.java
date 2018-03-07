package me.zwh.mvvm.aurora.mvvm.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvvm.BaseModel;
import com.jess.arms.mvvm.vo.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.zwh.mvvm.aurora.mvvm.model.api.cache.CommonCache;
import me.zwh.mvvm.aurora.mvvm.model.api.service.VideoService;
import me.zwh.mvvm.aurora.mvvm.model.entry.Category;

/**
 * @author zwh
 * @date 2018\3\1 0001
 */

public class MainModel extends BaseModel {

    private RxErrorHandler mErrorHandler;

    @Inject
    public MainModel(IRepositoryManager mRepositoryManager, RxErrorHandler mErrorHandler) {
        super(mRepositoryManager);
        this.mErrorHandler = mErrorHandler;
    }

    public LiveData<Resource<List<Category>>> getCategories() {
        MutableLiveData<Resource<List<Category>>> mResult = new MutableLiveData<>();
        Observable<List<Category>> categories = mRepositoryManager.obtainRetrofitService(VideoService.class)
                .getCategories();
        mRepositoryManager.obtainCacheService(CommonCache.class)
                .getCategories(categories)
                .flatMap(listReply -> Observable.just(listReply.getData()))
                .subscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleSubscriber<List<Category>>(mErrorHandler) {

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
                    public void onNext(List<Category> categories) {
                        mResult.postValue(Resource.success(categories));
                    }
                });
        return mResult;
    }

}
