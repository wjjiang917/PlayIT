package com.pindiboy.playit.presenter;

import com.pindiboy.playit.model.bean.youtube.Snippet;
import com.pindiboy.playit.model.db.RealmHelper;
import com.pindiboy.playit.presenter.contract.FavoriteContract;

import javax.inject.Inject;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public class FavoritePresenter extends RxPresenter<FavoriteContract.View> implements FavoriteContract.Presenter {
    @Inject
    public FavoritePresenter(RealmHelper realmHelper) {
        mRealmHelper = realmHelper;
    }

    @Override
    public void getFavorite() {
        mView.onFavoriteLoaded(mRealmHelper.queryFavourite());
    }

    @Override
    public void addFavorite(Snippet video) {
        mRealmHelper.insertFavourite(video);
    }

    @Override
    public void removeFavorite(String videoId) {
        mRealmHelper.deleteFavourite(videoId);
    }

    @Override
    public void updateFavorite(String videoId, double order) {
        mRealmHelper.updateFavourite(videoId, order);
    }
}
