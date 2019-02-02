package com.aimi.wanandroid_mvp.contract;

import com.aimi.wanandroid_mvp.base.IBasePresenter;
import com.aimi.wanandroid_mvp.base.IBaseView;
import com.aimi.wanandroid_mvp.entity.ProjectEntity;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.List;

public interface ProjectContract {
    interface View extends IBaseView {
        void setTitle(List<ProjectEntity> projectEntities);
    }

    interface Presenter extends IBasePresenter {
        void getTitle(RxFragment rxFragment);
    }
}
