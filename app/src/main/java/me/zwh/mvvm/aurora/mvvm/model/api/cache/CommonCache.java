package me.zwh.mvvm.aurora.mvvm.model.api.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;
import me.zwh.mvvm.aurora.mvvm.model.entry.Category;
import me.zwh.mvvm.aurora.mvvm.model.entry.VideoListInfo;

/**
 * @author zwh
 * @date 2018\3\7 0006
 */
public interface CommonCache {


    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<VideoListInfo>> getVideoList(Observable<VideoListInfo> videolistInfo, DynamicKey lastQueried, EvictProvider evictProvider);

    @LifeCache(duration = 3, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<Category>>> getCategories(Observable<List<Category>> categories);

}
