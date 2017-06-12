package com.dayang.dycmmedit.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dayang.dycmmedit.R;

import org.w3c.dom.Text;

/**
 * Created by 冯傲 on 2017/5/5.
 * e-mail 897840134@qq.com
 */

public class DialogCreateListHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public EditText edit;
    public Spinner spinner;
    public CheckBox checkBox;
    public View view;
    public LinearLayout ll_dialog_create_item_cb_edit;
    public LinearLayout ll_dialog_create_item_cb_spinner;
    public LinearLayout ll_normal;
    public TextView button_save;
    public ImageView iv_thumbnail;
    public RelativeLayout rl_thumbnail;
    public ImageView iv_del_thumbnail;

    public DialogCreateListHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        initView();
    }

    private void initView() {
        checkBox = (CheckBox) view.findViewById(R.id.dialog_create_item_cb_choose);
        iv_thumbnail = (ImageView) view.findViewById(R.id.iv_thumbnail);
        rl_thumbnail = (RelativeLayout) view.findViewById(R.id.rl_thumbnail);
        iv_del_thumbnail = (ImageView) view.findViewById(R.id.iv_del_thumbnail);
        edit = (EditText) view.findViewById(R.id.dialog_create_item_cb_edit);
        spinner = (Spinner) view.findViewById(R.id.dialog_create_item_cb_spinner);
        title = (TextView) view.findViewById(R.id.tv_item_title);
        ll_dialog_create_item_cb_edit = (LinearLayout) view.findViewById(R.id.ll_dialog_create_item_cb_edit);
        ll_dialog_create_item_cb_spinner = (LinearLayout) view.findViewById(R.id.ll_dialog_create_item_cb_spinner);
        ll_normal = (LinearLayout) view.findViewById(R.id.ll_normal);
        button_save = (TextView) view.findViewById(R.id.button_save);

    }
}
