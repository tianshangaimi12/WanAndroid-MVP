package com.aimi.wanandroid_mvp.contract;

import com.aimi.wanandroid_mvp.base.IBasePresenter;
import com.aimi.wanandroid_mvp.base.IBaseView;
import com.aimi.wanandroid_mvp.base.RxBaseActivity;
import com.aimi.wanandroid_mvp.entity.WebsiteEntity;

import java.util.List;

public interface UsefulWebsitesContract {
    interface View extends IBaseView {
        void showWebsites(List<WebsiteEntity> websiteEntities);
    }

    interface Presenter extends IBasePresenter {
        void getData(RxBaseActivity activity);
    }
}
