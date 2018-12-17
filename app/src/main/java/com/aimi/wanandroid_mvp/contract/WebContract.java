package com.aimi.wanandroid_mvp.contract;

import com.aimi.wanandroid_mvp.base.IBasePresenter;
import com.aimi.wanandroid_mvp.base.IBaseView;

public interface WebContract {
    interface View extends IBaseView{
        void showWebPage(String url);
    }

    interface Presenter extends IBasePresenter{
        void loadUrl(String url);
    }
}
