package com.pindiboy.playit.model.http.api;

import com.pindiboy.playit.model.bean.ChannelConfig;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Jiangwenjin on 2017/3/23.
 */

public interface ChannelApi {
    String HOST = "https://s3.ap-south-1.amazonaws.com/pindiboy/WeddingVideo/";

    @GET("channels.json")
    Observable<List<ChannelConfig>> getChannels();
}
