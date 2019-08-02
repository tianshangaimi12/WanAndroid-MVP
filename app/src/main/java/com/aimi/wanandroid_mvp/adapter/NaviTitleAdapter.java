package com.aimi.wanandroid_mvp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.base.OnItemClickListener;
import com.aimi.wanandroid_mvp.entity.ArticleEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NaviTitleAdapter extends RecyclerView.Adapter<NaviTitleAdapter.TitleViewHolder> {
    private Context context;
    private List<ArticleEntity> articleEntities;
    private OnItemClickListener onItemClickListener;

    public NaviTitleAdapter(Context context, List<ArticleEntity> articleEntities) {
        this.context = context;
        this.articleEntities = articleEntities;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TitleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_navigation_title, viewGroup, false);
        return new TitleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TitleViewHolder titleViewHolder, int i) {
        ArticleEntity entity = articleEntities.get(i);
        titleViewHolder.mTvTitle.setText(entity.getName());
        titleViewHolder.mTvTitle.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(v, i);
            }
        });
        if (entity.isSelect()){
            titleViewHolder.itemView.setBackgroundColor(Color.WHITE);
            titleViewHolder.mTvTitle.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            titleViewHolder.mTvTitle.setTextColor(context.getResources().getColor(R.color.colorMediumGray));
            titleViewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.navigationGray));

        }
    }

    @Override
    public int getItemCount() {
        return articleEntities == null ? 0 : articleEntities.size();
    }

    static class TitleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_navigation_title)
        TextView mTvTitle;

        TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
