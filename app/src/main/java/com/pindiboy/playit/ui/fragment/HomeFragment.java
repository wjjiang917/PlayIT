package com.pindiboy.playit.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.pindiboy.playit.R;
import com.pindiboy.playit.model.bean.youtube.ItemId;
import com.pindiboy.playit.model.bean.youtube.YouTubeBean;
import com.pindiboy.playit.presenter.HomePresenter;
import com.pindiboy.playit.presenter.contract.HomeContract;
import com.pindiboy.playit.ui.BaseFragment;
import com.pindiboy.playit.ui.adapter.HomeVideosAdapter;
import com.pindiboy.playit.ui.adapter.HomeVideosWithItemAdapter;

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

        mCricketAdapter = new HomeVideosWithItemAdapter(null);
        rvCricket.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvCricket.setAdapter(mCricketAdapter);

        mBollywoodAdapter = new HomeVideosWithItemAdapter(null);
        rvBollywood.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvBollywood.setAdapter(mBollywoodAdapter);

        mTrailersAdapter = new HomeVideosWithItemAdapter(null);
        rvTrailers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvTrailers.setAdapter(mTrailersAdapter);

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
}
