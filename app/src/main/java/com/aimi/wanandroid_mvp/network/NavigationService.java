package com.aimi.wanandroid_mvp.network;

import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.aimi.wanandroid_mvp.entity.BaseEntity;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface NavigationService {
    @GET("navi/json")
    Observable<BaseEntity<List<ArticleEntity>>> getNavigationData();
}
