package com.aimi.wanandroid_mvp.contract;

import com.aimi.wanandroid_mvp.base.IBasePresenter;
import com.aimi.wanandroid_mvp.base.IBaseView;
import com.aimi.wanandroid_mvp.entity.TreeBean;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.List;

public interface KnowledgeContract {
    interface View extends IBaseView{
        void setView(List<TreeBean> treeBeans);
    }

    interface Presenter extends IBasePresenter{
        void getKnowledgeTree(RxFragment rxFragment);
    }
}
