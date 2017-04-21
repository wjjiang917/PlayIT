package com.pindiboy.playit.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pindi.play.it.R;
import com.pindiboy.playit.common.Constant;
import com.pindiboy.playit.model.bean.youtube.ItemId;
import com.pindiboy.playit.model.bean.youtube.Snippet;
import com.pindiboy.playit.model.bean.youtube.YouTubeBean;
import com.pindiboy.playit.presenter.ChannelPresenter;
import com.pindiboy.playit.presenter.contract.ChannelContract;
import com.pindiboy.playit.ui.BaseActivity;
import com.pindiboy.playit.ui.adapter.VideosAdapter;
import com.pindiboy.playit.util.ImageUtil;
import com.pindiboy.playit.util.TipUtil;

import butterknife.BindView;

/**
 * Created by Jiangwenjin on 2017/4/13.
 */

public class ChannelDetailActivity extends BaseActivity<ChannelPresenter> implements ChannelContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.rv_video_list)
    RecyclerView rvVideoList;
    @BindView(R.id.pb_video_list)
    ProgressBar pbVideoList;
    @BindView(R.id.channel_image)
    ImageView ivThumbnail;

    private String channelId;
    private String channelTitle;
    private String channelThumbnail;
    private VideosAdapter mAdapter;
    private boolean loadMore = false;
    private String pageToken = ""; // for pagination

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_channel_detail;
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void init() {
        channelId = getIntent().getStringExtra(Constant.INTENT_EXTRA_CHANNEL_ID);
        channelTitle = getIntent().getStringExtra(Constant.INTENT_EXTRA_CHANNEL_TITLE);
        channelThumbnail = getIntent().getStringExtra(Constant.INTENT_EXTRA_CHANNEL_THUMBNAIL);

        setToolBar(toolbar, channelTitle);
        ImageUtil.load(mContext, channelThumbnail, ivThumbnail);

        mAdapter = new VideosAdapter(null);
        rvVideoList.setLayoutManager(new LinearLayoutManager(mContext));
        rvVideoList.setAdapter(mAdapter);

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

        mAdapter.setOnLoadMoreListener(() -> {
            loadMore = true;
            mPresenter.getChannelVideos(channelId, pageToken);
        });

        mPresenter.getChannelVideos(channelId, "");
    }

    @Override
    public void onChannelVideosLoaded(YouTubeBean<ItemId> youTubeBean) {
        mAdapter.loadMoreComplete();
        pbVideoList.setVisibility(View.GONE);

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
}
