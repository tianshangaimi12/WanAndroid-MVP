package com.aimi.wanandroid_mvp.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.aimi.wanandroid_mvp.network.FirstPageService;
import com.aimi.wanandroid_mvp.network.TreeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    private static Context context;
    private static OkHttpClient okHttpClient;
    private static RetrofitUtils retrofitUtils;

    private static final String BASE_URL = "http://www.wanandroid.com";

    public static void init(Application application) {
        context = application;
        retrofitUtils = new RetrofitUtils();
        retrofitUtils.initOkHttpClient();
    }

    public static FirstPageService getFirstPageApi(){
        return retrofitUtils.createApi(FirstPageService.class, BASE_URL);
    }

    public static TreeService getTreeApi(){
        return retrofitUtils.createApi(TreeService.class, BASE_URL);
    }

    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    private synchronized void initOkHttpClient() {
        if (okHttpClient == null) {
            Cache cache = new Cache(new File(context.getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(interceptor)
                    .cookieJar(new CookieJar() {
                        private Map<String, List<Cookie>> cookieStore = new HashMap<>();

                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies == null ? new ArrayList<Cookie>() : cookies;
                        }
                    })
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS);
            okHttpClient = builder.build();
        }
    }

}
