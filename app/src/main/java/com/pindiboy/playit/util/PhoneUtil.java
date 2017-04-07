package com.pindiboy.playit.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */

public class PhoneUtil {
    /**
     * get App Version Name
     */
    public static String getAppVersionName(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e("", e);
        }
        return null;
    }

    /**
     * get App Version code
     */
    public static int getAppVersionCode(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e("", e);
        }

        return 0;
    }

    /**
     * 获取手机厂商
     */
    public static String getVendor() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取系统版本号
     */
    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * get device name
     *
     * @return
     */
    public static String getDeviceName() {
        return Build.MODEL;
    }

    // 获取国际移动用户识别码(International Mobile Subscriber Identity)
    public static String getImsi(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getSubscriberId();
    }

    /**
     * 获取移动设备国际身份码(International Mobile Equipment Identity)
     */
    public static String getImei(Context c) {
        try {
            TelephonyManager mTelephonyMgr = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
            return mTelephonyMgr.getDeviceId();
        } catch (Exception e) {
            Logger.e("", e);
            return "";
        }
    }

    /**
     * 获取Mac地址
     */
//    public static String getMacAddress(Context c) {
//        try {
//            WifiManager wifiMgr = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
//            WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
//            if (null != info) {
//                return info.getMacAddress();
//            } else {
//                return System.currentTimeMillis() + "";
//            }
//        } catch (Exception e) {
//            Logger.e("", e);
//            return System.currentTimeMillis() + "";
//        }
//    }

    // WifiManager
//    public static String getDeviceId(Context c) {
//        String device = getImei(c); // 移动设备国际身份码(International Mobile Equipment Identity)
//        if (device == null || "".equals(device)) {
//            device = getImsi(c); // 国际移动用户识别码(International Mobile Subscriber Identity)
//            if (device == null || "".equals(device)) {
//                device = getMacAddress(c); // Mac地址
//            }
//        }
//        return device;
//    }
    public static int getPortalType(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return ai.metaData.getInt("CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e("PhoneUtil can not find metaname: CHANNEL", e);
        }

        return 200;
    }


    public static boolean isServiceRunning(Context context, String className) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo service : services) {
            if (service.service.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAppInstalled(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

//    private static long tempSize = 0L;
//    private static long tempTime = 0L;
//
//    public static long getUploadSpeed() {
//        long val = 0L;
//        long now = System.currentTimeMillis();
//        long totalSize = getTotalBytes();
//        if(tempTime != 0L) {
//            val = (totalSize - tempSize) / (now - tempTime);
//        }
//
//        tempTime = now;
//        tempSize = totalSize;
//        return val;
//    }
//
//    public static long getTotalBytes() {
//        FileReader segs = null;
//        long tmp = 0L;
//        long totalSize = 0L;
//
//        try {
//            segs = new FileReader("/proc/net/dev");
//            BufferedReader in = new BufferedReader(segs, 500);
//
//            while(true) {
//                while(true) {
//                    String var11;
//                    int var12;
//                    do {
//                        if((var11 = in.readLine()) == null) {
//                            return totalSize;
//                        }
//
//                        var11 = var11.trim();
//                        var12 = 0;
//                    } while(!var11.startsWith("rmnet") && !var11.startsWith("eth") && !var11.startsWith("wlan") && !var11.startsWith("ccmni"));
//
//                    String[] var13 = var11.split(":")[1].split(" +");
//
//                    for(int i = 0; i < var13.length; ++i) {
//                        boolean isNum = true;
//
//                        try {
//                            tmp = Long.parseLong(var13[i]);
//                        } catch (Exception var9) {
//                            isNum = false;
//                        }
//
//                        if(isNum) {
//                            ++var12;
//                        }
//
//                        if(var12 == 9) {
//                            totalSize += tmp;
//                            break;
//                        }
//                    }
//                }
//            }
//        } catch (Exception var10) {
//            var10.printStackTrace();
//            return totalSize;
//        }
//    }

    public static void hideNavigationBar(Activity activity) {
        // set navigation bar status, remember to disable "setNavigationBarTintEnabled"
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        // This work only for android 4.4+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = activity.getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
        }
    }

    public static void showNavigationBar(Activity activity) {
        // set navigation bar status, remember to disable "setNavigationBarTintEnabled"
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        // This work only for android 4.4+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = activity.getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
        }
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int id = resources.getIdentifier(resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", "android");
        if (id > 0) {
            return resources.getDimensionPixelSize(id);
        }
        return 0;
    }

    public static Map<String, String> getPhoneInfo(Context context) {
        Map<String, String> infos = new HashMap<>();
        infos.put("imei", getImei(context) + "");
        infos.put("imsi", getImsi(context) + "");
        infos.put("deviceName", getDeviceName() + "");
        infos.put("phoneOperator", getVendor() + "");
        infos.put("sdkVersion", Build.VERSION.SDK_INT + "");
        infos.put("osVersion", getOsVersion());
        infos.put("brand", Build.BRAND);
        infos.put("appVersionName", getAppVersionName(context) + "");
        infos.put("appVersionCode", getAppVersionCode(context) + "");
        infos.put("phoneSize", getDisplaySize(context)[0] + "*" + getDisplaySize(context)[1]);
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        infos.put("phoneNumber", mTelephonyMgr.getLine1Number() + "");
        infos.put("simNumber", mTelephonyMgr.getSimSerialNumber() + "");
        infos.put("simOperator", mTelephonyMgr.getSimOperator() + "");

        infos.put("simState", mTelephonyMgr.getSimState() + "");
        infos.put("simCountryIso", mTelephonyMgr.getSimCountryIso() + "");
        infos.put("simOperatorName", mTelephonyMgr.getSimOperatorName() + "");
        infos.put("voiceMailNumber", mTelephonyMgr.getVoiceMailNumber() + "");
        infos.put("callState", mTelephonyMgr.getCallState() + "");
        infos.put("cellLocation", mTelephonyMgr.getCellLocation() + "");
        infos.put("phoneType", mTelephonyMgr.getPhoneType() + "");
        infos.put("deviceSoftwareVersion", mTelephonyMgr.getDeviceSoftwareVersion() + "");
        infos.put("networkType", mTelephonyMgr.getNetworkType() + "");
        infos.put("networkCountryIso", mTelephonyMgr.getNetworkCountryIso() + "");
        infos.put("networkOperator", mTelephonyMgr.getNetworkOperator() + "");
        infos.put("networkOperatorName", mTelephonyMgr.getNetworkOperatorName() + "");
        infos.put("timeZone", TimeZone.getDefault().getID() + "");
//        infos.put("macAddress", getMacAddress(context) + "");
        infos.put("memory", getTotalMemory(context) + "");
        infos.put("cpu", getCpuInfo() + "");
        return infos;
    }

    public static int[] getDisplaySize(Context context) {
        int[] size = new int[2];
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Method mGetRawH = null, mGetRawW = null;
        try {
            // For JellyBean 4.2 (API 17) and onward
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealMetrics(metrics);

                size[0] = metrics.widthPixels;
                size[1] = metrics.heightPixels;
            } else {
                mGetRawH = Display.class.getMethod("getRawHeight");
                mGetRawW = Display.class.getMethod("getRawWidth");

                size[0] = (Integer) mGetRawW.invoke(display);
                size[1] = (Integer) mGetRawH.invoke(display);
            }
        } catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            Logger.e("", e);
            metrics = context.getResources().getDisplayMetrics();
            size[0] = metrics.widthPixels;
            size[1] = metrics.heightPixels;
        }
        return size;
    }

    private static String getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(context, mi.availMem);
    }

    private static String getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;
            localBufferedReader.close();

        } catch (IOException e) {
        }
        return Formatter.formatFileSize(context, initial_memory);
    }

    private static String getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2;
        String[] cpuInfo = {"", ""};
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return "Model-" + cpuInfo[0] + ", Hz-" + cpuInfo[1];
    }

    public static int getWindowWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        Method mGetRawH = null, mGetRawW = null;
        try {
            // For JellyBean 4.2 (API 17) and onward
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealMetrics(metrics);
                return metrics.widthPixels;
            }

            mGetRawW = Display.class.getMethod("getRawWidth");
            return (Integer) mGetRawW.invoke(display);
        } catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            Logger.e("", e);
            metrics = activity.getResources().getDisplayMetrics();
            return metrics.widthPixels;
        }
    }

    public static int getWindowHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        Method mGetRawH = null, mGetRawW = null;
        try {
            // For JellyBean 4.2 (API 17) and onward
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealMetrics(metrics);
                return metrics.heightPixels;
            }

            mGetRawH = Display.class.getMethod("getRawHeight");
            return (Integer) mGetRawH.invoke(display);
        } catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            Logger.e("", e);
            metrics = activity.getResources().getDisplayMetrics();
            return metrics.heightPixels;
        }
    }

//    public static Location getLocation(Context context) {
//        if (null == context) {
//            return null;
//        }
//        LocationManager locationManager = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);
//        List<String> providers = locationManager.getProviders(true);
//        String locationProvider = null;
//        if (providers.contains(LocationManager.GPS_PROVIDER)) {
//            locationProvider = LocationManager.GPS_PROVIDER;
//        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
//            locationProvider = LocationManager.NETWORK_PROVIDER;
//        }
//        if (!TextUtils.isEmpty(locationProvider)) {
//            return locationManager.getLastKnownLocation(locationProvider);
//        }
//        return null;
//    }
//
//    public static double getLatitude(Context context) {
//        Location location = getLocation(context);
//        if (null != location) {
//            return location.getLatitude();
//        }
//        return 0;
//    }
//
//    public static double getLongitude(Context context) {
//        Location location = getLocation(context);
//        if (null != location) {
//            return location.getLongitude();
//        }
//        return 0;
//    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
