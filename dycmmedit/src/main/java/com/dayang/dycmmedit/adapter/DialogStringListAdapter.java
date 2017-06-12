package com.dayang.dycmmedit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.holder.StringListHolder;

/**
 * Created by 冯傲 on 2017/4/28.
 * e-mail 897840134@qq.com
 */

public class DialogStringListAdapter extends RecyclerView.Adapter<StringListHolder> {
    String[] list;
    Context context;
    ItemClickListener itemClickListener;

    public DialogStringListAdapter(String[] list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public StringListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_string_list, parent, false);
        return new StringListHolder(view);
    }

    @Override
    public void onBindViewHolder(StringListHolder holder, final int position) {
        holder.textView.setText(list[position]);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemOnClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemOnClick(int position);
    }
}
