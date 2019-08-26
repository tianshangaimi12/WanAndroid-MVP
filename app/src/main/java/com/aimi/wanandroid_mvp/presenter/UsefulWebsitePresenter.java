package com.aimi.wanandroid_mvp.presenter;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.base.RxBaseActivity;
import com.aimi.wanandroid_mvp.contract.UsefulWebsitesContract;
import com.aimi.wanandroid_mvp.utils.RetrofitUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UsefulWebsitePresenter implements UsefulWebsitesContract.Presenter {
    private UsefulWebsitesContract.View mView;

    public UsefulWebsitePresenter(UsefulWebsitesContract.View view) {
        mView = view;
    }

    @Override
    public void getData(RxBaseActivity activity) {
        RetrofitUtils.getFirstPageApi()
                .getWebsites()
                .compose(activity.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    mView.hideLoading();
                    if (result.getErrorCode() == 0) {
                        mView.showWebsites(result.getData());
                    } else {
                        mView.showToast(result.getErrorMsg());
                    }
                }, throwable -> {
                    mView.hideLoading();
                    mView.showToast(R.string.load_failed);});
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
