package com.pindiboy.playit.di.component;

import android.app.Activity;

import com.pindiboy.playit.di.module.ActivityModule;
import com.pindiboy.playit.di.scope.ActivityScope;
import com.pindiboy.playit.ui.activity.ChannelDetailActivity;
import com.pindiboy.playit.ui.activity.MainActivity;
import com.pindiboy.playit.ui.activity.PlayerActivity;
import com.pindiboy.playit.ui.activity.SearchActivity;
import com.pindiboy.playit.ui.activity.SplashActivity;

import dagger.Component;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(PlayerActivity playerActivity);

    void inject(SearchActivity searchActivity);

    void inject(ChannelDetailActivity channelDetailActivity);
}
