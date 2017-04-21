package com.pindiboy.playit.util;

import android.app.Activity;
import android.support.v4.content.ContextCompat;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.pindi.play.it.R;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
public class TipUtil {
    public static void showSnackbar(Activity activity, String msg) {
        new SuperActivityToast(activity)
                .setText(msg)
                .setTextColor(ContextCompat.getColor(activity, android.R.color.white))
                .setColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                .setDuration(Style.DURATION_VERY_SHORT)
                .setFrame(Style.FRAME_LOLLIPOP)
                .setAnimations(Style.ANIMATIONS_POP)
                .show();
    }

    public static void showToast(Activity activity, String msg) {
        new SuperActivityToast(activity)
                .setText(msg)
                .setTextColor(ContextCompat.getColor(activity, android.R.color.white))
                .setColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                .setDuration(Style.DURATION_VERY_SHORT)
                .setFrame(Style.FRAME_KITKAT)
                .setAnimations(Style.ANIMATIONS_POP)
                .show();
    }
}
