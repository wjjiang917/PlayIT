package com.pindiboy.playit.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pindi.playkar.R;
import com.pindiboy.playit.common.Constant;
import com.pindiboy.playit.model.bean.youtube.ItemId;
import com.pindiboy.playit.model.bean.youtube.Thumbnail;
import com.pindiboy.playit.model.bean.youtube.YouTubeBean;
import com.pindiboy.playit.presenter.MostViewedChannelsPresenter;
import com.pindiboy.playit.presenter.contract.MostViewedChannelsContract;
import com.pindiboy.playit.ui.BaseFragment;
import com.pindiboy.playit.ui.activity.ChannelDetailActivity;
import com.pindiboy.playit.ui.adapter.ChannelsAdapter;

import butterknife.BindView;

/**
 * Created by Jiangwenjin on 2017/3/14.
 */

public class MostViewedChannelsFragment extends BaseFragment<MostViewedChannelsPresenter> implements MostViewedChannelsContract.View {
    @BindView(R.id.rv_channel_list)
    RecyclerView rvChannelList;
    @BindView(R.id.pb_channel_list)
    ProgressBar progressBar;

    private ChannelsAdapter mAdapter;
    private boolean loadMore = false;
    private String pageToken = ""; // for pagination

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_channel_list;
    }

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void init() {
        mAdapter = new ChannelsAdapter(null);
        rvChannelList.setLayoutManager(new LinearLayoutManager(mContext));
        rvChannelList.setAdapter(mAdapter);

        rvChannelList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, ChannelDetailActivity.class);
                intent.putExtra(Constant.INTENT_EXTRA_CHANNEL_ID, mAdapter.getData().get(position).getSnippet().getChannelId());
                intent.putExtra(Constant.INTENT_EXTRA_CHANNEL_TITLE, mAdapter.getData().get(position).getSnippet().getChannelTitle());
                intent.putExtra(Constant.INTENT_EXTRA_CHANNEL_THUMBNAIL, mAdapter.getData().get(position).getSnippet().getThumbnails().get(Thumbnail.TYPE_HIGH).getUrl());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, "channel_item_view");
                    mContext.startActivity(intent, options.toBundle());
                } else {
                    mContext.startActivity(intent);
                }
            }
        });

        mAdapter.setOnLoadMoreListener(() -> {
            loadMore = true;
            mPresenter.getMostViewedChannels(pageToken);
        });

        mPresenter.getMostViewedChannels("");
    }

    @Override
    public void onChannelsLoaded(YouTubeBean<ItemId> channels) {
        mAdapter.loadMoreComplete();
        progressBar.setVisibility(View.GONE);

        // disable load more
        if (null == channels.getItems() || channels.getItems().size() < Constant.MOST_VIEWED_CHANNELS_PAGE_SIZE) {
            mAdapter.loadMoreEnd();
        }

        pageToken = channels.getNextPageToken();
        if (loadMore) {
            mAdapter.addData(channels.getItems());
        } else {
            mAdapter.setNewData(channels.getItems());
        }
    }
}
