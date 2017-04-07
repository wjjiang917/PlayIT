package com.pindiboy.playit.model.bean.youtube;

import java.util.List;

/**
 * Created by Jiangwenjin on 2017/3/14.
 * <p>
 * "kind": "youtube#searchListResponse",
 * "etag": "\"uQc-MPTsstrHkQcRXL3IWLmeNsM/bECqA6d-kVjW5-HYx-X0Vw1N6fk\"",
 * "nextPageToken": "CAUQAA",
 * "regionCode": "PK",
 * "pageInfo": {},
 * "items": []
 */
public class YouTubeBean<T> {
    private String kind;
    private String etag;
    private String prevPageToken;
    private String nextPageToken;
    private String regionCode;
    private PageInfo pageInfo;
    private List<Item<T>> items;

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getPrevPageToken() {
        return prevPageToken;
    }

    public void setPrevPageToken(String prevPageToken) {
        this.prevPageToken = prevPageToken;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<Item<T>> getItems() {
        return items;
    }

    public void setItems(List<Item<T>> items) {
        this.items = items;
    }
}
