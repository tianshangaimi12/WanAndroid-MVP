package com.aimi.wanandroid_mvp.network;

import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.aimi.wanandroid_mvp.entity.BaseEntity;
import com.aimi.wanandroid_mvp.entity.TreeBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface TreeService {
    @GET("tree/json")
    Observable<BaseEntity<List<TreeBean>>> getSystemTree();

    @GET("article/list/{page}/json")
    Observable<BaseEntity<ArticleEntity>> getTreeNodeArticle(@Query("cid")int cid, @Path("page")int page);
}
