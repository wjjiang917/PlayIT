package com.pindiboy.playit.ui.adapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pindi.playkar.R;
import com.pindiboy.playit.model.bean.youtube.Item;
import com.pindiboy.playit.model.bean.youtube.ItemId;
import com.pindiboy.playit.util.DateUtil;
import com.pindiboy.playit.util.ImageUtil;

import java.util.List;

/**
 * Created by Jiangwenjin on 2017/3/15.
 */

public class HomeVideosWithItemAdapter extends BaseItemDraggableAdapter<Item<ItemId>, BaseViewHolder> {
    public HomeVideosWithItemAdapter(List<Item<ItemId>> data) {
        super(R.layout.item_video_home, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Item<ItemId> item) {
        ImageUtil.load(mContext, item.getSnippet().getThumbnail(), helper.getView(R.id.video_image));
        if (item.getSnippet().isFavourite()) {
            helper.setImageResource(R.id.video_favorite_btn, R.drawable.ic_favorite_red_24dp);
        } else {
            helper.setImageResource(R.id.video_favorite_btn, R.drawable.ic_favorite_border_red_24dp);
        }
        helper.setText(R.id.video_title, item.getSnippet().getTitle())
                .setText(R.id.video_info, DateUtil.getNewFormat(item.getSnippet().getPublishedAt(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd MMM, yyyy"))
                .addOnClickListener(R.id.video_favorite_btn);
    }
}
