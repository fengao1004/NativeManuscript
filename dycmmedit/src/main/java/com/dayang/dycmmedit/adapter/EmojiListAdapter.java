package com.dayang.dycmmedit.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.holder.EmojiListViewHolder;
import com.dayang.dycmmedit.utils.PublicResource;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/23.
 * e-mail 897840134@qq.com
 */

public class EmojiListAdapter extends RecyclerView.Adapter<EmojiListViewHolder> {
    Context context;
    int pagerPosition;
    List<Integer> drawableList;
    private final int windowWidth;
    ClickEmojiListener listener;

    public EmojiListAdapter(Context context, int pagerPosition, int with) {
        this.context = context;
        this.pagerPosition = pagerPosition;
        drawableList = PublicResource.getInstance().getDrawableList();
        windowWidth = with;
    }

    @Override
    public EmojiListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_emoji_image, parent, false);
        return new EmojiListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmojiListViewHolder holder, final int position) {
        ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
        layoutParams.width = windowWidth / 7;
        layoutParams.height = windowWidth / 7;
        holder.imageView.setLayoutParams(layoutParams);
        if (position != getItemCount() - 1) {
            if (pagerPosition * 20 + position < drawableList.size() - 1) {
                int drawable = drawableList.get(pagerPosition * 20 + position);
                ImageLoader.getInstance().displayImage("drawable://" + drawable, holder.imageView);
            }
        } else {
            ImageLoader.getInstance().displayImage("drawable://" + R.drawable.compose_emotion_delete_highlighted, holder.imageView);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int drawable;
                if (position != getItemCount() - 1) {
                    drawable = pagerPosition * 20 + position <= drawableList.size() - 1 ? drawableList.get(pagerPosition * 20 + position) : 0;
                } else {
                    drawable = R.drawable.compose_emotion_delete_highlighted;
                }
                if (listener != null) {
                    listener.onClick(drawable);
                }
            }
        });
    }


    public void setListener(ClickEmojiListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return 21;
    }

    public interface ClickEmojiListener {
        void onClick(int resource);
    }
}
