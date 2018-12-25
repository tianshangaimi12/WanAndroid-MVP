package com.aimi.wanandroid_mvp.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.adapter.KnowledgeTreeAdapter;
import com.aimi.wanandroid_mvp.base.RxBaseFragment;
import com.aimi.wanandroid_mvp.contract.KnowledgeContract;
import com.aimi.wanandroid_mvp.entity.TreeBean;
import com.aimi.wanandroid_mvp.presenter.KnowledgePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class KnowledgeFragment extends RxBaseFragment<KnowledgePresenter> implements KnowledgeContract.View {
    @BindView(R.id.rv_knowledge)
    RecyclerView mRecyclerView;

    private KnowledgeTreeAdapter mAdapter;
    private List<TreeBean> mTreeBeans;
    private LinearLayoutManager lm;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTreeBeans = new ArrayList<>();
        mAdapter = new KnowledgeTreeAdapter(getContext(), mTreeBeans);
        lm = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(mAdapter);
        presenter = new KnowledgePresenter(this);
        presenter.getKnowledgeTree(this);
        mAdapter.setOnItemClickListener(position -> SystemArticleActivity.launch(getContext(), mTreeBeans.get(position)));
    }

    @Override
    public void setView(List<TreeBean> treeBeans) {
        mTreeBeans.addAll(treeBeans);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public void scrollToFirst() {
        int firstCompletelyPosition = lm.findFirstCompletelyVisibleItemPosition();
        if (firstCompletelyPosition != 0) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }
}
