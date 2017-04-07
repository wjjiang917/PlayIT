package com.pindiboy.playit.presenter.contract;

import com.pindiboy.playit.model.bean.youtube.Snippet;
import com.pindiboy.playit.presenter.BasePresenter;
import com.pindiboy.playit.ui.BaseView;

import java.util.List;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public interface FavoriteContract {
    interface View extends BaseView {
        void onFavoriteLoaded(List<Snippet> videos);
    }

    interface Presenter extends BasePresenter<View> {
        void getFavorite();

        void addFavorite(Snippet video);

        void removeFavorite(String videoId);

        void updateFavorite(String videoId, double order);
    }
}
