package com.pindiboy.playit.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pindiboy.playit.App;
import com.pindiboy.playit.R;
import com.pindiboy.playit.common.Constant;
import com.pindiboy.playit.presenter.MainPresenter;
import com.pindiboy.playit.presenter.contract.MainContract;
import com.pindiboy.playit.ui.BaseActivity;
import com.pindiboy.playit.ui.fragment.AboutFragment;
import com.pindiboy.playit.ui.fragment.FavoriteFragment;
import com.pindiboy.playit.ui.fragment.MainFragment;
import com.pindiboy.playit.util.SPUtil;

import javax.inject.Inject;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.view_search)
    MaterialSearchView mSearchView;

    @Inject
    MainFragment mainFragment;

    @Inject
    FavoriteFragment favoriteFragment;

    @Inject
    AboutFragment aboutFragment;

    private int hideFragment = Constant.TYPE_HOME;
    private int showFragment = Constant.TYPE_HOME;

    private ActionBarDrawerToggle mDrawerToggle;
    private MenuItem mLastMenuItem;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void init() {
        setToolBar(mToolbar, getString(R.string.app_name));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mLastMenuItem = mNavigationView.getMenu().findItem(R.id.nav_home);
        // load fragments
        loadMultipleRootFragment(R.id.layout_main_content, 0, mainFragment, favoriteFragment, aboutFragment);

        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    showFragment = Constant.TYPE_HOME;
                    break;
                case R.id.nav_favorite:
                    showFragment = Constant.TYPE_FAVORITES;
                    break;
                case R.id.nav_about:
                    showFragment = Constant.TYPE_ABOUT;
                    break;
            }
            if (mLastMenuItem != null) {
                mLastMenuItem.setChecked(false);
            }
            mLastMenuItem = item;
            SPUtil.setCurrentPage(showFragment);
            item.setChecked(true);
            setTitle();
            mDrawerLayout.closeDrawers();
            showHideFragment(getFragment(showFragment), getFragment(hideFragment));
            hideFragment = showFragment;
            return false;
        });

        // home page
        showFragment = SPUtil.getCurrentPage();
        hideFragment = Constant.TYPE_HOME;
        showHideFragment(getFragment(showFragment), getFragment(hideFragment));
        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        setTitle();
        hideFragment = showFragment;

        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    return true;
                }
                mSearchView.closeSearch();
                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.putExtra(Constant.INTENT_EXTRA_SEARCH_Q, query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressedSupport() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.app_name))
                    .setMessage(getString(R.string.exit_message))
                    .setNegativeButton(R.string.cancel, null)
                    .setPositiveButton(R.string.sure, (dialogInterface, i) -> App.getInstance().exitApp());
            builder.show();
        }
    }

    private SupportFragment getFragment(int pageType) {
        switch (pageType) {
            case Constant.TYPE_HOME:
                return mainFragment;
            case Constant.TYPE_FAVORITES:
                return favoriteFragment;
            case Constant.TYPE_ABOUT:
                return aboutFragment;
        }
        return mainFragment;
    }

    private int getCurrentResId(int pageType) {
        switch (pageType) {
            case Constant.TYPE_HOME:
                return R.id.nav_home;
            case Constant.TYPE_FAVORITES:
                return R.id.nav_favorite;
            case Constant.TYPE_ABOUT:
                return R.id.nav_about;
        }
        return R.id.nav_home;
    }

    private void setTitle() {
        switch (SPUtil.getCurrentPage()) {
            case Constant.TYPE_HOME:
                mToolbar.setTitle(getString(R.string.app_name));
                break;
            default:
                mToolbar.setTitle(mNavigationView.getMenu().findItem(getCurrentResId(SPUtil.getCurrentPage())).getTitle());
                break;
        }
    }
}
