package com.aimi.wanandroid_mvp.main;

import android.app.Application;

import com.aimi.wanandroid_mvp.utils.RetrofitUtils;

public class WanAndroidApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitUtils.init(this);
    }
}
