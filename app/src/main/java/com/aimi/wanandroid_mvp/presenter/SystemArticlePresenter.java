package com.aimi.wanandroid_mvp.presenter;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.contract.SystemArticleContract;
import com.aimi.wanandroid_mvp.utils.RetrofitUtils;
import com.trello.rxlifecycle.components.support.RxFragment;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SystemArticlePresenter implements SystemArticleContract.Presenter{
    private SystemArticleContract.View view;

    public SystemArticlePresenter(SystemArticleContract.View view){
        this.view = view;
        view.showLoading();
    }

    @Override
    public void getArticles(RxFragment fragment, int page, int cid) {
        RetrofitUtils.getTreeApi()
                .getTreeNodeArticle(page, cid)
                .compose(fragment.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articleEntityBaseEntity -> {
                    view.hideLoading();
                    if (articleEntityBaseEntity.getErrorCode() == 0){
                        view.addArticles(articleEntityBaseEntity.getData());
                    } else {
                        view.addArticlesError();
                        view.showToast(articleEntityBaseEntity.getErrorMsg());
                    }
                }, throwable -> {
                    view.hideLoading();
                    view.addArticlesError();
                    view.showToast(R.string.load_failed);
                });
    }

    @Override
    public void detachView() {
        view = null;
    }
}
