package com.pindiboy.playit.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;

import com.pindi.play.it.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */

public class FileUtil {
    public static String getVideosDir() {
        String dir = Environment.getExternalStorageDirectory().getPath() + "/" + BuildConfig.APPLICATION_ID + "/Videos/";
        File file = new File(dir);
        if (!file.exists() && !file.mkdirs()) {
            return null;
        }

        return dir;
    }

    /**
     * 获取可以使用的缓存目录
     *
     * @param context
     * @param uniqueName 目录名称
     * @return
     */
    public static File getDiskCache(Context context, String uniqueName) {
        File f = new File(getDiskCacheDir(context) + uniqueName);
        if (f.exists()) {
            f.delete();
        }
        return f;
    }

    public static String getDiskCacheDir(Context context) {
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ?
                getExternalDir(context, "cache").getPath() : context.getCacheDir().getPath();
        File file = new File(cachePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return cachePath + File.separator;
    }

    public static String getDiskFilesDir(Context context) {
        final String path = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ?
                getExternalDir(context, "files").getPath() : context.getFilesDir().getPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path + File.separator;
    }

    /**
     * 获取程序外部的缓存目录
     *
     * @param context
     * @return
     */
    public static File getExternalDir(Context context, String dir) {
        final String path = "/Android/data/" + context.getPackageName() + "/" + dir + "/";
        return new File(Environment.getExternalStorageDirectory().getPath() + path);
    }

    /**
     * 获取文件路径空间大小
     *
     * @param path
     * @return
     */
    public static long getUsableSpace(File path) {
        try {
            final StatFs stats = new StatFs(path.getPath());
            return (long) stats.getBlockSize()
                    * (long) stats.getAvailableBlocks();
        } catch (Exception e) {
            Logger.e("获取 sdcard 缓存大小 出错，请查看AndroidManifest.xml 是否添加了sdcard的访问权限");
            Logger.e("", e);
            return -1;
        }
    }

    public static boolean isBlackPicture(Bitmap picture) {
        long start = System.currentTimeMillis();
        if (null == picture) {
            return true;
        }

        boolean isBlack = true;
        int width = picture.getWidth();
        int height = picture.getHeight();
        Logger.v("picture size: " + width + ", " + height);

        Random random = new Random();
        int x, y;
        for (int i = 0; i < 10; i++) {
            x = random.nextInt(width);
            y = random.nextInt(height);
            Logger.v("picture loop[" + i + "] x, y: " + x + ", " + y);

            int pixel = picture.getPixel(x, y);
            if (pixel != Color.BLACK) {
                isBlack = false;
                break;
            }
        }

        Logger.v("picture is black: " + isBlack + ", time: " + (System.currentTimeMillis() - start));
        return isBlack;
    }

    /**
     * transfer bitmap tp file, check if file is exist first
     *
     * @param bitmap   Bitmap
     * @param filePath filePath
     * @return File
     */
    public static File bitmapToFile(Bitmap bitmap, String filePath) {
        if (null == bitmap) {
            return null;
        }

        File file = new File(filePath);
        try {
            if ((!file.exists() || !file.isFile()) && !file.createNewFile()) {
                return null;
            }

            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            Logger.e("", e);
        }
        return file;
    }

    public static String fileToBase64(File file) {
        if (null == file) {
            return "";
        }

        Logger.d("fileToBase64: " + file.getAbsolutePath());
        ByteArrayOutputStream baos = null;
        try {
            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            return "data:image/jpeg;base64," + Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            Logger.e("", e);
            return "";
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException ioe) {
                // ignore
            }
        }
    }

    static String[] splitFileName(String fileName) {
        String name = fileName;
        String extension = "";
        int i = fileName.lastIndexOf(".");
        if (i != -1) {
            name = fileName.substring(0, i);
            extension = fileName.substring(i);
        }

        return new String[]{name, extension};
    }

    static String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } catch (Exception e) {
                Logger.e("", e);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf(File.separator);
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            String realPath = cursor.getString(index);
            cursor.close();
            return realPath;
        }
    }
}
