package com.aimi.wanandroid_mvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.internal.FlowLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.entity.TreeBean;
import com.aimi.wanandroid_mvp.utils.ConstantUtils;
import com.aimi.wanandroid_mvp.utils.DisplayUtils;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KnowledgeTreeAdapter extends RecyclerView.Adapter<KnowledgeTreeAdapter.TreeHolder> {
    private Context context;
    private List<TreeBean> treeBeans;
    private OnItemClickListener onItemClickListener;

    public KnowledgeTreeAdapter(Context context, List<TreeBean> treeBeans){
        this.context = context;
        this.treeBeans = treeBeans;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TreeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_knowledge_tree, viewGroup, false);
        return new TreeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreeHolder treeHolder, int i) {
        TreeBean treeBean = treeBeans.get(i);
        treeHolder.mTvTitle.setText(treeBean.getName());
        Random random = new Random();
        int randomNum = random.nextInt(7);
        treeHolder.mTvTitle.setTextColor(context.getResources().getColor(ConstantUtils.TREE_NODE_TITLE_COLORS.get(randomNum)));
        treeHolder.mLayoutTags.removeAllViews();
        List<TreeBean> beanList = treeBean.getChildren();
        for (TreeBean treeBeanNode : beanList){
            TextView tv = new TextView(context);
            tv.setTextColor(context.getResources().getColor(R.color.colorMediumGray));
            tv.setText(treeBeanNode.getName());
            tv.setTextSize(14);
            tv.setPadding(0, 0, DisplayUtils.dp2Px(context, 5), DisplayUtils.dp2Px(context, 5));
            treeHolder.mLayoutTags.addView(tv);
        }
        treeHolder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null){
                onItemClickListener.onClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return treeBeans == null ? 0 : treeBeans.size();
    }

    public static class TreeHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_knowledge_tree_node_title)
        TextView mTvTitle;
        @BindView(R.id.flow_layout_tree_node)
        FlowLayout mLayoutTags;

        TreeHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }
}
