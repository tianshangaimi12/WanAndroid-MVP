package com.aimi.wanandroid_mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.adapter.ArticleAdapter;
import com.aimi.wanandroid_mvp.base.RxBaseFragment;
import com.aimi.wanandroid_mvp.contract.ProjectPageContract;
import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.aimi.wanandroid_mvp.presenter.ProjectPagePresenter;
import com.aimi.wanandroid_mvp.utils.ConstantUtils;
import com.airbnb.lottie.LottieAnimationView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectPageFragment extends RxBaseFragment<ProjectPagePresenter> implements ProjectPageContract.View {
    @BindView(R.id.rv_project_page)
    RecyclerView mRecyclerView;
    @BindView(R.id.smart_refresh_layout_project)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.anim_loading)
    LottieAnimationView mLoading;

    private int cid;
    private int page = 0;
    private boolean isViewCreated = false;
    private boolean isViewVisible = false;
    private ArticleAdapter mAdapter;
    private List<ArticleEntity.ArticleBean> mArticleBeans;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_page;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            cid = bundle.getInt(ConstantUtils.EXTRA_PROJECT_CID);
        }
        mArticleBeans = new ArrayList<>();
        mAdapter = new ArticleAdapter(getContext(), mArticleBeans);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        presenter = new ProjectPagePresenter(this);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mArticleBeans.clear();
            page = 0;
            presenter.getProjects(ProjectPageFragment.this, page, cid);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            presenter.getProjects(ProjectPageFragment.this, page, cid);
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewVisible = isVisibleToUser;
        if (isViewVisible) {
            lazyLoad();
        }
    }

    public void lazyLoad() {
        if (isViewCreated && isViewVisible) {
            presenter.getProjects(this, page, cid);
            isViewVisible = false;
            isViewCreated = false;
        }
    }

    @Override
    public void addProjects(List<ArticleEntity.ArticleBean> datas) {
        mArticleBeans.addAll(datas);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh(2000);
        mRefreshLayout.finishLoadMore(1000);
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
        mRefreshLayout.setVisibility(View.INVISIBLE);
        mLoading.setVisibility(View.VISIBLE);
        mLoading.setAnimation("stopwatch.json");
        mLoading.playAnimation();
    }

    @Override
    public void hideLoading() {
        if (mRefreshLayout != null){
            mRefreshLayout.setVisibility(View.VISIBLE);
        }
        if (mLoading != null){
            mLoading.setVisibility(View.INVISIBLE);
            mLoading.cancelAnimation();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideLoading();
    }

    public void scrollToFirst(){
        if (mRecyclerView != null){
            mRecyclerView.smoothScrollToPosition(0);
        }
    }
}
