package com.pindiboy.playit.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.pindiboy.playit.R;
import com.pindiboy.playit.ui.SimpleFragment;
import com.pindiboy.playit.ui.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Jiangwenjin on 2017/3/28.
 */

public class MainFragment extends SimpleFragment {
    private final String[] HOME_TITLE = new String[]{"Home", "Featured"};

    @BindView(R.id.tab_home)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager_home)
    ViewPager mViewPager;

    private ViewPagerAdapter mAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void init() {
        fragments.add(new HomeFragment());
        fragments.add(new MostViewedChannelsFragment());

        mAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);

        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(HOME_TITLE[0]);
        mTabLayout.getTabAt(1).setText(HOME_TITLE[1]);
    }
}
