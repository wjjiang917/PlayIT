package com.pindiboy.playit.model.http;

import com.pindiboy.playit.model.bean.ChannelConfig;
import com.pindiboy.playit.model.bean.IpInfo;
import com.pindiboy.playit.model.bean.youtube.ItemId;
import com.pindiboy.playit.model.bean.youtube.YouTubeBean;
import com.pindiboy.playit.model.http.api.ChannelApi;
import com.pindiboy.playit.model.http.api.IpApi;
import com.pindiboy.playit.model.http.api.YouTubeApi;

import java.util.List;

import rx.Observable;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */

public class ApiService {
    private YouTubeApi youTubeApi;
    private IpApi ipApi;
    private ChannelApi channelApi;

    public ApiService(YouTubeApi youTubeApi, IpApi ipApi, ChannelApi channelApi) {
        this.youTubeApi = youTubeApi;
        this.ipApi = ipApi;
        this.channelApi = channelApi;
    }

    public Observable<YouTubeBean<ItemId>> fetchChannelVideos(String channelId, String pageToken) {
        return youTubeApi.getChannelVideos(channelId, pageToken);
    }

    public Observable<YouTubeBean<ItemId>> fetchRelatedVideos(String videoId, String pageToken) {
        return youTubeApi.getRelatedVideos(videoId, pageToken);
    }

    public Observable<YouTubeBean<ItemId>> search(String q, String pageToken) {
        return youTubeApi.search(q, pageToken);
    }

    public Observable<YouTubeBean<String>> fetchVideoDetail(String videoId) {
        return youTubeApi.getVideoDetail(videoId);
    }

    public Observable<YouTubeBean<String>> fetchTrending(int maxResults, String pageToken) {
        return youTubeApi.getTrending(maxResults, pageToken);
    }

    public Observable<YouTubeBean<ItemId>> fetchMostViewsChannels(String pageToken) {
        return youTubeApi.getMostViewsChannels(pageToken);
    }

    public Observable<IpInfo> fetchIpInfo() {
        return ipApi.getIpInfo();
    }

    public Observable<List<ChannelConfig>> fetchChannels() {
        return channelApi.getChannels();
    }
}
