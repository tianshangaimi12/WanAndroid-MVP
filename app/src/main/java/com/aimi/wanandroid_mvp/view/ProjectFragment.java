package com.aimi.wanandroid_mvp.view;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.adapter.ProjectTitleAdapter;
import com.aimi.wanandroid_mvp.base.RxBaseFragment;
import com.aimi.wanandroid_mvp.contract.ProjectContract;
import com.aimi.wanandroid_mvp.entity.ProjectEntity;
import com.aimi.wanandroid_mvp.presenter.ProjectFragmentPresenter;
import com.aimi.wanandroid_mvp.widget.RecyclerViewDivider;
import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectFragment extends RxBaseFragment<ProjectFragmentPresenter> implements ProjectContract.View {
    @BindView(R.id.rv_project_title)
    RecyclerView mRvProjectTitle;
    @BindView(R.id.rv_project_detail)
    RecyclerView mRvProjectDetail;
    @BindView(R.id.anim_loading)
    LottieAnimationView mLoading;

    private ProjectTitleAdapter mProjectTitleAdapter;
    private List<ProjectEntity> mProjectEntities;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
       initRecyclerView();
        presenter = new ProjectFragmentPresenter(this);
        presenter.getTitle(this);
    }

    private void initRecyclerView(){
        LinearLayoutManager lmHorizontal = new LinearLayoutManager(getActivity());
        lmHorizontal.setOrientation(LinearLayoutManager.HORIZONTAL);
        mProjectEntities = new ArrayList<>();
        mProjectTitleAdapter = new ProjectTitleAdapter(getActivity(), mProjectEntities);
        mRvProjectTitle.setLayoutManager(lmHorizontal);
        mRvProjectTitle.setAdapter(mProjectTitleAdapter);
        RecyclerViewDivider titleDivider = new RecyclerViewDivider(getActivity(), LinearLayoutManager.VERTICAL, 4,
                getResources().getColor(R.color.colorOrange));
        mRvProjectTitle.addItemDecoration(titleDivider);
    }

    @Override
    public void setTitle(List<ProjectEntity> projectEntities) {
        mProjectEntities.addAll(projectEntities);
        mProjectTitleAdapter.notifyDataSetChanged();
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
        if (mLoading != null) {
            mLoading.setVisibility(View.INVISIBLE);
            mLoading.cancelAnimation();
        }
    }
}
