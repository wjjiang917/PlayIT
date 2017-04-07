package com.pindiboy.playit.model.bean.youtube;

/**
 * Created by Jiangwenjin on 2017/3/15.
 * <p>
 * "kind": "youtube#video",
 * "videoId": "Ee2ot0QkEKg"
 */

public class ItemId {
    private String kind;
    private String videoId;
    private String channelId;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
