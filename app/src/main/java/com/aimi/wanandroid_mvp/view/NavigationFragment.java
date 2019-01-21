package com.aimi.wanandroid_mvp.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.adapter.NaviDetailAdapter;
import com.aimi.wanandroid_mvp.adapter.NaviTitleAdapter;
import com.aimi.wanandroid_mvp.base.RxBaseFragment;
import com.aimi.wanandroid_mvp.contract.NavigationContract;
import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.aimi.wanandroid_mvp.presenter.NavigationPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NavigationFragment extends RxBaseFragment<NavigationPresenter> implements NavigationContract.View{
    @BindView(R.id.rv_navigation_title)
    RecyclerView mRecyclerViewTitle;
    @BindView(R.id.rv_navigation_detail)
    RecyclerView mRecyclerViewDetail;

    private NaviTitleAdapter mTitleAdapter;
    private NaviDetailAdapter mDetailAdapter;
    private List<ArticleEntity> mArticleEntities;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mArticleEntities = new ArrayList<>();
        mTitleAdapter = new NaviTitleAdapter(getActivity(), mArticleEntities);
        mDetailAdapter = new NaviDetailAdapter(getActivity(), mArticleEntities);
        initRecyclerView();
        presenter = new NavigationPresenter(this);
        presenter.getNavigationData(this);
    }

    private void initRecyclerView(){
        LinearLayoutManager titleManager = new LinearLayoutManager(getActivity());
        LinearLayoutManager detailManager = new LinearLayoutManager(getActivity());
        mRecyclerViewTitle.setLayoutManager(titleManager);
        mRecyclerViewDetail.setLayoutManager(detailManager);
        mRecyclerViewTitle.setAdapter(mTitleAdapter);
        mRecyclerViewDetail.setAdapter(mDetailAdapter);
    }

    @Override
    public void setView(List<ArticleEntity> articleEntities) {
        mArticleEntities.addAll(articleEntities);
        mTitleAdapter.notifyDataSetChanged();
        mDetailAdapter.notifyDataSetChanged();
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
}
