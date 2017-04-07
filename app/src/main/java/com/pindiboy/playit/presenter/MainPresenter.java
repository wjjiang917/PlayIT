package com.pindiboy.playit.presenter;

import com.pindiboy.playit.model.http.ApiService;
import com.pindiboy.playit.presenter.contract.MainContract;

import javax.inject.Inject;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    @Inject
    public MainPresenter(ApiService apiService) {
        mApiService = apiService;
    }
}
