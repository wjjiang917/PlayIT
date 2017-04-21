package com.pindiboy.playit.util;

import android.os.Environment;
import android.util.Log;

import com.pindi.play.it.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jiangwenjin on 2017/2/28.
 */
public class Logger {
    /**
     * 是否打印
     */
    private static boolean isDebugEnabled = true;
    private static boolean isErrorEnabled = true;
    private static boolean isInfoEnabled = true;
    private static boolean isVerboseEnabled = true;
    private static boolean isWarnEnabled = true;
    private static boolean isWtfEnabled = true;

    private static boolean isSaveLog = true;

    public static String dir = Environment.getExternalStorageDirectory().getPath() + "/" + BuildConfig.APPLICATION_ID + "/log/";

    private Logger() {
    }

    private static String getTag() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String clazzName = stackTrace[4].getClassName();
        clazzName = clazzName.substring(clazzName.lastIndexOf(".") + 1);

        // Logger.getTag(54)
        return clazzName + "." + stackTrace[4].getMethodName() + "(" + stackTrace[4].getLineNumber() + ")";
    }

    public static void d(String msg) {
        if (!isDebugEnabled) return;

        String tag = getTag();
        Log.d(tag, msg);

        if (isSaveLog) {
            saveLog(tag, " DEBUG | " + msg);
        }
    }

    public static void d(String msg, Throwable tr) {
        if (!isDebugEnabled) return;

        String tag = getTag();
        Log.d(tag, msg, tr);

        if (isSaveLog) {
            saveLog(tag, " DEBUG | " + msg + '\n' + Log.getStackTraceString(tr));
        }
    }

    public static void e(String msg) {
        if (!isErrorEnabled) return;

        String tag = getTag();
        Log.e(tag, msg);

        if (isSaveLog) {
            saveLog(tag, " ERROR | " + msg);
        }
    }

    public static void e(String msg, Throwable tr) {
        if (!isErrorEnabled) return;

        String tag = getTag();
        Log.e(tag, msg, tr);

        if (isSaveLog) {
            saveLog(tag, " ERROR | " + msg + '\n' + Log.getStackTraceString(tr));
        }
    }

    public static void i(String msg) {
        if (!isInfoEnabled) return;
        Log.i(getTag(), msg);
    }

    public static void i(String msg, Throwable tr) {
        if (!isInfoEnabled) return;
        Log.i(getTag(), msg, tr);
    }

    public static void v(String msg) {
        if (!isVerboseEnabled) return;
        Log.v(getTag(), msg);
    }

    public static void v(String msg, Throwable tr) {
        if (!isVerboseEnabled) return;
        Log.v(getTag(), msg, tr);
    }

    public static void w(String msg) {
        if (!isWarnEnabled) return;

        String tag = getTag();
        Log.w(getTag(), msg);

        if (isSaveLog) {
            saveLog(tag, " WARN | " + msg);
        }
    }

    public static void w(String msg, Throwable tr) {
        if (!isWarnEnabled) return;

        String tag = getTag();
        Log.w(getTag(), msg, tr);

        if (isSaveLog) {
            saveLog(tag, " WARN | " + msg + '\n' + Log.getStackTraceString(tr));
        }
    }

    public static void w(Throwable tr) {
        if (!isWarnEnabled) return;

        String tag = getTag();
        Log.w(getTag(), tr);

        if (isSaveLog) {
            saveLog(tag, " WARN | " + Log.getStackTraceString(tr));
        }
    }

    public static void wtf(String msg) {
        if (!isWtfEnabled) return;
        Log.wtf(getTag(), msg);
    }

    public static void wtf(String msg, Throwable tr) {
        if (!isWtfEnabled) return;
        Log.wtf(getTag(), msg, tr);
    }

    public static void wtf(Throwable tr) {
        if (!isWtfEnabled) return;
        Log.wtf(getTag(), tr);
    }

    private static void saveLog(String tag, String msg) {
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    || Environment.getExternalStorageDirectory().exists()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String time = sdf.format(new Date());

                File dirFile = new File(dir);
                if (!dirFile.exists()) {
                    System.out.println(dirFile.getAbsolutePath() + " mkdirs return: " + dirFile.mkdirs());
                }

                if (!dirFile.exists()) {
                    return;
                }

                String log = dir + time + ".log";
                File logFile = new File(log);
                if (!logFile.exists()) {
                    try {
                        logFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (!logFile.exists()) {
                    return;
                }

                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                time = sdf.format(new Date());

                // put app version in logs
                String data = time + " | " + BuildConfig.VERSION_NAME + " | " + tag + " | " + msg + "\n";
                OutputStream out = null;
                try {
                    out = openOutputStream(logFile, true);
                    out.write(data.getBytes(Charset.defaultCharset()));
                    out.close(); // don't swallow close Exception if copy completes normally
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException ioe) {
                        // ignore
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param file   the file to open for output, must not be {@code null}
     * @param append if {@code true}, then bytes will be added to the
     *               end of the file rather than overwriting
     * @return a new {@link FileOutputStream} for the specified file
     * @throws IOException
     */
    public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canWrite() == false) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file, append);
    }

    /**
     * only save logs in 10 days
     */
    public static void clear(long expiryPeriod) {
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            return;
        }

        File[] logs = dirFile.listFiles();
        if (null != logs && logs.length > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fileName;
            for (File tLog : logs) {
                fileName = tLog.getName();
                if (fileName.contains(".")) {
                    fileName = fileName.substring(0, fileName.indexOf("."));

                    try {
                        long fileTime = sdf.parse(fileName).getTime();
                        if (new Date().getTime() - fileTime > expiryPeriod) {
                            System.out.println("Delete log file, file name: " + fileName);
                            tLog.delete();
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }
    }
}
