package com.pindiboy.playit.presenter.contract;

import com.pindiboy.playit.model.bean.IpInfo;
import com.pindiboy.playit.presenter.BasePresenter;
import com.pindiboy.playit.ui.BaseView;
import com.tbruyelle.rxpermissions.RxPermissions;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */

public interface SplashContract {
    interface View extends BaseView {
        void onIpInfoLoaded(IpInfo ipInfo);

        void onCountDownEnd();
    }

    interface Presenter extends BasePresenter<View> {
        void getIpInfo();

        void checkPermissions(RxPermissions rxPermissions);
    }
}
