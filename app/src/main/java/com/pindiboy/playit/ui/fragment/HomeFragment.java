package com.pindiboy.playit.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.pindiboy.playit.R;
import com.pindiboy.playit.common.Constant;
import com.pindiboy.playit.model.bean.youtube.ItemId;
import com.pindiboy.playit.model.bean.youtube.Snippet;
import com.pindiboy.playit.model.bean.youtube.YouTubeBean;
import com.pindiboy.playit.presenter.HomePresenter;
import com.pindiboy.playit.presenter.contract.HomeContract;
import com.pindiboy.playit.ui.BaseFragment;
import com.pindiboy.playit.ui.activity.PlayerActivity;
import com.pindiboy.playit.ui.adapter.HomeVideosAdapter;
import com.pindiboy.playit.ui.adapter.HomeVideosWithItemAdapter;
import com.pindiboy.playit.util.TipUtil;

import butterknife.BindView;

/**
 * Created by Jiangwenjin on 2017/3/28.
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    private static String CHANNEL_BOLLYWOOD = "UC6OosMGOAm1_Xy3lmwzmO6w";
    private static String CHANNEL_TRAILERS = "UCi8e0iOVk1fEOogdfu4YgfA";
    private static String CHANNEL_SPORTS = "UCbtluQ_5OvCSvS1D24AlF_A";

    @BindView(R.id.rv_trending)
    RecyclerView rvTrending;
    @BindView(R.id.rv_cricket)
    RecyclerView rvCricket;
    @BindView(R.id.rv_bollywood)
    RecyclerView rvBollywood;
    @BindView(R.id.rv_trailers)
    RecyclerView rvTrailers;
    @BindView(R.id.pb_trending)
    ProgressBar pbTrending;
    @BindView(R.id.pb_cricket)
    ProgressBar pbCricket;
    @BindView(R.id.pb_bollywood)
    ProgressBar pbBollywood;
    @BindView(R.id.pb_trailers)
    ProgressBar pbTrailers;

    private HomeVideosAdapter mTrendingAdapter;
    private HomeVideosWithItemAdapter mCricketAdapter;
    private HomeVideosWithItemAdapter mBollywoodAdapter;
    private HomeVideosWithItemAdapter mTrailersAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void init() {
        mTrendingAdapter = new HomeVideosAdapter(null);
        rvTrending.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvTrending.setAdapter(mTrendingAdapter);
        rvTrending.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                openPlayer(view, mTrendingAdapter.getData().get(position).getSnippet().getVideoId());
            }
        });
        rvTrending.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.video_favorite_btn:
                        favorite(view, mTrendingAdapter.getData().get(position).getSnippet());
                        break;
                }
            }
        });

        mCricketAdapter = new HomeVideosWithItemAdapter(null);
        rvCricket.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvCricket.setAdapter(mCricketAdapter);
        rvCricket.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                openPlayer(view, mCricketAdapter.getData().get(position).getSnippet().getVideoId());
            }
        });
        rvCricket.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.video_favorite_btn:
                        favorite(view, mCricketAdapter.getData().get(position).getSnippet());
                        break;
                }
            }
        });

        mBollywoodAdapter = new HomeVideosWithItemAdapter(null);
        rvBollywood.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvBollywood.setAdapter(mBollywoodAdapter);
        rvBollywood.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                openPlayer(view, mBollywoodAdapter.getData().get(position).getSnippet().getVideoId());
            }
        });
        rvBollywood.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.video_favorite_btn:
                        favorite(view, mBollywoodAdapter.getData().get(position).getSnippet());
                        break;
                }
            }
        });

        mTrailersAdapter = new HomeVideosWithItemAdapter(null);
        rvTrailers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvTrailers.setAdapter(mTrailersAdapter);
        rvTrailers.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                openPlayer(view, mTrailersAdapter.getData().get(position).getSnippet().getVideoId());
            }
        });
        rvTrailers.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.video_favorite_btn:
                        favorite(view, mTrailersAdapter.getData().get(position).getSnippet());
                        break;
                }
            }
        });

        mPresenter.getTrending(); // trending
        mPresenter.getChannelVideos(CHANNEL_SPORTS); // cricket
        mPresenter.getChannelVideos(CHANNEL_BOLLYWOOD); // bollywood
        mPresenter.getChannelVideos(CHANNEL_TRAILERS); // trailers
    }

    @Override
    public void onTrendingLoaded(YouTubeBean<String> trendings) {
        pbTrending.setVisibility(View.GONE);
        mTrendingAdapter.setNewData(trendings.getItems());
    }

    @Override
    public void onChannelVideosLoaded(String channelId, YouTubeBean<ItemId> videos) {
        if (CHANNEL_BOLLYWOOD.equals(channelId)) {
            pbBollywood.setVisibility(View.GONE);
            mBollywoodAdapter.setNewData(videos.getItems());
        } else if (CHANNEL_TRAILERS.equals(channelId)) {
            pbTrailers.setVisibility(View.GONE);
            mTrailersAdapter.setNewData(videos.getItems());
        } else if (CHANNEL_SPORTS.equals(channelId)) {
            pbCricket.setVisibility(View.GONE);
            mCricketAdapter.setNewData(videos.getItems());
        }
    }

    private void openPlayer(View view, String videoId) {
        Intent intent = new Intent(mContext, PlayerActivity.class);
        intent.putExtra(Constant.INTENT_EXTRA_VIDEO_ID, videoId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, "video_item_view");
            mContext.startActivity(intent, options.toBundle());
        } else {
            mContext.startActivity(intent);
        }
    }

    private void favorite(View view, Snippet video) {
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
    }
}
