package com.pindiboy.playit.common;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */

public interface Constant {
    // Constant
    String CACHE_NET = "net";
    String DEVELOPER_KEY = "AIzaSyD6RJNbrSDK3QheXOy2mCg056lLnMLwso0";
    int MAX_FAVORITE = 100;

    int CHANNEL_VIDEOS_PAGE_SIZE = 10;
    int MOST_VIEWED_CHANNELS_PAGE_SIZE = 10;

    // orig type
    int TYPE_HOME = 100;
    int TYPE_DOWNLOADS = 101;
    int TYPE_FAVORITES = 102;

    // SharePreference
    String SP_NAME = "sp_name";
    String SP_KEY_COUNTRY_CODE = "sp_key_country_code";
    String SP_KEY_CURRENT_PAGE = "sp_key_current_page";

    // intent extra key / fragment argument bundle key
    String BUNDLE_CHANNEL_ID = "bundle_channel_id";
    String INTENT_EXTRA_VIDEO_ID = "intent_extra_video_id";
    String INTENT_EXTRA_SEARCH_Q = "intent_extra_search_q";
    String INTENT_EXTRA_CHANNELS = "intent_extra_channels";
}
