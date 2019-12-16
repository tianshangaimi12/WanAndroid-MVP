package com.aimi.wanandroid_mvp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.aimi.wanandroid_mvp.entity.BannerBean;
import com.aimi.wanandroid_mvp.view.WebActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstPageAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<BannerBean> bannerBeans;
    private List<ArticleEntity.ArticleBean> articleBeans;

    private static final int TYPE_BANNER = 0;
    private static final int TYPE_ARTICLE = 1;

    public FirstPageAdapter(Context context, List<BannerBean> bannerBeans, List<ArticleEntity.ArticleBean> articleBeans) {
        this.context = context;
        this.bannerBeans = bannerBeans;
        this.articleBeans = articleBeans;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        } else {
            return TYPE_ARTICLE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View view;
        if (type == TYPE_BANNER) {
            view = LayoutInflater.from(context).inflate(R.layout.item_banner, viewGroup, false);
            return new BannerHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_article, viewGroup, false);
            return new ArticleHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int itemPosition) {
        if (itemPosition == 0) {
            BannerHolder holder = (BannerHolder) viewHolder;
            BannerAdapter adapter = new BannerAdapter(context, bannerBeans);
            holder.mViewPagerBanner.setAdapter(adapter);
            holder.mViewPagerBanner.setPageTransformer(true, (page, position) -> {
                page.setPivotY(page.getHeight() / 2);
                float maxRotate = 35f;
                float minScale = 0.8f;
                float maxTranslationX = page.getWidth() / 5;
                if (position <= -1) {
                    page.setRotationY(maxRotate);
                    page.setPivotX(0);
                    page.setScaleX(minScale);
                    page.setScaleY(minScale);
                    page.setTranslationX(maxTranslationX);
                } else if (position < 1) {
                    page.setRotationY(-position * maxRotate);
                    if (position < 0) {
                        page.setPivotX(0);
                        page.setScaleX(1 + position * (1 - minScale));
                        page.setScaleY(1 + position * (1 - minScale));
                        page.setTranslationX(-position * maxTranslationX);
                    } else {
                        page.setPivotX(page.getWidth());
                        page.setScaleX(1 - position * (1 - minScale));
                        page.setScaleY(1 - position * (1 - minScale));
                        page.setTranslationX(-position * maxTranslationX);
                    }
                } else {
                    page.setRotationY(-1 * maxRotate);
                    page.setPivotX(page.getWidth());
                    page.setScaleX(minScale);
                    page.setScaleY(minScale);
                    page.setTranslationX(-maxTranslationX);
                }
            });
            holder.mViewPagerBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                }

                @Override
                public void onPageSelected(int i) {
                    BannerBean bannerBean = bannerBeans.get(i);
                    holder.mTvBannerTitle.setText(bannerBean.getTitle());
                    holder.mTvBannerIndex.setText(String.format(context.getString(R.string.banner_index), (i + 1), bannerBeans.size()));
                }

                @Override
                public void onPageScrollStateChanged(int i) {
                }
            });
            holder.mViewPagerBanner.setCurrentItem(0);
            if (bannerBeans.size() > 0) {
                holder.mTvBannerTitle.setText(bannerBeans.get(0).getTitle());
                holder.mTvBannerIndex.setText(String.format(context.getString(R.string.banner_index), 1, bannerBeans.size()));
            }
        } else {
            ArticleHolder holder = (ArticleHolder) viewHolder;
            ArticleEntity.ArticleBean articleBean = articleBeans.get(itemPosition - 1);
            holder.mTvAuthor.setText(articleBean.getAuthor());
            holder.mTvCategory.setText(String.format(context.getString(R.string.article_category),
                    articleBean.getSuperChapterName(), articleBean.getChapterName()));
            holder.mTvTitle.setText(articleBean.getTitle());
            holder.mTvTime.setText(articleBean.getNiceDate());
            if (articleBean.isCollect()) {
                holder.mImgCollect.setImageResource(R.mipmap.ic_love_red);
            } else {
                holder.mImgCollect.setImageResource(R.mipmap.ic_love_gray);
            }
            if (TextUtils.isEmpty(articleBean.getProjectLink())) {
                holder.mBtnProject.setVisibility(View.INVISIBLE);
            } else {
                holder.mBtnProject.setVisibility(View.VISIBLE);
            }
            holder.itemView.setOnClickListener(v -> WebActivity.launch(context, articleBean));
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (bannerBeans != null && bannerBeans.size() > 0) {
            count += 1;
        }
        if (articleBeans != null) {
            count += articleBeans.size();
        }
        return count;
    }

    public static class ArticleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_collect_article)
        ImageView mImgCollect;
        @BindView(R.id.tv_article_author_name)
        TextView mTvAuthor;
        @BindView(R.id.tv_article_category)
        TextView mTvCategory;
        @BindView(R.id.tv_article_title)
        TextView mTvTitle;
        @BindView(R.id.tv_article_time)
        TextView mTvTime;
        @BindView(R.id.btn_article_project)
        Button mBtnProject;

        public ArticleHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class BannerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_pager_first_page)
        ViewPager mViewPagerBanner;
        @BindView(R.id.tv_banner_title)
        TextView mTvBannerTitle;
        @BindView(R.id.tv_banner_index)
        TextView mTvBannerIndex;

        public BannerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
