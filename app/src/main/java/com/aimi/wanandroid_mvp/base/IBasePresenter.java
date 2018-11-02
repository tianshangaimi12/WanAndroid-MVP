package com.aimi.wanandroid_mvp.base;

public interface IBasePresenter<T extends IBaseView>{
    void attachView(T view);
    void detachView();
}
