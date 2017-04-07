package com.pindiboy.playit.presenter.contract;

import com.pindiboy.playit.model.bean.youtube.ItemId;
import com.pindiboy.playit.model.bean.youtube.Snippet;
import com.pindiboy.playit.model.bean.youtube.YouTubeBean;
import com.pindiboy.playit.presenter.BasePresenter;
import com.pindiboy.playit.ui.BaseView;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public interface SearchContract {
    interface View extends BaseView {
        void onSearchLoaded(YouTubeBean<ItemId> youTubeBean);
    }

    interface Presenter extends BasePresenter<View> {
        void search(String q, String pageToken);

        void addFavorite(Snippet video);

        void removeFavorite(String videoId);
    }
}
