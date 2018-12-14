package com.aimi.wanandroid_mvp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class RxBaseFragment<T extends IBasePresenter> extends RxFragment {
    private Unbinder binder;
    public T presenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
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
