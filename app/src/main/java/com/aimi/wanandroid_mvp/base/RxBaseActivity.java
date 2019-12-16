package com.aimi.wanandroid_mvp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class RxBaseActivity<T extends IBasePresenter> extends RxAppCompatActivity implements IBaseView {
    private Unbinder binder;
    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        binder = ButterKnife.bind(this);
        initView(savedInstanceState);
        initToolBar();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        if (presenter != null){
            presenter.detachView();
            presenter = null;
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initToolBar();

    protected abstract void initView(Bundle savedInstanceState);
}
