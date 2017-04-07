package com.pindiboy.playit.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.pindiboy.playit.App;
import com.pindiboy.playit.common.Constant;

/**
 * Created by Jiangwenjin on 2017/3/7.
 */

public class SPUtil {
    public static SharedPreferences getSP() {
        return App.getInstance().getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
    }

    public static String getCountryCode() {
        return getSP().getString(Constant.SP_KEY_COUNTRY_CODE, "");
    }

    public static void setCountryCode(String countryCode) {
        getSP().edit().putString(Constant.SP_KEY_COUNTRY_CODE, countryCode).apply();
    }

    public static int getCurrentPage() {
        return getSP().getInt(Constant.SP_KEY_CURRENT_PAGE, Constant.TYPE_HOME);
    }

    public static void setCurrentPage(int item) {
        getSP().edit().putInt(Constant.SP_KEY_CURRENT_PAGE, item).apply();
    }
}
