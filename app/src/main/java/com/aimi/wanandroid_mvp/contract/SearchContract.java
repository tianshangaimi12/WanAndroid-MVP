package com.aimi.wanandroid_mvp.contract;

import com.aimi.wanandroid_mvp.base.IBasePresenter;
import com.aimi.wanandroid_mvp.base.IBaseView;
import com.aimi.wanandroid_mvp.base.RxBaseFragment;
import com.aimi.wanandroid_mvp.entity.ArticleEntity;

public interface SearchContract {
    interface View extends IBaseView {

        void showSearchResult(ArticleEntity articleEntity);
    }

    interface Presenter extends IBasePresenter {
        void getSearchResult(RxBaseFragment fragment, String key, int page);
    }
}
