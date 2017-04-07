package com.pindiboy.playit.model.http.api;

import com.pindiboy.playit.common.Constant;
import com.pindiboy.playit.model.bean.youtube.ItemId;
import com.pindiboy.playit.model.bean.youtube.YouTubeBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jiangwenjin on 2017/3/14.
 */

public interface YouTubeApi {
    String HOST = "https://www.googleapis.com/youtube/v3/";

    /**
     * get videos by channelId
     */
    @GET("search?part=snippet&type=video&order=date&maxResults=" + Constant.CHANNEL_VIDEOS_PAGE_SIZE + "&key=" + Constant.DEVELOPER_KEY)
    Observable<YouTubeBean<ItemId>> getChannelVideos(@Query("channelId") String channelId, @Query("pageToken") String pageToken);

    /**
     * get related videos by videoId
     */
    @GET("search?part=snippet&type=video&order=viewCount&maxResults=" + Constant.CHANNEL_VIDEOS_PAGE_SIZE + "&key=" + Constant.DEVELOPER_KEY)
    Observable<YouTubeBean<ItemId>> getRelatedVideos(@Query("relatedToVideoId") String videoId, @Query("pageToken") String pageToken);

    /**
     * video detail
     */
    @GET("videos?part=snippet,contentDetails,statistics&key=" + Constant.DEVELOPER_KEY)
    Observable<YouTubeBean<String>> getVideoDetail(@Query("id") String videoId);

    /**
     * most popular videos
     */
    @GET("videos?part=snippet,contentDetails,statistics&chart=mostPopular&key=" + Constant.DEVELOPER_KEY)
    Observable<YouTubeBean<String>> getTrending(@Query("maxResults") int maxResults, @Query("pageToken") String pageToken);

    /**
     * most viewed channels
     */
    @GET("search?part=snippet&type=channel&order=viewCount&maxResults=" + Constant.MOST_VIEWED_CHANNELS_PAGE_SIZE + "&key=" + Constant.DEVELOPER_KEY)
    Observable<YouTubeBean<ItemId>> getMostViewsChannels(@Query("pageToken") String pageToken);

    /**
     * search
     */
    @GET("search?part=snippet&type=video&order=relevance&maxResults=" + Constant.CHANNEL_VIDEOS_PAGE_SIZE + "&key=" + Constant.DEVELOPER_KEY)
    Observable<YouTubeBean<ItemId>> search(@Query("q") String q, @Query("pageToken") String pageToken);
}
