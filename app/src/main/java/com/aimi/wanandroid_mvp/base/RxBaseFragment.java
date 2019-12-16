package com.aimi.wanandroid_mvp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class RxBaseFragment<T extends IBasePresenter> extends RxFragment {
    private Unbinder binder;
    public T presenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binder = ButterKnife.bind(this, view);
        initView(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binder.unbind();
        if (presenter != null){
            presenter.detachView();
            presenter = null;
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView(Bundle savedInstanceState);
}
