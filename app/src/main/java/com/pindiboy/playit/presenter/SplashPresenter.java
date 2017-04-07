package com.pindiboy.playit.presenter;

import android.Manifest;

import com.pindiboy.playit.common.RxUtil;
import com.pindiboy.playit.model.http.ApiService;
import com.pindiboy.playit.presenter.contract.SplashContract;
import com.pindiboy.playit.util.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Jiangwenjin on 2017/3/24.
 */

public class SplashPresenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter {
    @Inject
    public SplashPresenter(ApiService apiService) {
        this.mApiService = apiService;
    }

    @Override
    public void getIpInfo() {
        addSubscribe(mApiService.fetchIpInfo()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(ipInfo -> {
                            mView.onIpInfoLoaded(ipInfo);
                            startCountDown();
                        },
                        throwable -> {
                            Logger.e("", throwable);
                            startCountDown();
                        }));
    }

    @Override
    public void checkPermissions(RxPermissions rxPermissions) {
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                });
    }

    /**
     * Go to MainActivity after 2s
     */
    private void startCountDown() {
        addSubscribe(Observable.timer(1500L, TimeUnit.MILLISECONDS)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(aLong -> mView.onCountDownEnd()));
    }
}
