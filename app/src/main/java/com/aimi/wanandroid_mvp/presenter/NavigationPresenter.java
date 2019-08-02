package com.aimi.wanandroid_mvp.presenter;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.contract.NavigationContract;
import com.aimi.wanandroid_mvp.utils.RetrofitUtils;
import com.trello.rxlifecycle.components.support.RxFragment;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NavigationPresenter implements NavigationContract.Presenter {

    private NavigationContract.View view;

    public NavigationPresenter(NavigationContract.View view) {
        this.view = view;
    }

    @Override
    public void getNavigationData(RxFragment rxFragment) {
        RetrofitUtils.getNavigationApi()
                .getNavigationData()
                .compose(rxFragment.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
