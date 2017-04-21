package com.pindiboy.playit.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.pindi.playkar.R;
import com.pindiboy.playit.model.bean.IpInfo;
import com.pindiboy.playit.presenter.SplashPresenter;
import com.pindiboy.playit.presenter.contract.SplashContract;
import com.pindiboy.playit.ui.BaseActivity;
import com.pindiboy.playit.util.SPUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

/**
 * Created by Jiangwenjin on 2017/3/24.
 */

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void init() {
        // check storage permission
        mPresenter.checkPermissions(new RxPermissions(this));

        // get country code
        onIpInfoLoaded(null); // set default value
        mPresenter.getIpInfo();
    }

    @Override
    public void onIpInfoLoaded(IpInfo ipInfo) {
        String countryCode;
        if (null != ipInfo && !TextUtils.isEmpty(ipInfo.getCountryCode())) {
            countryCode = ipInfo.getCountryCode();
        } else {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            countryCode = tm.getNetworkCountryIso();
        }

        SPUtil.setCountryCode(countryCode.toUpperCase());
    }

    @Override
    public void onCountDownEnd() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
