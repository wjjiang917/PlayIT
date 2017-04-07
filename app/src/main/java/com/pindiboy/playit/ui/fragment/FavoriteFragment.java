package com.pindiboy.playit.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.pindiboy.playit.R;
import com.pindiboy.playit.common.Constant;
import com.pindiboy.playit.model.bean.youtube.Snippet;
import com.pindiboy.playit.presenter.FavoritePresenter;
import com.pindiboy.playit.presenter.contract.FavoriteContract;
import com.pindiboy.playit.ui.BaseFragment;
import com.pindiboy.playit.ui.activity.PlayerActivity;
import com.pindiboy.playit.ui.adapter.FavoriteAdapter;
import com.pindiboy.playit.util.TipUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Jiangwenjin on 2017/3/14.
 */

public class FavoriteFragment extends BaseFragment<FavoritePresenter> implements FavoriteContract.View {
    @BindView(R.id.rv_video_list)
    RecyclerView rvVideoList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.pb_video_list)
    ProgressBar progressBar;

    private FavoriteAdapter mAdapter;

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
        mAdapter = new FavoriteAdapter(null);
        rvVideoList.setLayoutManager(new LinearLayoutManager(mContext));
        rvVideoList.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.empty_view, rvVideoList);

        rvVideoList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra(Constant.INTENT_EXTRA_VIDEO_ID, mAdapter.getData().get(position).getVideoId());
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
                        Snippet video = mAdapter.getData().get(position);
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

        swipeRefresh.setOnRefreshListener(() -> mPresenter.getFavorite());

        // swipe and drag
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(rvVideoList);

        // open drag
        mAdapter.enableDragItem(itemTouchHelper, R.id.video_list_item, true);
        mAdapter.setOnItemDragListener(onItemDragListener);

        mPresenter.getFavorite();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        progressBar.setVisibility(View.VISIBLE);
        mPresenter.getFavorite();
    }

    @Override
    public void onFavoriteLoaded(List<Snippet> videos) {
        progressBar.setVisibility(View.GONE);
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }

        mAdapter.setNewData(videos);
    }

    OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            // change video order
            Snippet video = mAdapter.getData().get(pos);
            double order = 1.0;
            if (pos == 0) { // move to first
                order += mAdapter.getData().get(1).getOrder();
            } else if (pos == mAdapter.getData().size() - 1) {  // move to last one
                double prevOrder = mAdapter.getData().get(pos - 1).getOrder();
                order = (prevOrder + 0.0) / 2.0;
            } else { //
                double prevOrder = mAdapter.getData().get(pos - 1).getOrder();
                double nextOrder = mAdapter.getData().get(pos + 1).getOrder();
                order = (prevOrder + nextOrder) / 2.0;
            }
            video.setOrder(order);
            mPresenter.updateFavorite(video.getVideoId(), order);
        }
    };
}
