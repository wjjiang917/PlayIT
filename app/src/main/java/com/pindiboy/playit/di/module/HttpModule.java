package com.pindiboy.playit.di.module;

import com.pindiboy.playit.App;
import com.pindi.play.it.BuildConfig;
import com.pindiboy.playit.common.Constant;
import com.pindiboy.playit.di.qualifier.ServiceType;
import com.pindiboy.playit.model.http.api.ChannelApi;
import com.pindiboy.playit.model.http.api.IpApi;
import com.pindiboy.playit.model.http.api.VideoApi;
import com.pindiboy.playit.model.http.api.YouTubeApi;
import com.pindiboy.playit.util.EncryptUtil;
import com.pindiboy.playit.util.FileUtil;
import com.pindiboy.playit.util.PhoneUtil;
import com.pindiboy.playit.util.SPUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
@Module
public class HttpModule {
    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    @ServiceType("YouTube")
    Retrofit provideYouTubeRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, YouTubeApi.HOST);
    }

    @Singleton
    @Provides
    YouTubeApi provideYouTubeService(@ServiceType("YouTube") Retrofit retrofit) {
        return retrofit.create(YouTubeApi.class);
    }

    @Singleton
    @Provides
    @ServiceType("Video")
    Retrofit provideVideoRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, EncryptUtil.decrypt(VideoApi.HOST));
    }

    @Singleton
    @Provides
    VideoApi provideVideoService(@ServiceType("Video") Retrofit retrofit) {
        return retrofit.create(VideoApi.class);
    }

    @Singleton
    @Provides
    @ServiceType("Ip")
    Retrofit provideIpRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, IpApi.HOST);
    }

    @Singleton
    @Provides
    IpApi provideIpService(@ServiceType("Ip") Retrofit retrofit) {
        return retrofit.create(IpApi.class);
    }

    @Singleton
    @Provides
    @ServiceType("Channel")
    Retrofit provideChannelRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ChannelApi.HOST);
    }

    @Singleton
    @Provides
    ChannelApi provideChannelService(@ServiceType("Channel") Retrofit retrofit) {
        return retrofit.create(ChannelApi.class);
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        Interceptor cacheInterceptor = chain -> {
            Request request = chain.request();
            if (!PhoneUtil.isNetworkConnected(App.getInstance())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (PhoneUtil.isNetworkConnected(App.getInstance())) {
                // 有网络时, 不缓存, 最大保存时长为0
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + 0)
                        .removeHeader("Pragma")
                        .build();
            } else {
                // 无网络时，设置超时为4周
                int maxStale = 60 * 60 * 24 * 28;
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
            return response;
        };

        Interceptor paramsInterceptor = chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            Request.Builder builder1 = null;
            if ("GET".equalsIgnoreCase(original.method())) {
                HttpUrl url;
                if (originalHttpUrl.toString().contains("youtube")) {
                    url = originalHttpUrl.newBuilder()
                            .addQueryParameter("regionCode", SPUtil.getCountryCode())
                            .build();
                } else {
                    url = originalHttpUrl.newBuilder().build();
                }

                builder1 = original.newBuilder()
                        .url(url)
                        .method(original.method(), original.body());
            } else {
                RequestBody oldRb = original.body();
                String reqParam = "";
                if (originalHttpUrl.toString().contains("youtube")) {
                    reqParam = "&regionCode=" + SPUtil.getCountryCode();
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Sink sink = Okio.sink(baos);
                BufferedSink bufferedSink = Okio.buffer(sink);
                oldRb.writeTo(bufferedSink);
                bufferedSink.writeString(reqParam, Charset.forName("UTF-8"));

                RequestBody rb = RequestBody.create(oldRb.contentType(), bufferedSink.buffer().readUtf8());
                builder1 = original.newBuilder().post(rb);
            }

            return chain.proceed(builder1.build());
        };

        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.addInterceptor(paramsInterceptor);

        File cacheDir = new File(FileUtil.getDiskCacheDir(App.getInstance()) + File.pathSeparator + Constant.CACHE_NET);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        if (cacheDir.exists()) {
            Cache cache = new Cache(cacheDir, 1024 * 1024 * 50); // 50M
            builder.cache(cache);
        }

        //设置超时
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
