package com.pindiboy.playit.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.pindi.play.it.BuildConfig;
import com.pindi.play.it.R;
import com.pindiboy.playit.ui.SimpleFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by codeest on 16/8/23.
 */

public class AboutFragment extends SimpleFragment {
    @BindView(R.id.app_desc)
    TextView appDesc;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void init() {
        appDesc.setText(getString(R.string.app_name) + "    (" + BuildConfig.VERSION_NAME + ")");
    }

    @OnClick(R.id.app_desc)
    public void clickAbout(View view) {
        Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + mContext.getPackageName())));
        }
    }
}
