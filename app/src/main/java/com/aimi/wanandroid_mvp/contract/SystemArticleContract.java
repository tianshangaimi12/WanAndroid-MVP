package com.aimi.wanandroid_mvp.contract;

import com.aimi.wanandroid_mvp.base.IBasePresenter;
import com.aimi.wanandroid_mvp.base.IBaseView;
import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.trello.rxlifecycle.components.support.RxFragment;

public interface SystemArticleContract {
    interface View extends IBaseView{
        void addArticles(ArticleEntity articleEntity);
    }

    interface Presenter extends IBasePresenter{
        void getArticles(RxFragment fragment, int page, int cid);
    }
}
