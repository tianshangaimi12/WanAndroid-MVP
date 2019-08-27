package com.aimi.wanandroid_mvp.utils;

import android.content.Context;

import com.aimi.wanandroid_mvp.main.WanAndroidApplication;

public class DisplayUtils {
    public static int dp2Px(Context context, int dp){
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(density*dp+0.5f);
    }

    public static int dp2Px(int dp){
        float density = WanAndroidApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int)(density*dp+0.5f);
    }
}
