package com.pindiboy.playit.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pindi.play.it.R;
import com.pindiboy.playit.model.bean.youtube.Item;
import com.pindiboy.playit.model.bean.youtube.ItemId;
import com.pindiboy.playit.model.bean.youtube.Thumbnail;
import com.pindiboy.playit.util.DateUtil;
import com.pindiboy.playit.util.ImageUtil;

import java.util.List;

/**
 * Created by Jiangwenjin on 2017/3/15.
 */

public class ChannelsAdapter extends BaseQuickAdapter<Item<ItemId>, BaseViewHolder> {
    public ChannelsAdapter(List<Item<ItemId>> data) {
        super(R.layout.item_channel_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Item<ItemId> item) {
        ImageUtil.load(mContext, item.getSnippet().getThumbnails().get(Thumbnail.TYPE_HIGH).getUrl(), helper.getView(R.id.channel_image));
        helper.setText(R.id.channel_title, item.getSnippet().getTitle())
                .setText(R.id.channel_info, DateUtil.getNewFormat(item.getSnippet().getPublishedAt(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd MMM, yyyy"));
    }
}
