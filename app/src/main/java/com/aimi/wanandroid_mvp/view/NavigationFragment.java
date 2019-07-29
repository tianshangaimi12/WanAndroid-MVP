package com.aimi.wanandroid_mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.adapter.NaviDetailAdapter;
import com.aimi.wanandroid_mvp.adapter.NaviTitleAdapter;
import com.aimi.wanandroid_mvp.base.RxBaseFragment;
import com.aimi.wanandroid_mvp.contract.NavigationContract;
import com.aimi.wanandroid_mvp.entity.ArticleEntity;
import com.aimi.wanandroid_mvp.presenter.NavigationPresenter;
import com.aimi.wanandroid_mvp.widget.TopSmoothScroller;
import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class NavigationFragment extends RxBaseFragment<NavigationPresenter> implements NavigationContract.View {
    @BindView(R.id.rv_navigation_title)
    RecyclerView mRecyclerViewTitle;
    @BindView(R.id.rv_navigation_detail)
    RecyclerView mRecyclerViewDetail;
    @BindView(R.id.anim_loading)
    LottieAnimationView mLoading;

    private NaviTitleAdapter mTitleAdapter;
    private NaviDetailAdapter mDetailAdapter;
    private List<ArticleEntity> mArticleEntities;
    private LinearLayoutManager mTitleManager;
    private LinearLayoutManager mDetailManager;
    private TopSmoothScroller mTopSmoothScroller;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mArticleEntities = new ArrayList<>();
        mTopSmoothScroller = new TopSmoothScroller(getActivity());
        mTitleAdapter = new NaviTitleAdapter(getActivity(), mArticleEntities);
        mDetailAdapter = new NaviDetailAdapter(getActivity(), mArticleEntities);
        initRecyclerView();
        presenter = new NavigationPresenter(this);
        presenter.getNavigationData(this);
    }

    private void initRecyclerView() {
        mTitleManager = new LinearLayoutManager(getActivity());
        mDetailManager = new LinearLayoutManager(getActivity());
        mRecyclerViewTitle.setLayoutManager(mTitleManager);
        mRecyclerViewDetail.setLayoutManager(mDetailManager);
        mRecyclerViewTitle.setAdapter(mTitleAdapter);
        mRecyclerViewDetail.setAdapter(mDetailAdapter);
        mTitleAdapter.setOnItemClickListener(position -> {
            mTopSmoothScroller.setTargetPosition(position);
            mDetailManager.startSmoothScroll(mTopSmoothScroller);
            setSelect(position);
        });
        mRecyclerViewDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int prePosition = 0;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE) {
                    int height = mRecyclerViewTitle.getChildAt(0).getHeight();
                    int position = mDetailManager.findFirstVisibleItemPosition();
                    if (position >= prePosition) {
                        mRecyclerViewTitle.smoothScrollToPosition(position + 5);
                    } else {
                        mRecyclerViewTitle.smoothScrollBy(0, (position - prePosition) * height);
                    }
                    prePosition = position;
                    setSelect(position);
                }
            }
        });
    }

    @Override
    public void setView(List<ArticleEntity> articleEntities) {
        mArticleEntities.addAll(articleEntities);
        if (mArticleEntities.size() > 0) {
            mArticleEntities.get(0).setSelect(true);
        }
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
        mLoading.setVisibility(View.VISIBLE);
        mLoading.setAnimation("stopwatch.json");
        mLoading.playAnimation();
    }

    @Override
    public void hideLoading() {
        if (mLoading != null){
            mLoading.setVisibility(View.INVISIBLE);
            mLoading.cancelAnimation();
        }
    }

    private void setSelect(int position) {
        for (int i = 0; i < mArticleEntities.size(); i++) {
            ArticleEntity entity = mArticleEntities.get(i);
            if (i == position) {
                entity.setSelect(true);
            } else {
                entity.setSelect(false);
            }
        }
        mTitleAdapter.notifyDataSetChanged();
    }

    public void scrollToFirst() {
        int titleFirst = mTitleManager.findFirstCompletelyVisibleItemPosition();
        int detailFirst = mDetailManager.findFirstCompletelyVisibleItemPosition();
        if (titleFirst != 0) {
            mRecyclerViewTitle.smoothScrollToPosition(0);
            setSelect(0);
        }
        if (detailFirst != 0) {
            mRecyclerViewDetail.smoothScrollToPosition(0);
        }
    }
}
