package com.aimi.wanandroid_mvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.internal.FlowLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.aimi.wanandroid_mvp.utils.ConstantUtils;
import com.aimi.wanandroid_mvp.view.WebActivity;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NaviDetailAdapter extends RecyclerView.Adapter<NaviDetailAdapter.DetailViewHolder> {
    private Context context;
    private List<ArticleEntity> articleEntities;

    public NaviDetailAdapter(Context context, List<ArticleEntity> articleEntities) {
        this.context = context;
        this.articleEntities = articleEntities;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_navigation_detail, viewGroup, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder detailViewHolder, int i) {
        ArticleEntity articleEntity = articleEntities.get(i);
        detailViewHolder.mTvTitle.setText(articleEntity.getName());
        List<ArticleEntity.ArticleBean> articleBeans = articleEntity.getArticles();
        for (ArticleEntity.ArticleBean articleBean : articleBeans) {
            Button button = new Button(context);
            button.setBackgroundResource(R.drawable.shape_navi_detail_item);
            button.setText(articleBean.getTitle());
            button.setTextSize(13);
            int randomNum = new Random().nextInt(7);
            button.setTextColor(context.getResources().getColor(ConstantUtils.TREE_NODE_TITLE_COLORS.get(randomNum)));
            button.setOnClickListener(v -> WebActivity.launch(context, articleBean));
            detailViewHolder.mFlowLayout.addView(button);
        }
    }

    @Override
    public int getItemCount() {
        return articleEntities == null ? 0 : articleEntities.size();
    }

    static class DetailViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_navigation_detail_title)
        TextView mTvTitle;
        @BindView(R.id.flow_layout_navigation_detail)
        FlowLayout mFlowLayout;

        DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}