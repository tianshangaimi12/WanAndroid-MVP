package com.aimi.wanandroid_mvp.view;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.base.RxBaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends RxBaseActivity {
    @BindView(R.id.drawer_layout_main)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.tool_bar_main)
    Toolbar mToolbar;
    @BindView(R.id.tv_tool_bar_title)
    TextView mTvTitle;

    private FirstPageFragment mFirstPageFragment;
    private KnowledgeFragment mKnowledgeFragment;
    private NavigationFragment mNavigationFragment;
    private ProjectFragment mProjectFragment;
    private FragmentManager fm;
    private int page;

    private static final int PAGE_FIRST_PAGE = 0;
    private static final int PAGE_KNOWLEDGE = 1;
    private static final int PAGE_NAVIGATION = 2;
    private static final int PAGE_PROJECT = 3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolBar() {
        mToolbar.setTitle("");
        mTvTitle.setText(R.string.menu_first_page);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> {
            mDrawerLayout.openDrawer(Gravity.START);
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBottomNavigationView.setLabelVisibilityMode(1);
        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_first_page:
                    mTvTitle.setText(R.string.menu_first_page);
                    showFirstPageFragment();
                    break;
                case R.id.menu_knowledge:
                    mTvTitle.setText(R.string.menu_knowledge);
                    showKnowledgeFragment();
                    break;
                case R.id.menu_navigation:
                    mTvTitle.setText(R.string.menu_navigation);
                    showNavigationFragment();
                    break;
                case R.id.menu_project:
                    mTvTitle.setText(R.string.menu_project);
                    showProjectFragment();
                    break;
            }
            return true;
        });
        fm = getSupportFragmentManager();
        showFirstPageFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_tool_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_useful_sites:
                showToast(String.valueOf(item.getItemId()));
                break;
            case R.id.menu_search:
                showToast(String.valueOf(item.getItemId()));
                break;
        }
        return true;
    }

    public void showFirstPageFragment() {
        page = PAGE_FIRST_PAGE;
        mFirstPageFragment = (FirstPageFragment) fm.findFragmentByTag("FirstPageFragment");
        if (mFirstPageFragment == null) {
            mFirstPageFragment = new FirstPageFragment();
        }
        FragmentTransaction transaction = fm.beginTransaction();
        if (!mFirstPageFragment.isAdded()) {
            transaction.add(R.id.fl_main, mFirstPageFragment, "FirstPageFragment");
        }
        showFragment(transaction, mFirstPageFragment);
    }

    public void showKnowledgeFragment() {
        page = PAGE_KNOWLEDGE;
        mKnowledgeFragment = (KnowledgeFragment) fm.findFragmentByTag("KnowledgeFragment");
        if (mKnowledgeFragment == null) {
            mKnowledgeFragment = new KnowledgeFragment();
        }
        FragmentTransaction transaction = fm.beginTransaction();
        if (!mKnowledgeFragment.isAdded()) {
            transaction.add(R.id.fl_main, mKnowledgeFragment, "KnowledgeFragment");
        }
        showFragment(transaction, mKnowledgeFragment);
    }

    public void showNavigationFragment() {
        page = PAGE_NAVIGATION;
        mNavigationFragment = (NavigationFragment) fm.findFragmentByTag("NavigationFragment");
        if (mNavigationFragment == null) {
            mNavigationFragment = new NavigationFragment();
        }
        FragmentTransaction transaction = fm.beginTransaction();
        if (!mNavigationFragment.isAdded()) {
            transaction.add(R.id.fl_main, mNavigationFragment, "NavigationFragment");
        }
        showFragment(transaction, mNavigationFragment);
    }

    public void showProjectFragment() {
        page = PAGE_PROJECT;
        mProjectFragment = (ProjectFragment) fm.findFragmentByTag("ProjectFragment");
        if (mProjectFragment == null) {
            mProjectFragment = new ProjectFragment();
        }
        FragmentTransaction transaction = fm.beginTransaction();
        if (!mProjectFragment.isAdded()) {
            transaction.add(R.id.fl_main, mProjectFragment, "ProjectFragment");
        }
        showFragment(transaction, mProjectFragment);
    }

    public void showFragment(FragmentTransaction transaction, Fragment fragment) {
        Fragment current = getVisibleFragment();
        if (current != null) {
            if (current != fragment) {
                transaction.hide(current);
                transaction.show(fragment);
                transaction.commit();
            }
        } else {
            transaction.show(fragment);
            transaction.commit();
        }
    }

    public Fragment getVisibleFragment() {
        List<Fragment> fragments = fm.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible()) {
                return fragment;
            }
        }
        return null;
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

    @OnClick(R.id.float_btn_up)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.float_btn_up:
                switch (page){
                    case PAGE_FIRST_PAGE:
                        mFirstPageFragment.scrollToFirst();
                        break;
                    case PAGE_KNOWLEDGE:
                        mKnowledgeFragment.scrollToFirst();;
                        break;
                }
                break;
        }
    }
}
