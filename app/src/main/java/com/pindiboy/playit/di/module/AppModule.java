package com.pindiboy.playit.di.module;

import com.pindiboy.playit.App;
import com.pindiboy.playit.model.db.RealmHelper;
import com.pindiboy.playit.model.http.ApiService;
import com.pindiboy.playit.model.http.api.ChannelApi;
import com.pindiboy.playit.model.http.api.IpApi;
import com.pindiboy.playit.model.http.api.YouTubeApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    ApiService provideApiService(YouTubeApi youTubeApi, IpApi ipApi, ChannelApi channelApi) {
        return new ApiService(youTubeApi, ipApi, channelApi);
    }

    @Provides
    @Singleton
    RealmHelper provideRealmHelper() {
        return new RealmHelper(application);
    }
}
