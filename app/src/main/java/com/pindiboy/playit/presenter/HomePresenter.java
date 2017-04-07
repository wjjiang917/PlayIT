package com.pindiboy.playit.presenter;

import com.pindiboy.playit.common.RxUtil;
import com.pindiboy.playit.model.bean.youtube.Item;
import com.pindiboy.playit.model.bean.youtube.ItemId;
import com.pindiboy.playit.model.bean.youtube.Snippet;
import com.pindiboy.playit.model.bean.youtube.Thumbnail;
import com.pindiboy.playit.model.db.RealmHelper;
import com.pindiboy.playit.model.http.ApiService;
import com.pindiboy.playit.presenter.contract.HomeContract;
import com.pindiboy.playit.util.Logger;

import javax.inject.Inject;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter {
    @Inject
    public HomePresenter(ApiService apiService, RealmHelper realmHelper) {
        mApiService = apiService;
        mRealmHelper = realmHelper;
    }

    @Override
    public void getTrending() {
        addSubscribe(mApiService.fetchTrending(10, "")
                .compose(RxUtil.rxSchedulerHelper())
                .map(youTubeBean -> {
                    Item<String> item = youTubeBean.getItems().get(0);
                    Snippet snippet = item.getSnippet();
                    snippet.setVideoId(item.getId());
                    snippet.setThumbnail(item.getSnippet().getThumbnails().get(Thumbnail.TYPE_HIGH).getUrl());
                    snippet.setFavourite(mRealmHelper.queryFavourite(item.getId()));
                    return youTubeBean;
                })
                .subscribe(youTubeBean -> mView.onTrendingLoaded(youTubeBean),
                        throwable -> Logger.e("", throwable)));
    }

    @Override
    public void getChannelVideos(String channelId) {
        addSubscribe(mApiService.fetchChannelVideos(channelId, "")
                .compose(RxUtil.rxSchedulerHelper())
                .map(youTubeBean -> {
                    for (Item<ItemId> item : youTubeBean.getItems()) {
                        Snippet snippet = item.getSnippet();
                        snippet.setVideoId(item.getId().getVideoId());
                        snippet.setThumbnail(item.getSnippet().getThumbnails().get(Thumbnail.TYPE_HIGH).getUrl());
                        snippet.setFavourite(mRealmHelper.queryFavourite(item.getId().getVideoId()));
                    }
                    return youTubeBean;
                })
                .subscribe(youTubeBean -> mView.onChannelVideosLoaded(channelId, youTubeBean),
                        throwable -> Logger.e("", throwable)));
    }
}
