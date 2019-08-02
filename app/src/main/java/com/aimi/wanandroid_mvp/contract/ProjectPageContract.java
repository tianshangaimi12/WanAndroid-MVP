package com.aimi.wanandroid_mvp.contract;

import com.aimi.wanandroid_mvp.base.IBasePresenter;
import com.aimi.wanandroid_mvp.base.IBaseView;
import com.aimi.wanandroid_mvp.base.RxBaseFragment;
import com.aimi.wanandroid_mvp.entity.ArticleEntity;

import java.util.List;

public interface ProjectPageContract {
    interface View extends IBaseView{
        void addProjects(List<ArticleEntity.ArticleBean> datas);
    }

    interface Presenter extends IBasePresenter{
        void getProjects(RxBaseFragment fragment, int cid, int page);
    }
}
