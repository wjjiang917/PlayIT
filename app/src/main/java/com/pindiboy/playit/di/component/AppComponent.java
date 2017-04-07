package com.pindiboy.playit.di.component;

import com.pindiboy.playit.App;
import com.pindiboy.playit.di.module.AppModule;
import com.pindiboy.playit.di.module.HttpModule;
import com.pindiboy.playit.di.module.PageModule;
import com.pindiboy.playit.model.db.RealmHelper;
import com.pindiboy.playit.model.http.ApiService;
import com.pindiboy.playit.ui.fragment.FavoriteFragment;
import com.pindiboy.playit.ui.fragment.MainFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class, PageModule.class})
public interface AppComponent {
    App getContext();

    ApiService apiService();

    RealmHelper realmHelper();

    MainFragment mainFragment();

    FavoriteFragment favoriteFragment();
}
