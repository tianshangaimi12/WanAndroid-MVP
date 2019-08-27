package com.aimi.wanandroid_mvp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.aimi.wanandroid_mvp.main.WanAndroidApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreferenceUtils {
    private static final int MAX_SEARCH_HISTORY = 9;
    private static final String PREFERENCE_NAME = "WanAndroid";
    private static final String SEARCH_HISTORY = "search_history";

    // 保存搜索记录
    public static void saveSearchHistory(String inputText) {
        SharedPreferences sp = WanAndroidApplication.getInstance().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (TextUtils.isEmpty(inputText)) {
            return;
        }
        String longHistory = sp.getString(SEARCH_HISTORY, "");  //获取之前保存的历史记录
        String[] tmpHistory = longHistory.split(","); //逗号截取 保存在数组中
        List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory)); //将改数组转换成ArrayList
        SharedPreferences.Editor editor = sp.edit();
        if (historyList.size() > 0) {
            for (int i = 0; i < historyList.size(); i++) {
                if (inputText.equals(historyList.get(i))) {
                    historyList.remove(i);
                    break;
                }
            }
            historyList.add(0, inputText);
            if (historyList.size() > MAX_SEARCH_HISTORY) {
                historyList.remove(historyList.size() - 1);
            }
            //逗号拼接
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < historyList.size(); i++) {
                sb.append(historyList.get(i)).append(",");
            }
            //保存到sp
            editor.putString(SEARCH_HISTORY, sb.toString());
            editor.apply();
        } else {
            //之前未添加过
            editor.putString(SEARCH_HISTORY, inputText + ",");
            editor.commit();
        }
    }

    //获取搜索记录
    public static List<String> getSearchHistory() {
        SharedPreferences sp = WanAndroidApplication.getInstance().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        String longHistory = sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longHistory.split(",");
        List<String> historyList = new ArrayList<>(Arrays.asList(tmpHistory));
        if (historyList.size() == 1 && historyList.get(0).equals("")) {
            historyList.clear();
        }
        return historyList;
    }

    //删除所有搜索记录
    public static boolean deleteSearchHistory(){
        SharedPreferences sp = WanAndroidApplication.getInstance().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SEARCH_HISTORY, "");
        return editor.commit();
    }
}
