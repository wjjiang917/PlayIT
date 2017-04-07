package com.pindiboy.playit.common;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */

public class RxBus {
    private static volatile RxBus mInstance;
    private SerializedSubject<Object, Object> mSubject;

    private RxBus() {
        mSubject = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getInstance() {
        if (mInstance == null) {
            synchronized (RxBus.class) {
                if (mInstance == null) {
                    mInstance = new RxBus();
                }
            }
        }
        return mInstance;
    }

    /**
     * post event
     */
    public void post(Object o) {
        mSubject.onNext(o);
    }

    /**
     * return specific Observable from eventType
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mSubject.ofType(eventType);
    }

    /**
     * default subscribe
     */
    public <T> Subscription toDefaultObservable(Class<T> eventType, Action1<T> act) {
        return mSubject.ofType(eventType).compose(RxUtil.<T>rxSchedulerHelper()).subscribe(act);
    }
}
