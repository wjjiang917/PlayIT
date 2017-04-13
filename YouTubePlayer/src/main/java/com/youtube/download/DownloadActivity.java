package com.youtube.download;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.youtube.iframeplayer.R;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

/**
 * Created by Jiangwenjin on 2017/3/22.
 */
public class DownloadActivity extends Activity {
    public static String INTENT_EXTRA_VIDEO_ID = "intent_extra_video_id";
    public static String INTENT_EXTRA_PARSEDASHMANIFEST = "intent_extra_parsedashmanifest";
    public static String INTENT_EXTRA_DOWNLOADDASH = "intent_extra_downloaddash";
    public static String INTENT_EXTRA_INCLUDEWEBM = "intent_extra_includewebm";
    public static String INTENT_EXTRA_DOWNLOAD_DIR = "intent_extra_download_dir";

    private boolean parseDashManifest;
    private boolean downloadDash;
    private boolean includeWebM;
    private String videoId;
    private String dir;
    private LinearLayout mainLayout;
    private ProgressBar mainProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoId = getIntent().getStringExtra(INTENT_EXTRA_VIDEO_ID);
        dir = getIntent().getStringExtra(INTENT_EXTRA_DOWNLOAD_DIR);
        parseDashManifest = getIntent().getBooleanExtra(INTENT_EXTRA_PARSEDASHMANIFEST, false);
        downloadDash = getIntent().getBooleanExtra(INTENT_EXTRA_DOWNLOADDASH, false);
        includeWebM = getIntent().getBooleanExtra(INTENT_EXTRA_INCLUDEWEBM, false);

        setContentView(R.layout.activity_download);
        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        mainProgressBar = (ProgressBar) findViewById(R.id.prgrBar);

        if (TextUtils.isEmpty(videoId)) {
            finish();
            return;
        }

        getYoutubeDownloadUrl("http://youtube.com/watch?v=" + videoId);
    }

    private void getYoutubeDownloadUrl(String youtubeLink) {
        new YouTubeExtractor(this) {

            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                mainProgressBar.setVisibility(View.GONE);

                if (ytFiles == null) {
                    // Something went wrong we got no urls. Always check this.
                    Button btn = new Button(getApplicationContext());
                    btn.setText("N/A");
                    mainLayout.addView(btn);
                    return;
                }
                // Iterate over itags
                for (int i = 0, itag; i < ytFiles.size(); i++) {
                    itag = ytFiles.keyAt(i);
                    // ytFile represents one file with its url and meta data
                    YtFile ytFile = ytFiles.get(itag);

                    // Just add videos in a decent format => height -1 = audio
                    if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 360) {
                        addButtonToMainLayout(vMeta.getTitle(), ytFile);
                    }
                }
            }
        }.extract(youtubeLink, parseDashManifest, includeWebM);
    }

    private void addButtonToMainLayout(final String videoTitle, final YtFile ytfile) {
        if (ytfile.getFormat().isDashContainer() && !downloadDash) {
            return;
        }

        // Display some buttons and let the user choose the format
        String btnText = (ytfile.getFormat().getHeight() == -1) ? "Audio " +
                ytfile.getFormat().getAudioBitrate() + " kbit/s" :
                ytfile.getFormat().getHeight() + "p";
        btnText += (ytfile.getFormat().isDashContainer()) ? " dash" : "";
        Button btn = new Button(this);
        btn.setText(btnText);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String filename;
                if (videoTitle.length() > 55) {
                    filename = videoTitle.substring(0, 55) + "." + ytfile.getFormat().getExt();
                } else {
                    filename = videoTitle + "." + ytfile.getFormat().getExt();
                }
                filename = filename.replaceAll("\\\\|>|<|\"|\\||\\*|\\?|%|:|#|/", "");
                downloadFromUrl(ytfile.getUrl(), videoTitle, filename);
                finish();
            }
        });
        mainLayout.addView(btn);
    }

    private void downloadFromUrl(String youtubeDlUrl, String downloadTitle, String fileName) {
        Uri uri = Uri.parse(youtubeDlUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(downloadTitle);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        if (TextUtils.isEmpty(dir)) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        } else {
            request.setDestinationInExternalPublicDir(dir, fileName);
        }
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}
