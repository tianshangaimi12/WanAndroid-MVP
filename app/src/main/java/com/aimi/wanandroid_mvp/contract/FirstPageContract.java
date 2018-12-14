package com.aimi.wanandroid_mvp.contract;

import com.aimi.wanandroid_mvp.base.IBasePresenter;
import com.aimi.wanandroid_mvp.base.IBaseView;
import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.aimi.wanandroid_mvp.entity.BannerBean;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.List;

public interface FirstPageContract {
    interface View extends IBaseView{
        void setBanner(List<BannerBean> bannerBeans);
        void addArticles(ArticleEntity articleEntity);
        void addArticlesError();
    }

    interface Presenter extends IBasePresenter{
        void getBanner(RxFragment fragment);
        void getArticles(RxFragment fragment, int page);
    }
}
