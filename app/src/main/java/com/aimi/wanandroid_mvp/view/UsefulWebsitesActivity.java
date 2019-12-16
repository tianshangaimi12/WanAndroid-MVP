package com.aimi.wanandroid_mvp.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.base.RxBaseActivity;
import com.aimi.wanandroid_mvp.contract.UsefulWebsitesContract;
import com.aimi.wanandroid_mvp.entity.WebsiteEntity;
import com.aimi.wanandroid_mvp.presenter.UsefulWebsitePresenter;
import com.aimi.wanandroid_mvp.utils.ConstantUtils;
import com.aimi.wanandroid_mvp.utils.DisplayUtils;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.internal.FlowLayout;

import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class UsefulWebsitesActivity extends RxBaseActivity<UsefulWebsitePresenter> implements UsefulWebsitesContract.View {
    @BindView(R.id.tool_bar_useful_websites)
    Toolbar mToolbar;
    @BindView(R.id.flow_layout_useful_website)
    FlowLayout mFlowLayout;
    @BindView(R.id.anim_loading)
    LottieAnimationView mLoading;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_useful_websites;
    }

    @Override
    protected void initToolBar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new UsefulWebsitePresenter(this);
        presenter.getData(this);
        showLoading();
    }

    @Override
    public void showWebsites(List<WebsiteEntity> websiteEntities) {
        FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT,
                FlowLayout.LayoutParams.WRAP_CONTENT);
        for (WebsiteEntity websiteEntity : websiteEntities) {
            TextView tv = new TextView(this);
            tv.setText(websiteEntity.getName());
            tv.setTextSize(16);
            tv.setTextColor(Color.WHITE);
            tv.setPadding(DisplayUtils.dp2Px(this, 8), DisplayUtils.dp2Px(this, 2), DisplayUtils.dp2Px(this, 8),
                    DisplayUtils.dp2Px(this, 2));
            int randomNum = new Random().nextInt(7);
            tv.setBackgroundColor(getResources().getColor(ConstantUtils.TREE_NODE_TITLE_COLORS.get(randomNum)));
            tv.setOnClickListener(v -> WebActivity.launch(UsefulWebsitesActivity.this, websiteEntity));
            mFlowLayout.addView(tv, lp);
        }
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
