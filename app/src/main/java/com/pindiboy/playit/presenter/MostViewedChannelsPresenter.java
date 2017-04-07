package com.pindiboy.playit.presenter;

import com.pindiboy.playit.common.RxUtil;
import com.pindiboy.playit.model.http.ApiService;
import com.pindiboy.playit.presenter.contract.MostViewedChannelsContract;
import com.pindiboy.playit.util.Logger;

import javax.inject.Inject;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public class MostViewedChannelsPresenter extends RxPresenter<MostViewedChannelsContract.View> implements MostViewedChannelsContract.Presenter {
    @Inject
    public MostViewedChannelsPresenter(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public void getMostViewedChannels(String pageToken) {
        addSubscribe(mApiService.fetchMostViewsChannels(pageToken)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(channels -> mView.onChannelsLoaded(channels),
                        throwable -> Logger.e("", throwable)));
    }
}
