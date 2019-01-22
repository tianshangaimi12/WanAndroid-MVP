package com.aimi.wanandroid_mvp.presenter;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.contract.KnowledgeContract;
import com.aimi.wanandroid_mvp.utils.RetrofitUtils;
import com.trello.rxlifecycle.components.support.RxFragment;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class KnowledgePresenter implements KnowledgeContract.Presenter {
    private KnowledgeContract.View view;

    public KnowledgePresenter(KnowledgeContract.View view) {
        this.view = view;
    }

    @Override
    public void getKnowledgeTree(RxFragment rxFragment) {
        RetrofitUtils.getTreeApi()
                .getSystemTree()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxFragment.bindToLifecycle())
                .doOnSubscribe(() -> view.showLoading())
                .subscribe(listBaseEntity -> {
                    view.hideLoading();
                    if (listBaseEntity.getErrorCode() == 0) {
                        view.setView(listBaseEntity.getData());
                    } else {
                        view.showToast(listBaseEntity.getErrorMsg());
                    }
                }, throwable -> {
                    view.hideLoading();
                    view.showToast(R.string.load_failed);
                });
    }

    @Override
    public void detachView() {
        view = null;
    }
}
