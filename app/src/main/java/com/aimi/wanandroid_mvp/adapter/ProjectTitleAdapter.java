package com.aimi.wanandroid_mvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.base.OnItemClickListener;
import com.aimi.wanandroid_mvp.entity.ProjectEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectTitleAdapter extends RecyclerView.Adapter<ProjectTitleAdapter.TitleHolder> {
    private Context context;
    private List<ProjectEntity> projectEntities;
    private OnItemClickListener onItemClickListener;

    public ProjectTitleAdapter(Context context, List<ProjectEntity> projectEntities) {
        this.context = context;
        this.projectEntities = projectEntities;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TitleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_project_title, viewGroup, false);
        return new TitleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TitleHolder titleHolder, int i) {
        ProjectEntity entity = projectEntities.get(i);
        titleHolder.mTitleNameTv.setText(entity.getName());
        titleHolder.mTitleNameTv.setTextColor(context.getResources().getColor(entity.isSelect() ? R.color.colorPrimary : R.color.colorLightGray));
        titleHolder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(v, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectEntities == null ? 0 : projectEntities.size();
    }

    static class TitleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_project_title_name)
        TextView mTitleNameTv;

        TitleHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
