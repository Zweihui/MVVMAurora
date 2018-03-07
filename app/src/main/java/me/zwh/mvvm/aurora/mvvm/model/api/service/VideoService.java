package me.zwh.mvvm.aurora.mvvm.model.api.service;


import java.util.List;

import io.reactivex.Observable;
import me.zwh.mvvm.aurora.mvvm.model.entry.Category;
import me.zwh.mvvm.aurora.mvvm.model.entry.IndextVideoListInfo;
import me.zwh.mvvm.aurora.mvvm.model.entry.VideoListInfo;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author zwh
 * @date 2018\3\7 0006
 */

public interface VideoService {
    @GET("v5/index/tab/feed")
    Observable<IndextVideoListInfo> getIndexVideoList(@Query("date") long date,@Query("num") int num, @Query("udid") String udid, @Query("vc") String vc, @Query("vn") String vn, @Query("deviceModel") String deviceModel);
    //获取分类信息
    @GET("v2/categories")
    Observable<List<Category>> getCategories();
    //获取各分类下的视频列表
    @GET("v3/videos")
    Observable<VideoListInfo> getVideoList(@Query("start") int startCount, @Query("num") int num, @Query("categoryName") String categoryName);
}
