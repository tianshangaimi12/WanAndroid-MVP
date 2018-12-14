package com.aimi.wanandroid_mvp.network;

import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.aimi.wanandroid_mvp.entity.BannerBean;
import com.aimi.wanandroid_mvp.entity.BaseEntity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface FirstPageService {
    @GET("banner/json")
    Observable<BaseEntity<List<BannerBean>>> getBanners();

    @GET("article/list/{page}/json")
    Observable<BaseEntity<ArticleEntity>> getArticles(@Path("page")int page);
}
