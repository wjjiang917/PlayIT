package com.pindiboy.playit.model.bean.youtube;

import io.realm.RealmObject;

/**
 * Created by Jiangwenjin on 2017/3/15.
 * <p>
 * "url": "https://i.ytimg.com/vi/Ee2ot0QkEKg/default.jpg",
 * "width": 120,
 * "height": 90
 */

public class Thumbnail extends RealmObject {
    public static final String TYPE_DEFAULT = "default";
    public static final String TYPE_MEDIUM = "medium";
    public static final String TYPE_HIGH = "high";

    private String url;
    private int width;
    private int height;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
