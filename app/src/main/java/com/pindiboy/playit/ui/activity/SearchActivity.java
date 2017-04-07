package com.pindiboy.playit.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pindiboy.playit.R;
import com.pindiboy.playit.common.Constant;
import com.pindiboy.playit.model.bean.youtube.ItemId;
import com.pindiboy.playit.model.bean.youtube.Snippet;
import com.pindiboy.playit.model.bean.youtube.YouTubeBean;
import com.pindiboy.playit.presenter.SearchPresenter;
import com.pindiboy.playit.presenter.contract.SearchContract;
import com.pindiboy.playit.ui.BaseActivity;
import com.pindiboy.playit.ui.adapter.VideosAdapter;
import com.pindiboy.playit.util.TipUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jiangwenjin on 2017/3/22.
 */

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {
    @BindView(R.id.rv_video_list)
    RecyclerView rvVideoList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.pb_video_list)
    ProgressBar progressBar;
    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;

    private String q;
    private VideosAdapter mAdapter;
    private boolean loadMore = false;
    private String pageToken = ""; // for pagination

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void init() {
        q = getIntent().getStringExtra(Constant.INTENT_EXTRA_SEARCH_Q);
        mSearchView.showSearch(false);
        mSearchView.setQuery(q, false);
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    return true;
                }

                q = query;
                mPresenter.search(q, "");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mAdapter = new VideosAdapter(null);
        rvVideoList.setLayoutManager(new LinearLayoutManager(mContext));
        rvVideoList.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.empty_view, rvVideoList);

        rvVideoList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra(Constant.INTENT_EXTRA_VIDEO_ID, mAdapter.getData().get(position).getSnippet().getVideoId());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mContext, view, "video_item_view");
                    mContext.startActivity(intent, options.toBundle());
                } else {
                    mContext.startActivity(intent);
                }
            }
        });
        rvVideoList.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.video_favorite_btn:
                        Snippet video = mAdapter.getData().get(position).getSnippet();
                        if (video.isFavourite()) {
                            video.setFavourite(false);
                            mPresenter.removeFavorite(video.getVideoId());
                            ((ImageView) view).setImageResource(R.drawable.ic_favorite_border_red_24dp);
                            TipUtil.showToast(mContext, getString(R.string.removed_favorite));
                        } else {
                            video.setFavourite(true);
                            mPresenter.addFavorite(video);
                            ((ImageView) view).setImageResource(R.drawable.ic_favorite_red_24dp);
                            TipUtil.showToast(mContext, getString(R.string.added_favorite));
                        }
                        break;
                }
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore = true;
                mPresenter.search(q, pageToken);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMore = false;
                pageToken = "";
                mPresenter.search(q, pageToken);
            }
        });

        mPresenter.search(q, "");
    }

    @Override
    public void onSearchLoaded(YouTubeBean<ItemId> youTubeBean) {
        mAdapter.loadMoreComplete();
        progressBar.setVisibility(View.GONE);
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }

        // disable load more
        if (null == youTubeBean.getItems() || youTubeBean.getItems().size() < Constant.CHANNEL_VIDEOS_PAGE_SIZE) {
            mAdapter.loadMoreEnd();
        }

        pageToken = youTubeBean.getNextPageToken();
        if (loadMore) {
            mAdapter.addData(youTubeBean.getItems());
        } else {
            mAdapter.setNewData(youTubeBean.getItems());
        }
    }

    @OnClick(com.miguelcatalan.materialsearchview.R.id.action_up_btn)
    public void clickBack(View view) {
        finish();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
    }
}
