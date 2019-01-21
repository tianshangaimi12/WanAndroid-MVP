package com.aimi.wanandroid_mvp.contract;

import com.aimi.wanandroid_mvp.base.IBasePresenter;
import com.aimi.wanandroid_mvp.base.IBaseView;
import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.List;

public interface NavigationContract {
    interface View extends IBaseView{
        void setView(List<ArticleEntity> articleEntities);
    }

    interface Presenter extends IBasePresenter{
        void getNavigationData(RxFragment rxFragment);
    }
}
