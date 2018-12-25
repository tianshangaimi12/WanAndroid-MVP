package com.aimi.wanandroid_mvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.aimi.wanandroid_mvp.view.WebActivity;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<FirstPageAdapter.ArticleHolder>{
    private Context context;
    private List<ArticleEntity.ArticleBean> articleBeans;

    public ArticleAdapter(Context context, List<ArticleEntity.ArticleBean> articleBeans){
        this.context = context;
        this.articleBeans = articleBeans;
    }

    @NonNull
    @Override
    public FirstPageAdapter.ArticleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, viewGroup, false);
        return new FirstPageAdapter.ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FirstPageAdapter.ArticleHolder articleHolder, int i) {
        ArticleEntity.ArticleBean articleBean = articleBeans.get(i);
        articleHolder.mTvAuthor.setText(articleBean.getAuthor());
        articleHolder.mTvCategory.setText(String.format(context.getString(R.string.article_category),
                articleBean.getSuperChapterName(), articleBean.getChapterName()));
        articleHolder.mTvTitle.setText(articleBean.getTitle());
        articleHolder.mTvTime.setText(articleBean.getNiceDate());
        if (articleBean.isCollect()) {
            articleHolder.mImgCollect.setImageResource(R.mipmap.ic_love_red);
        } else {
            articleHolder.mImgCollect.setImageResource(R.mipmap.ic_love_gray);
        }
        if (TextUtils.isEmpty(articleBean.getProjectLink())) {
            articleHolder.mBtnProject.setVisibility(View.INVISIBLE);
        } else {
            articleHolder.mBtnProject.setVisibility(View.VISIBLE);
        }
        articleHolder.itemView.setOnClickListener(v -> WebActivity.launch(context, articleBean));
    }

    @Override
    public int getItemCount() {
        return articleBeans == null ? 0 : articleBeans.size();
    }
}
