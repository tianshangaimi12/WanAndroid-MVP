package com.aimi.wanandroid_mvp.presenter;

import com.aimi.wanandroid_mvp.contract.SplashContract;

public class SplashPresenter implements SplashContract.Presenter{
    private SplashContract.View view;

    public SplashPresenter(SplashContract.View view){
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void jumpToMain() {
        view.showNext();
    }
}
