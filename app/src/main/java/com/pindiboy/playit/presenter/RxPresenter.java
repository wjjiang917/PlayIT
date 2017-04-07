package com.pindiboy.playit.presenter;

import com.pindiboy.playit.common.RxBus;
import com.pindiboy.playit.model.db.RealmHelper;
import com.pindiboy.playit.model.http.ApiService;
import com.pindiboy.playit.ui.BaseView;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */

public class RxPresenter<T extends BaseView> implements BasePresenter<T> {
    protected T mView;
    protected CompositeSubscription mCompositeSubscription;

    /**
     * api service
     */
    protected ApiService mApiService;

    /**
     * api service
     */
    protected RealmHelper mRealmHelper;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    protected <U> void addRxBusSubscribe(Class<U> eventType, Action1<U> act) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(RxBus.getInstance().toDefaultObservable(eventType, act));
    }
}
