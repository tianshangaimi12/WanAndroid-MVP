package com.aimi.wanandroid_mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.entity.BannerBean;
import com.aimi.wanandroid_mvp.view.WebActivity;
import com.bumptech.glide.Glide;

import java.util.List;

public class BannerAdapter extends PagerAdapter {
    private Context context;
    private List<BannerBean> bannerBeans;

    public BannerAdapter(Context context, List<BannerBean> bannerBeans) {
        this.context = context;
        this.bannerBeans = bannerBeans;
    }

    @Override
    public int getCount() {
        return bannerBeans == null ? 0 : bannerBeans.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_first_page_banner, container, false);
        ImageView img = view.findViewById(R.id.img_banner);
        Glide.with(context).load(bannerBeans.get(position).getImagePath()).into(img);
        container.addView(view);
        view.setOnClickListener(v -> WebActivity.launch(context, bannerBeans.get(position)));
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
