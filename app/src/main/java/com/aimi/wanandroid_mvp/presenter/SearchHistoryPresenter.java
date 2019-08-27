package com.aimi.wanandroid_mvp.presenter;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.base.RxBaseFragment;
import com.aimi.wanandroid_mvp.contract.SearchHistoryContract;
import com.aimi.wanandroid_mvp.entity.WebsiteEntity;
import com.aimi.wanandroid_mvp.utils.PreferenceUtils;
import com.aimi.wanandroid_mvp.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchHistoryPresenter implements SearchHistoryContract.Presenter {
    private SearchHistoryContract.View mView;

    public SearchHistoryPresenter(SearchHistoryContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void getSearchHistory() {
        mView.showSearchHistory(PreferenceUtils.getSearchHistory());
    }

    @Override
    public void getHotSearch(RxBaseFragment fragment) {
        RetrofitUtils.getFirstPageApi()
                .getHotKeys()
                .compose(fragment.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    mView.hideLoading();
                    if (result.getErrorCode() == 0){
                        List<WebsiteEntity> entities = result.getData();
                        List<String> hotSearch = new ArrayList<>();
                        for (WebsiteEntity websiteEntity : entities){
                            hotSearch.add(websiteEntity.getName());
                        }
                        mView.showHotSearch(hotSearch);
                    } else {
                        mView.showToast(result.getErrorMsg());
                    }
                }, throwable -> {
                    mView.hideLoading();
                    mView.showToast(R.string.load_failed);
                });
    }
}
