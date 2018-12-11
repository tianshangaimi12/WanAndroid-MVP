package com.aimi.wanandroid_mvp.view;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.base.RxBaseActivity;
import com.aimi.wanandroid_mvp.contract.SplashContract;
import com.aimi.wanandroid_mvp.presenter.SplashPresenter;
import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;

public class SplashActivity extends RxBaseActivity<SplashContract.Presenter> implements SplashContract.View{
    @BindView(R.id.anim_splash)
    LottieAnimationView mAnimationView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new SplashPresenter(this);
        mAnimationView.setAnimation("mailsent.json");
        mAnimationView.playAnimation();
        mAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                presenter.jumpToMain();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAnimationView != null){
            mAnimationView.cancelAnimation();
        }
    }

    @Override
    public void showNext() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
