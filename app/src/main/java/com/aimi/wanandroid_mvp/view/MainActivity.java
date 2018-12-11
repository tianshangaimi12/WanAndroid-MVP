package com.aimi.wanandroid_mvp.view;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.base.RxBaseActivity;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends RxBaseActivity {
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;

    private FirstPageFragment mFirstPageFragment;
    private KnowledgeFragment mKnowledgeFragment;
    private NavigationFragment mNavigationFragment;
    private ProjectFragment mProjectFragment;
    private FragmentManager fm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBottomNavigationView.setLabelVisibilityMode(1);
        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_first_page:
                    showFirstPageFragment();
                    break;
                case R.id.menu_knowledge:
                    showKnowledgeFragment();
                    break;
                case R.id.menu_navigation:
                    showNavigationFragment();
                    break;
                case R.id.menu_project:
                    showProjectFragment();
                    break;
            }
            return true;
        });
        fm = getSupportFragmentManager();
        showFirstPageFragment();
    }

    public void showFirstPageFragment() {
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

}
