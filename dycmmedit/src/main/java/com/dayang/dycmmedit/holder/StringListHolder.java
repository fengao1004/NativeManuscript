package com.dayang.dycmmedit.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dayang.dycmmedit.R;

/**
 * Created by 冯傲 on 2017/4/28.
 * e-mail 897840134@qq.com
 */

public class StringListHolder extends RecyclerView.ViewHolder {
    View view;
    public TextView textView;

    public StringListHolder(View itemView) {
        super(itemView);
        view = itemView;
        textView = (TextView) itemView.findViewById(R.id.tv_item_string_list);
    }
}
