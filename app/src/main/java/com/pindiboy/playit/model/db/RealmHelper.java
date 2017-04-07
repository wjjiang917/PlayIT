package com.pindiboy.playit.model.db;

import android.content.Context;

import com.pindiboy.playit.common.Constant;
import com.pindiboy.playit.model.bean.youtube.Snippet;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Jiangwenjin on 2017/3/21.
 */

public class RealmHelper {
    private static final String DB_NAME = "myRealm.realm";

    private Realm mRealm;

    public RealmHelper(Context mContext) {
        Realm.init(mContext);
        mRealm = Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name(DB_NAME)
                .build());
    }

    public void close() {
        if (null != mRealm) {
            mRealm.close();
        }
    }

    public void insertFavourite(Snippet video) {
        mRealm.beginTransaction();
        RealmResults<Snippet> results = mRealm.where(Snippet.class).findAllSorted("order", Sort.DESCENDING);
        double order = 1.0;
        if (null != results && !results.isEmpty()) {
            order += results.get(0).getOrder();
        }

        // if length out of limit, remove the last one
        if (null != results && results.size() == Constant.MAX_FAVORITE) {
            results.get(results.size() - 1).deleteFromRealm();
        }
        video.setOrder(order);
        mRealm.copyToRealmOrUpdate(video);
        mRealm.commitTransaction();
    }

    public void deleteFavourite(String videoId) {
        Snippet video = mRealm.where(Snippet.class).equalTo("videoId", videoId).findFirst();
        mRealm.beginTransaction();
        if (video != null) {
            video.deleteFromRealm();
        }
        mRealm.commitTransaction();
    }

    public boolean queryFavourite(String videoId) {
        RealmResults<Snippet> results = mRealm.where(Snippet.class).findAll();
        for (Snippet item : results) {
            if (item.getVideoId().equals(videoId)) {
                return true;
            }
        }
        return false;
    }

    public void updateFavourite(String videoId, double order) {
        Snippet video = mRealm.where(Snippet.class).equalTo("videoId", videoId).findFirst();
        mRealm.beginTransaction();
        if (video != null) {
            video.setOrder(order);
        }
        mRealm.commitTransaction();
    }

    public List<Snippet> queryFavourite() {
        RealmResults<Snippet> results = mRealm.where(Snippet.class).findAllSorted("order", Sort.DESCENDING);
        return mRealm.copyFromRealm(results);
    }
}
