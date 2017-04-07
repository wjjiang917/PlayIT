package com.pindiboy.playit.di.component;

import android.app.Activity;

import com.pindiboy.playit.di.module.FragmentModule;
import com.pindiboy.playit.di.scope.FragmentScope;
import com.pindiboy.playit.ui.fragment.ChannelFragment;
import com.pindiboy.playit.ui.fragment.FavoriteFragment;
import com.pindiboy.playit.ui.fragment.HomeFragment;
import com.pindiboy.playit.ui.fragment.MostViewedChannelsFragment;

import dagger.Component;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

    void inject(ChannelFragment channelFragment);

    void inject(FavoriteFragment favoriteFragment);

    void inject(HomeFragment homeFragment);

    void inject(MostViewedChannelsFragment mostViewedChannelsFragment);
}
