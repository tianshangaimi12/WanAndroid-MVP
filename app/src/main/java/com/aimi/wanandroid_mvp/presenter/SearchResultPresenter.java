package com.aimi.wanandroid_mvp.presenter;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.base.RxBaseFragment;
import com.aimi.wanandroid_mvp.contract.SearchContract;
import com.aimi.wanandroid_mvp.utils.RetrofitUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchResultPresenter implements SearchContract.Presenter {
    private SearchContract.View mView;
    public boolean isInSearch;

    public SearchResultPresenter(SearchContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void getSearchResult(RxBaseFragment fragment, String key, int page) {
        isInSearch = true;
        if (page == 0){
            mView.showLoading();
        }
        RetrofitUtils.getFirstPageApi()
                .search(key, page)
                .compose(fragment.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    isInSearch = false;
                    mView.hideLoading();
                    if (result.getErrorCode() == 0) {
                        mView.showSearchResult(result.getData());
                    } else {
                        mView.showToast(result.getErrorMsg());
                    }
                }, throwable -> {
                    isInSearch = false;
                    mView.hideLoading();
                    mView.showToast(R.string.load_failed);
                });
    }
}
