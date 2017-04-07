package com.pindiboy.playit.presenter;

import com.pindiboy.playit.ui.BaseView;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
