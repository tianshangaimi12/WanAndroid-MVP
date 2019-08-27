package com.aimi.wanandroid_mvp.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.base.RxBaseActivity;
import com.aimi.wanandroid_mvp.utils.ConstantUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends RxBaseActivity {
    @BindView(R.id.search_view)
    SearchView mSearchView;

    private FragmentManager mManager;
    private SearchHistoryFragment mSearchHistoryFragment;
    private SearchResultFragment mSearchResultFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorSplash));
        initSearchView();
        mManager = getSupportFragmentManager();
        showSearchHistory();
    }

    private void initSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    showSearchResult(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void showSearchHistory() {
        FragmentTransaction transaction = mManager.beginTransaction();
        if (mSearchHistoryFragment == null) {
            mSearchHistoryFragment = new SearchHistoryFragment();
            mSearchHistoryFragment.setOnItemClickListener(name -> mSearchView.setQuery(name, true));
            transaction.add(R.id.fl_search_main, mSearchHistoryFragment);
            transaction.show(mSearchHistoryFragment);
        } else {
            transaction.show(mSearchHistoryFragment);
        }
        transaction.commit();
    }

    public void showSearchResult(String query) {
        FragmentTransaction transaction = mManager.beginTransaction();
        if (mSearchResultFragment == null) {
            mSearchResultFragment = new SearchResultFragment();
            Bundle bundle = new Bundle();
            bundle.putString(ConstantUtils.EXTRA_SEARCH_WORD, query);
            mSearchResultFragment.setArguments(bundle);
            transaction.add(R.id.fl_search_main, mSearchResultFragment);
            transaction.show(mSearchResultFragment);
        } else {
            mSearchResultFragment.setSearch(query);
            transaction.show(mSearchResultFragment);
        }
        transaction.commit();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
    }

    @OnClick({R.id.img_back, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_search:
                String text = mSearchView.getQuery().toString();
                mSearchView.setQuery(text, true);
                break;
        }
    }

    public void refreshSearchHistory(){
        mSearchHistoryFragment.refreshHistory();
    }
}
