package com.pindiboy.playit.presenter.contract;

import com.pindiboy.playit.model.bean.youtube.ItemId;
import com.pindiboy.playit.model.bean.youtube.Snippet;
import com.pindiboy.playit.model.bean.youtube.YouTubeBean;
import com.pindiboy.playit.presenter.BasePresenter;
import com.pindiboy.playit.ui.BaseView;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public interface HomeContract {
    interface View extends BaseView {
        void onTrendingLoaded(YouTubeBean<String> trendings);

        void onChannelVideosLoaded(String channelId, YouTubeBean<ItemId> videos);
    }

    interface Presenter extends BasePresenter<View> {
        void getTrending();

        void getChannelVideos(String channelId);

        void addFavorite(Snippet video);

        void removeFavorite(String videoId);
    }
}
