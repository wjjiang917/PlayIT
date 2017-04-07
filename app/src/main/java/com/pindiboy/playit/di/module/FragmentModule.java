package com.pindiboy.playit.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.pindiboy.playit.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jiangwenjin on 2017/3/4.
 */
@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
