package com.pindiboy.playit.util;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jiangwenjin on 2017/3/6.
 */
public class NumberUtil {
    public static String format(double value) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(value);
    }

    public static String format(String value) {
        long t = Long.valueOf(value);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(t);
    }

    public static String getNumber(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }
}
