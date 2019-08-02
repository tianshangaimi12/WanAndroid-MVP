package com.aimi.wanandroid_mvp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.adapter.ProjectTitleAdapter;
import com.aimi.wanandroid_mvp.base.RxBaseFragment;
import com.aimi.wanandroid_mvp.contract.ProjectContract;
import com.aimi.wanandroid_mvp.entity.ProjectEntity;
import com.aimi.wanandroid_mvp.presenter.ProjectFragmentPresenter;
import com.aimi.wanandroid_mvp.utils.ConstantUtils;
import com.aimi.wanandroid_mvp.widget.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectFragment extends RxBaseFragment<ProjectFragmentPresenter> implements ProjectContract.View {
    @BindView(R.id.rv_project_title)
    RecyclerView mRvProjectTitle;
    @BindView(R.id.view_pager_project_pages)
    ViewPager mViewPager;

    private LinearLayoutManager mLayoutManager;
    private ProjectTitleAdapter mProjectTitleAdapter;
    private List<ProjectEntity> mProjectEntities;
    private List<ProjectPageFragment> mFragments;

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

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mProjectEntities = new ArrayList<>();
        mProjectTitleAdapter = new ProjectTitleAdapter(getActivity(), mProjectEntities);
        mRvProjectTitle.setLayoutManager(mLayoutManager);
        mRvProjectTitle.setAdapter(mProjectTitleAdapter);
        RecyclerViewDivider titleDivider = new RecyclerViewDivider(getActivity(), LinearLayoutManager.VERTICAL, 4,
                getResources().getColor(R.color.colorOrange));
        mRvProjectTitle.addItemDecoration(titleDivider);
        mProjectTitleAdapter.setOnItemClickListener((view, position) -> mViewPager.setCurrentItem(position));
    }

    @Override
    public void setTitle(List<ProjectEntity> projectEntities) {
        if (projectEntities.size() == 0){
            return;
        }
        projectEntities.get(0).setSelect(true);
        mProjectEntities.addAll(projectEntities);
        mProjectTitleAdapter.notifyDataSetChanged();
        mFragments = new ArrayList<>();
        for (ProjectEntity projectEntity : projectEntities) {
            ProjectPageFragment projectPageFragment = new ProjectPageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ConstantUtils.EXTRA_PROJECT_CID, projectEntity.getId());
            projectPageFragment.setArguments(bundle);
            mFragments.add(projectPageFragment);
        }
        mViewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int page) {
                for (int i = 0;i<projectEntities.size();i++){
                    if (i == page){
                        projectEntities.get(i).setSelect(true);
                    } else {
                        projectEntities.get(i).setSelect(false);
                    }
                }
                mProjectTitleAdapter.notifyDataSetChanged();
                mRvProjectTitle.smoothScrollToPosition(page);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    public void scrollToFirst(){
        int current = mViewPager.getCurrentItem();
        ProjectPageFragment projectPageFragment = mFragments.get(current);
        projectPageFragment.scrollToFirst();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
