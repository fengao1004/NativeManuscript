package com.dayang.dycmmedit.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dayang.dycmmedit.R;

/**
 * Created by 冯傲 on 2017/5/12.
 * e-mail 897840134@qq.com
 */

public class WechatListHolder extends RecyclerView.ViewHolder {

    public LinearLayout ll_wechat_list_root;
    public LinearLayout ll_child;
    public RelativeLayout rl_father;
    public ImageView iv_father_image;
    public TextView tv_father_header;
    public ImageView iv_child_image;
    public TextView tv_child_header;
    public ImageView iv_delete_wechat;
    public ImageView iv_line_wechat;
    public ImageView iv_wechat_add;
    public TextView tv_child_time;
    public TextView tv_child_username;

    public WechatListHolder(View itemView) {
        super(itemView);
        ll_wechat_list_root = (LinearLayout) itemView.findViewById(R.id.ll_wechat_list_root);
        ll_child = (LinearLayout) itemView.findViewById(R.id.ll_child);
        rl_father = (RelativeLayout) itemView.findViewById(R.id.rl_father);
        iv_father_image = (ImageView) itemView.findViewById(R.id.iv_father_image);
        tv_father_header = (TextView) itemView.findViewById(R.id.tv_father_header);
        iv_child_image = (ImageView) itemView.findViewById(R.id.iv_child_image);
        tv_child_header = (TextView) itemView.findViewById(R.id.tv_child_header);
        tv_child_time = (TextView) itemView.findViewById(R.id.tv_child_time);
        tv_child_username = (TextView) itemView.findViewById(R.id.tv_child_username);
        iv_delete_wechat = (ImageView) itemView.findViewById(R.id.iv_delete_wechat);
        iv_line_wechat = (ImageView) itemView.findViewById(R.id.iv_line_wechat);
        iv_wechat_add = (ImageView) itemView.findViewById(R.id.iv_wechat_add);

    }
}
