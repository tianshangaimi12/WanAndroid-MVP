package com.aimi.wanandroid_mvp.contract;

import com.aimi.wanandroid_mvp.base.IBasePresenter;
import com.aimi.wanandroid_mvp.base.IBaseView;
import com.aimi.wanandroid_mvp.base.RxBaseFragment;

import java.util.List;

public interface SearchHistoryContract {
    interface View extends IBaseView {
        void showSearchHistory(List<String> search);

        void showHotSearch(List<String> search);
    }

    interface Presenter extends IBasePresenter {
        void getSearchHistory();

        void getHotSearch(RxBaseFragment fragment);
    }
}
