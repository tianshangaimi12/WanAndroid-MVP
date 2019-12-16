package com.aimi.wanandroid_mvp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.adapter.ArticleAdapter;
import com.aimi.wanandroid_mvp.base.RxBaseFragment;
import com.aimi.wanandroid_mvp.contract.SearchContract;
import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.aimi.wanandroid_mvp.presenter.SearchResultPresenter;
import com.aimi.wanandroid_mvp.utils.ConstantUtils;
import com.aimi.wanandroid_mvp.utils.PreferenceUtils;
import com.airbnb.lottie.LottieAnimationView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchResultFragment extends RxBaseFragment<SearchResultPresenter> implements SearchContract.View {
    @BindView(R.id.rv_search_result)
    RecyclerView mRecyclerView;
    @BindView(R.id.smart_refresh_layout_search)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.anim_loading)
    LottieAnimationView mLoading;

    private int page = 0;
    private String key = "";
    private ArticleAdapter mAdapter;
    private List<ArticleEntity.ArticleBean> mArticleBeans = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_result;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mAdapter = new ArticleAdapter(getContext(), mArticleBeans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        presenter = new SearchResultPresenter(this);
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            presenter.getSearchResult(this, key, page);
        });
        Bundle bundle = getArguments();
        if (bundle != null){
            key = bundle.getString(ConstantUtils.EXTRA_SEARCH_WORD, "");
            setSearch(key);
        }
    }

    public void setSearch(String search) {
        if (!presenter.isInSearch) {
            key = search;
            presenter.getSearchResult(this, search, 0);
            PreferenceUtils.saveSearchHistory(search);
            SearchActivity activity = (SearchActivity) getActivity();
            if (activity != null){
                activity.refreshSearchHistory();
            }
        }
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
        if (mRefreshLayout != null) {
            mRefreshLayout.setVisibility(View.VISIBLE);
        }
        if (mLoading != null) {
            mLoading.setVisibility(View.INVISIBLE);
            mLoading.cancelAnimation();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideLoading();
    }

    public void scrollToFirst() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void showSearchResult(ArticleEntity articleEntity) {
        mArticleBeans.addAll(articleEntity.getDatas());
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh(2000);
        mRefreshLayout.finishLoadMore(1000);
    }
}
