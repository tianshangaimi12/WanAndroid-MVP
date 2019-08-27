package com.aimi.wanandroid_mvp.network;

import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.aimi.wanandroid_mvp.entity.BannerBean;
import com.aimi.wanandroid_mvp.entity.BaseEntity;
import com.aimi.wanandroid_mvp.entity.WebsiteEntity;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface FirstPageService {
    @GET("banner/json")
    Observable<BaseEntity<List<BannerBean>>> getBanners();

    @GET("article/list/{page}/json")
    Observable<BaseEntity<ArticleEntity>> getArticles(@Path("page") int page);

    @GET("friend/json")
    Observable<BaseEntity<List<WebsiteEntity>>> getWebsites();

    @GET("hotkey/json")
    Observable<BaseEntity<List<WebsiteEntity>>> getHotKeys();

    @FormUrlEncoded
    @POST("article/query/{page}/json")
    Observable<BaseEntity<ArticleEntity>> search(@Field("k") String key, @Path("page") int page);
}
