package com.aimi.wanandroid_mvp.presenter;

import com.aimi.wanandroid_mvp.contract.WebContract;

public class WebPresenter implements WebContract.Presenter{
    private WebContract.View view;

    public WebPresenter(WebContract.View view){
        this.view = view;
    }

    @Override
    public void loadUrl(String url) {
        view.showWebPage(url);
    }

    @Override
    public void detachView() {
        view = null;
    }
}
