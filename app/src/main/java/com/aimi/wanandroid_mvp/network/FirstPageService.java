package com.aimi.wanandroid_mvp.network;

import com.aimi.wanandroid_mvp.Entity.ArticleEntity;
import com.aimi.wanandroid_mvp.Entity.BaseEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface FirstPageService {
    @GET("article/list/{page}/json")
    Observable<BaseEntity<ArticleEntity>> getArticles(@Path("page")int page);
}
