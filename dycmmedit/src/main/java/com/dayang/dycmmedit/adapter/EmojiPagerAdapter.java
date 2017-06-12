package com.dayang.dycmmedit.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.utils.PublicResource;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/23.
 * e-mail 897840134@qq.com
 */

public class EmojiPagerAdapter extends PagerAdapter {
    Context context;
    List<View> list;
    int width;

    public void setListenr(EmojiListAdapter.ClickEmojiListener listenr) {
        this.listenr = listenr;
    }

    private EmojiListAdapter.ClickEmojiListener listenr;

    public EmojiPagerAdapter(Context context, List<View> list, int width) {
        this.context = context;
        this.list = list;
        this.width = width;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(list.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        RecyclerView pager_emoji = (RecyclerView) list.get(position).findViewById(R.id.pager_emoji);
        pager_emoji.setLayoutManager(new GridLayoutManager(context, 7));
        EmojiListAdapter emojiListAdapter = new EmojiListAdapter(context, position, width);
        emojiListAdapter.setListener(listenr);
        pager_emoji.setAdapter(emojiListAdapter);
        view.addView(list.get(position));
        return list.get(position);
    }
}
