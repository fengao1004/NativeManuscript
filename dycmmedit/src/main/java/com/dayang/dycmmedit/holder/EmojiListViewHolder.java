package com.dayang.dycmmedit.holder;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.dayang.dycmmedit.R;

/**
 * Created by 冯傲 on 2017/5/23.
 * e-mail 897840134@qq.com
 */

public class EmojiListViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;

    public EmojiListViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.iv_emoji);
    }
}
