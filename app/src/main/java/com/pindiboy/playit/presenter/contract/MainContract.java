package com.pindiboy.playit.presenter.contract;

import com.pindiboy.playit.presenter.BasePresenter;
import com.pindiboy.playit.ui.BaseView;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */

public interface MainContract {
    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<View> {
    }
}
