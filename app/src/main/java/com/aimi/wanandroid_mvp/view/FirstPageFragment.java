package com.aimi.wanandroid_mvp.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.adapter.FirstPageAdapter;
import com.aimi.wanandroid_mvp.base.RxBaseFragment;
import com.aimi.wanandroid_mvp.contract.FirstPageContract;
import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.aimi.wanandroid_mvp.entity.BannerBean;
import com.aimi.wanandroid_mvp.presenter.FirstPagePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FirstPageFragment extends RxBaseFragment<FirstPagePresenter> implements FirstPageContract.View {
    @BindView(R.id.rv_first_page)
    RecyclerView mRecyclerView;
    @BindView(R.id.smart_refresh_layout_first_page)
    SmartRefreshLayout mRefreshLayout;

    private int page = 0;
    private FirstPageAdapter mAdapter;
    private List<BannerBean> mBannerBeans;
    private List<ArticleEntity.ArticleBean> mArticleBeans;
    private LinearLayoutManager lm;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first_page;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBannerBeans = new ArrayList<>();
        mArticleBeans = new ArrayList<>();
        lm = new LinearLayoutManager(getContext());
        mAdapter = new FirstPageAdapter(getContext(), mBannerBeans, mArticleBeans);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(lm);
        presenter = new FirstPagePresenter(this);
        presenter.getBanner(this);
        presenter.getArticles(this, page);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mBannerBeans.clear();
            mArticleBeans.clear();
            page = 0;
            presenter.getBanner(FirstPageFragment.this);
            presenter.getArticles(FirstPageFragment.this, page);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            presenter.getArticles(FirstPageFragment.this, page);
        });
    }

    @Override
    public void setBanner(List<BannerBean> bannerBeans) {
        mBannerBeans.addAll(bannerBeans);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh(1000);
    }

    @Override
    public void addArticles(ArticleEntity articleEntity) {
        mArticleBeans.addAll(articleEntity.getDatas());
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.finishLoadMore(1000);
    }

    @Override
    public void addArticlesError() {
        mRefreshLayout.finishLoadMore(false);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void scrollToFirst(){
        int firstCompletelyPosition = lm.findFirstCompletelyVisibleItemPosition();
        if (firstCompletelyPosition != 0){
            mRecyclerView.smoothScrollToPosition(0);
        }
    }
}
