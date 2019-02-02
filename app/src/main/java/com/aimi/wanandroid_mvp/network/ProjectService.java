package com.aimi.wanandroid_mvp.network;

import com.aimi.wanandroid_mvp.entity.BaseEntity;
import com.aimi.wanandroid_mvp.entity.ProjectEntity;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface ProjectService {
    @GET("project/tree/json")
    Observable<BaseEntity<List<ProjectEntity>>> getProjectTitle();
}
