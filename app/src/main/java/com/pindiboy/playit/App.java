package com.pindiboy.playit;

import android.app.Application;
import android.content.Context;

import com.bugtags.library.Bugtags;
import com.pindiboy.playit.di.component.AppComponent;
import com.pindiboy.playit.di.component.DaggerAppComponent;
import com.pindiboy.playit.di.module.AppModule;
import com.pindiboy.playit.di.module.HttpModule;
import com.pindiboy.playit.di.module.PageModule;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
public class App extends Application {
    private static App mInstance;
    public static AppComponent appComponent;

    public static synchronized App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //在这里初始化
        Bugtags.start("8b427c3783cac11c4d33d8ac9241d5cb", this, Bugtags.BTGInvocationEventNone);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(mInstance))
                    .httpModule(new HttpModule())
                    .pageModule(new PageModule())
                    .build();
        }
        return appComponent;
    }
}
