package com.aimi.wanandroid_mvp.main;

import android.app.Application;
import android.content.Context;

import com.aimi.wanandroid_mvp.utils.RetrofitUtils;

public class WanAndroidApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitUtils.init(this);
        context = getApplicationContext();
    }

    public static Context getInstance() {
        return context;
    }
}
