package com.aimi.wanandroid_mvp.presenter;

import com.aimi.wanandroid_mvp.R;
import com.aimi.wanandroid_mvp.base.RxBaseFragment;
import com.aimi.wanandroid_mvp.contract.ProjectPageContract;
import com.aimi.wanandroid_mvp.utils.RetrofitUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProjectPagePresenter implements ProjectPageContract.Presenter {
    private ProjectPageContract.View mView;

    public ProjectPagePresenter(ProjectPageContract.View view) {
        mView = view;
        mView.showLoading();
    }

    @Override
    public void getProjects(RxBaseFragment fragment, int page, int cid) {
        RetrofitUtils.getProjectApi()
                .getProjectItems(page, cid)
                .compose(fragment.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entity -> {
                    mView.hideLoading();
                    if (entity.getErrorCode() == 0) {
                        mView.addProjects(entity.getData().getDatas());
                    } else {
                        mView.showToast(entity.getErrorMsg());
                    }
                }, throwable -> {
                    mView.showToast(R.string.load_failed);
                    mView.hideLoading();
                });
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
