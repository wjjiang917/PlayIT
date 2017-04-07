package com.pindiboy.playit.model.bean.youtube;

import java.util.List;
import java.util.Map;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Jiangwenjin on 2017/3/15.
 * <p>
 * "publishedAt": "2016-11-19T11:30:00.000Z",
 * "channelId": "UC1KX5wQ8yqJTByEsd2KhrLA",
 * "title": "TOP 10 REASONS WHY MS DHONI WILL NEVER BE HATED",
 * "description": "10 reason why MS Dhoni is the best cricket captain in the world. EDITOR:- Sony Vegas Pro13 AUDIO SOURCE : NCS SUBSCRIBE CHANNEL: ...",
 * "thumbnails": {},
 * "channelTitle": "TOP10 INSANE - Cricket",
 * "liveBroadcastContent": "none"
 */

public class Snippet extends RealmObject {
    @Ignore
    private String categoryId;
    private String channelId;
    private String channelTitle;
    @Ignore
    private String description;
    @Ignore
    private String liveBroadcastContent;
    @Ignore
    private Localized localized;
    private String publishedAt;
    @Ignore
    private List<String> tags;
    @Ignore
    private Map<String, Thumbnail> thumbnails; // key: default, medium, high
    private String title;

    @PrimaryKey
    private String videoId;
    private boolean isFavourite; // save to local db
    private String thumbnail;
    private double order;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Localized getLocalized() {
        return localized;
    }

    public void setLocalized(Localized localized) {
        this.localized = localized;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLiveBroadcastContent() {
        return liveBroadcastContent;
    }

    public void setLiveBroadcastContent(String liveBroadcastContent) {
        this.liveBroadcastContent = liveBroadcastContent;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Map<String, Thumbnail> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Map<String, Thumbnail> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public double getOrder() {
        return order;
    }

    public void setOrder(double order) {
        this.order = order;
    }
}
