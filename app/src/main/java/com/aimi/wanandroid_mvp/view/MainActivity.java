package com.aimi.wanandroid_mvp.view;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.base.RxBaseActivity;

import butterknife.BindView;

public class MainActivity extends RxBaseActivity {
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;

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
    }

}
