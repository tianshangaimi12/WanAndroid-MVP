package com.aimi.wanandroid_mvp.presenter;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.contract.ProjectContract;
import com.aimi.wanandroid_mvp.utils.RetrofitUtils;
import com.trello.rxlifecycle.components.support.RxFragment;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProjectFragmentPresenter implements ProjectContract.Presenter {
    private ProjectContract.View view;

    public ProjectFragmentPresenter(ProjectContract.View view) {
        this.view = view;
    }

    @Override
    public void getTitle(RxFragment fragment) {
        RetrofitUtils.getProjectApi()
                .getProjectTitle()
                .compose(fragment.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> view.showLoading())
                .doOnUnsubscribe(() -> view.hideLoading())
                .subscribe(listBaseEntity -> {
                    if (listBaseEntity.getErrorCode() == 0) {
                        view.setTitle(listBaseEntity.getData());
                    } else {
                        view.showToast(listBaseEntity.getErrorMsg());
                    }
                }, throwable -> view.showToast(R.string.load_failed));
    }

    @Override
    public void detachView() {
        view = null;
    }

}
