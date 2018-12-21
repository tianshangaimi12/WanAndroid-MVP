package com.aimi.wanandroid_mvp.utils;

import android.content.Context;

public class DisplayUtils {
    public static int dp2Px(Context context, int dp){
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(density*dp+0.5f);
    }
}
