package com.pindiboy.playit.model.bean;

import java.util.List;

/**
 * Created by Jiangwenjin on 2017/3/20.
 * <p>
 * "name": "TOP10 INSANE - Cricket",
 * "image": "https://yt3.ggpht.com/0AsjD5UBIHwUL9gzgrpmfqlcsYZoFzpVd6w1pX_grGoSxPlfEa2lOsDcV_9k0qfoXX9LJE7KFA=w640-fcrop64=1,32b75a57cd48a5a8-nd-c0xffffffff-rj-k-no",
 * "channel": "42 videos",
 * "views": "58930 subscribers",
 * "results": "42",
 * "entry": [],
 * "token": "CBQQAA"
 */

public class Channel {
    private String channel;
    private String image;
    private String name;
    private String results;
    private String token;
    private String views;
    private List<VideoDetail> entry;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public List<VideoDetail> getEntry() {
        return entry;
    }

    public void setEntry(List<VideoDetail> entry) {
        this.entry = entry;
    }
}
