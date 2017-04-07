package com.pindiboy.playit.model.bean.youtube;

/**
 * Created by Jiangwenjin on 2017/3/16.
 * <p>
 * "viewCount": "535562",
 * "likeCount": "1985",
 * "dislikeCount": "270",
 * "favoriteCount": "0",
 * "commentCount": "39"
 */

public class Statistics {
    private String commentCount;
    private String dislikeCount;
    private String favoriteCount;
    private String likeCount;
    private String viewCount;

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(String dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public String getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(String favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }
}
