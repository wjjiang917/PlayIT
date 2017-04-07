package com.pindiboy.playit.presenter;

import com.pindiboy.playit.common.RxUtil;
import com.pindiboy.playit.model.bean.youtube.Item;
import com.pindiboy.playit.model.bean.youtube.ItemId;
import com.pindiboy.playit.model.bean.youtube.Snippet;
import com.pindiboy.playit.model.bean.youtube.Thumbnail;
import com.pindiboy.playit.model.db.RealmHelper;
import com.pindiboy.playit.model.http.ApiService;
import com.pindiboy.playit.presenter.contract.ChannelContract;
import com.pindiboy.playit.util.Logger;

import javax.inject.Inject;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public class ChannelPresenter extends RxPresenter<ChannelContract.View> implements ChannelContract.Presenter {
    @Inject
    public ChannelPresenter(ApiService apiService, RealmHelper realmHelper) {
        mApiService = apiService;
        mRealmHelper = realmHelper;
    }

    @Override
    public void getChannelVideos(String channelId, String pageToken) {
        addSubscribe(mApiService.fetchChannelVideos(channelId, pageToken)
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
                .subscribe(youTubeBean -> mView.onChannelVideosLoaded(youTubeBean),
                        throwable -> Logger.e("", throwable)));
    }

    @Override
    public void addFavorite(Snippet video) {
        mRealmHelper.insertFavourite(video);
    }

    @Override
    public void removeFavorite(String videoId) {
        mRealmHelper.deleteFavourite(videoId);
    }
}
