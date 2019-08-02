package com.aimi.wanandroid_mvp.network;

import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.aimi.wanandroid_mvp.entity.BaseEntity;
import com.aimi.wanandroid_mvp.entity.ProjectEntity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface ProjectService {
    @GET("project/tree/json")
    Observable<BaseEntity<List<ProjectEntity>>> getProjectTitle();

    @GET("project/list/{page}/json")
    Observable<BaseEntity<ArticleEntity>> getProjectItems(@Path("page") int page,@Query ("cid") int id);
}
