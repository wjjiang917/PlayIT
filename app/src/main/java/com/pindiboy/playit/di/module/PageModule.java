package com.pindiboy.playit.di.module;

import com.pindiboy.playit.ui.fragment.FavoriteFragment;
import com.pindiboy.playit.ui.fragment.MainFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
@Module
public class PageModule {
    @Singleton
    @Provides
    MainFragment provideMain() {
        return new MainFragment();
    }

    @Singleton
    @Provides
    FavoriteFragment provideFavorite() {
        return new FavoriteFragment();
    }
}
