package com.pindiboy.playit.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}
