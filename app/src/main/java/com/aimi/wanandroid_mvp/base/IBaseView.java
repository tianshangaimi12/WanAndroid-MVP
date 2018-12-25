package com.aimi.wanandroid_mvp.base;

public interface IBaseView {
    void showToast(String message);
    void showToast(int resId);
    void showLoading();
    void hideLoading();
}
