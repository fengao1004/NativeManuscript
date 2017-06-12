package com.dayang.dycmmedit.holder;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.adapter.ManuscriptListAdapter;

/**
 * Created by 冯傲 on 2017/4/27.
 * e-mail 897840134@qq.com
 */

public class ManuscriptListViewHolder extends RecyclerView.ViewHolder {
    public ImageView iv_icon;
    public TextView title;
    public TextView time;
    public TextView name;
    public TextView status;
    public TextView loadMore;

    public ManuscriptListViewHolder(View view, int type) {
        super(view);
        if (type == ManuscriptListAdapter.ITEM_TYPE_NORMAL) {
            status = (TextView) view.findViewById(R.id.tv_manuscript_status);
            iv_icon = (ImageView) view.findViewById(R.id.iv_manuscript_type_icon);
            title = (TextView) view.findViewById(R.id.tv_manuscript_title);
            time = (TextView) view.findViewById(R.id.tv_manuscript_time);
            name = (TextView) view.findViewById(R.id.tv_manuscript_name);
        } else if (type == ManuscriptListAdapter.ITEM_TYPE_LAST) {
            loadMore = (TextView) view.findViewById(R.id.tv_load_more);
        }

    }


}
