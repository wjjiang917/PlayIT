package com.pindiboy.playit.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pindiboy.playit.R;
import com.pindiboy.playit.model.bean.youtube.Snippet;
import com.pindiboy.playit.util.DateUtil;
import com.pindiboy.playit.util.ImageUtil;

import java.util.List;

/**
 * Created by Jiangwenjin on 2017/3/15.
 */

public class FavoriteAdapter extends BaseItemDraggableAdapter<Snippet, BaseViewHolder> {
    public FavoriteAdapter(List<Snippet> data) {
        super(R.layout.item_video_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Snippet item) {
        ImageUtil.load(mContext, item.getThumbnail(), (ImageView) helper.getView(R.id.video_image));
        if (item.isFavourite()) {
            helper.setImageResource(R.id.video_favorite_btn, R.drawable.ic_favorite_red_24dp);
        } else {
            helper.setImageResource(R.id.video_favorite_btn, R.drawable.ic_favorite_border_red_24dp);
        }
        helper.setText(R.id.video_title, item.getTitle())
                .setText(R.id.video_info, DateUtil.getNewFormat(item.getPublishedAt(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd MMM, yyyy"))
                .addOnClickListener(R.id.video_favorite_btn);
    }
}
