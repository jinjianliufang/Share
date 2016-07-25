package com.hengye.share.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hengye.share.R;
import com.hengye.share.ui.widget.image.SuperImageView;

import java.util.List;

public class TopicAlbumAdapter extends CommonAdapter<String, TopicAlbumAdapter.TopicAlbumViewHolder> {

    public TopicAlbumAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public TopicAlbumViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        return new TopicAlbumViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_topic_album, parent, false));
    }

    public static class TopicAlbumViewHolder extends CommonAdapter.ItemViewHolder<String> {

        SuperImageView superImageView;

        public TopicAlbumViewHolder(View v) {
            super(v);
            superImageView = (SuperImageView) findViewById(R.id.image);
        }

        @Override
        public void bindData(Context context, String string, int position) {
            superImageView.setImageUrl(string);
        }
    }
}
