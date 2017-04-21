package com.pindiboy.playit.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pindi.playkar.R;
import com.pindiboy.playit.common.Constant;
import com.pindiboy.playit.model.bean.youtube.ItemId;
import com.pindiboy.playit.model.bean.youtube.Snippet;
import com.pindiboy.playit.model.bean.youtube.YouTubeBean;
import com.pindiboy.playit.presenter.ChannelPresenter;
import com.pindiboy.playit.presenter.contract.ChannelContract;
import com.pindiboy.playit.ui.BaseFragment;
import com.pindiboy.playit.ui.activity.PlayerActivity;
import com.pindiboy.playit.ui.adapter.VideosAdapter;
import com.pindiboy.playit.util.TipUtil;

import butterknife.BindView;

/**
 * Created by Jiangwenjin on 2017/3/14.
 */

public class ChannelFragment extends BaseFragment<ChannelPresenter> implements ChannelContract.View {
    @BindView(R.id.rv_video_list)
    RecyclerView rvVideoList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.pb_video_list)
    ProgressBar progressBar;

    private String channelId;
    private VideosAdapter mAdapter;
    private boolean loadMore = false;
    private String pageToken = ""; // for pagination

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_video_list;
    }

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void init() {
        if (getArguments() != null) {
            channelId = getArguments().getString(Constant.BUNDLE_CHANNEL_ID);
        }

        mAdapter = new VideosAdapter(null);
        rvVideoList.setLayoutManager(new LinearLayoutManager(mContext));
        rvVideoList.setAdapter(mAdapter);

        rvVideoList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra(Constant.INTENT_EXTRA_VIDEO_ID, mAdapter.getData().get(position).getSnippet().getVideoId());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, "video_item_view");
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
                            TipUtil.showToast(mActivity, getString(R.string.removed_favorite));
                        } else {
                            video.setFavourite(true);
                            mPresenter.addFavorite(video);
                            ((ImageView) view).setImageResource(R.drawable.ic_favorite_red_24dp);
                            TipUtil.showToast(mActivity, getString(R.string.added_favorite));
                        }
                        break;
                }
            }
        });

        mAdapter.setOnLoadMoreListener(() -> {
            loadMore = true;
            mPresenter.getChannelVideos(channelId, pageToken);
        });

        swipeRefresh.setOnRefreshListener(() -> {
            loadMore = false;
            mPresenter.getChannelVideos(channelId, "");
        });

        mPresenter.getChannelVideos(channelId, "");
    }

    @Override
    public void onChannelVideosLoaded(YouTubeBean<ItemId> youTubeBean) {
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
}
