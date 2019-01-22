package com.aimi.wanandroid_mvp.presenter;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.contract.FirstPageContract;
import com.aimi.wanandroid_mvp.utils.RetrofitUtils;
import com.trello.rxlifecycle.components.support.RxFragment;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FirstPagePresenter implements FirstPageContract.Presenter {
    private FirstPageContract.View view;

    public FirstPagePresenter(FirstPageContract.View view) {
        this.view = view;
    }

    @Override
    public void getBanner(RxFragment fragment) {
        RetrofitUtils.getFirstPageApi()
                .getBanners()
                .compose(fragment.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> view.showLoading())
                .subscribe(bannerEntityBaseEntity -> {
                    view.hideLoading();
                    if (bannerEntityBaseEntity.getErrorCode() == 0) {
                        view.setBanner(bannerEntityBaseEntity.getData());
                    } else {
                        view.showToast(bannerEntityBaseEntity.getErrorMsg());
                    }
                }, throwable -> {
                    view.hideLoading();
                    view.showToast(R.string.load_failed);
                });
    }

    @Override
    public void getArticles(RxFragment fragment, int page) {
        RetrofitUtils.getFirstPageApi()
                .getArticles(page)
                .compose(fragment.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articleEntityBaseEntity -> {
                    if (articleEntityBaseEntity.getErrorCode() == 0) {
                        view.addArticles(articleEntityBaseEntity.getData());
                    } else {
                        view.showToast(articleEntityBaseEntity.getErrorMsg());
                        view.addArticlesError();
                    }
                }, throwable -> {
                    view.showToast(throwable.getMessage());
                    view.addArticlesError();
                });
    }

    @Override
    public void detachView() {
        view = null;
    }
}
