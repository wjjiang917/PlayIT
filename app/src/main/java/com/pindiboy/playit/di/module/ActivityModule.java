package com.pindiboy.playit.di.module;

import android.app.Activity;

import com.pindiboy.playit.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
