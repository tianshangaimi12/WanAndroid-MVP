package com.aimi.wanandroid_mvp.contract;

import com.aimi.wanandroid_mvp.base.IBasePresenter;
import com.aimi.wanandroid_mvp.base.IBaseView;

public interface SplashContract {

    interface View extends IBaseView{
        void showNext();
    }

    interface Presenter extends IBasePresenter{
        void jumpToMain();
    }
}
