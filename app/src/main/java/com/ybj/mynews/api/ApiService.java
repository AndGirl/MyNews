package com.ybj.mynews.api;

import com.ybj.mynews.bean.BaseRespose;
import com.ybj.mynews.bean.GirlData;
import com.ybj.mynews.bean.NewsDetail;
import com.ybj.mynews.bean.NewsSummary;
import com.ybj.mynews.bean.User;
import com.ybj.mynews.bean.VideoData;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by 杨阳洋 on 2017/5/22.
 */

public interface ApiService {

    @GET("login")
    Observable<BaseRespose<User>> login(@Query("username") String name,
                                        @Query("password") String password);

    @GET("nc/article/{postId}/full.html")
    Observable<Map<String,NewsDetail>> getNewDetail(
            @Header("Cache_Control") String cacheControl,
            @Path("postId") String postId);

    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsSummary>>> getNewsList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type, @Path("id") String id,
            @Path("startPage") int startPage);

    @GET
    Observable<ResponseBody> getNewsBodyHtmlPhoto(
            @Header("Cache-Control") String cacheControl,
            @Url String photoPath);

    @GET("data/福利/{size}/{page}")
    Observable<GirlData> getPhotoList(
            @Header("Cache-Control") String cacheControl,
            @Path("size") int size,
            @Path("page") int page);

    @GET("nc/video/list/{type}/n/{startPage}-10.html")
    Observable<Map<String, List<VideoData>>> getVideoList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type,
            @Path("startPage") int startPage);

}
