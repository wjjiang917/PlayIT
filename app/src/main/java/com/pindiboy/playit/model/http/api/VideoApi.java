package com.pindiboy.playit.model.http.api;

import com.pindiboy.playit.model.bean.Channel;
import com.pindiboy.playit.model.bean.VideoLinks;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jiangwenjin on 2017/3/14.
 */

public interface VideoApi {
    String HOST = "DBdK97T3NZYEVCX3lMOyAvQX6yu6tdt3nf8fvSzooXw=";

    /**
     * get videos by channelId
     */
    @GET("api.php?ep=list-videos")
    Observable<Channel> getChannelVideos(@Query("uid") String channelId, @Query("token") String pageToken);

    /**
     * video link
     */
    @GET("api.php?ep=play-links")
    Observable<VideoLinks> getVideoLink(@Query("v_id") String videoId);
}
