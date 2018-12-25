package com.aimi.wanandroid_mvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.base.RxBaseActivity;
import com.aimi.wanandroid_mvp.entity.TreeBean;
import com.aimi.wanandroid_mvp.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SystemArticleActivity extends RxBaseActivity{
    @BindView(R.id.tool_bar_system_article)
    Toolbar mToolbar;
    @BindView(R.id.tv_system_title)
    TextView mTvTitle;
    @BindView(R.id.tab_layout_system_article_tag)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager_system_article)
    ViewPager mViewPager;

    private TreeBean mTreeBean;
    private String mTitle;
    private List<String> mTags;
    private List<TreeBean> mTreeBeans;
    private List<SystemArticleFragment> mFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_article;
    }

    @Override
    protected void initToolBar() {
        mToolbar.setTitle("");
        mTvTitle.setText(mTitle);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTags = new ArrayList<>();
        mTreeBeans = new ArrayList<>();
        mFragments = new ArrayList<>();
        mTreeBean = getIntent().getParcelableExtra(ConstantUtils.EXTRA_TREE_BEAN);
        mTitle = mTreeBean.getName();
        mTreeBeans = mTreeBean.getChildren();
        for (TreeBean treeBean : mTreeBeans){
            mTags.add(treeBean.getName());
            Bundle bundle = new Bundle();
            bundle.putInt(ConstantUtils.EXREA_TREE_CID, treeBean.getId());
            SystemArticleFragment systemArticleFragment = new SystemArticleFragment();
            systemArticleFragment.setArguments(bundle);
            mFragments.add(systemArticleFragment);
        }
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTags.get(position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void showToast(int resId) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public static void launch(Context context, TreeBean treeBean){
        Intent intent = new Intent(context, SystemArticleActivity.class);
        intent.putExtra(ConstantUtils.EXTRA_TREE_BEAN, treeBean);
        context.startActivity(intent);
    }
}
