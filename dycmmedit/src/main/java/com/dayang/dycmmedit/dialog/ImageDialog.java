package com.dayang.dycmmedit.dialog;

import android.content.Context;
import android.widget.ImageView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dayang.dycmmedit.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by 冯傲 on 2017/5/22.
 * e-mail 897840134@qq.com
 */

public class ImageDialog {
    Context context;
    String path;
    private MaterialDialog createDialog;
    private ImageView iv_image_dialog;

    public ImageDialog(Context context, String path) {
        this.context = context;
        this.path = path;
    }

    public MaterialDialog build() {
        createDialog = new MaterialDialog.Builder(context)
                .title("缩略图")
                .titleGravity(GravityEnum.START)
                .customView(R.layout.dialog_image, true)
                .positiveText("确定")
                .positiveColor(context.getResources().getColor(R.color.colorDYBlue))
                .cancelable(true)
                .build();
        initViews();
        return createDialog;
    }

    private void initViews() {
        iv_image_dialog = (ImageView) createDialog.getCustomView().findViewById(R.id.iv_image_dialog);
        ImageLoader.getInstance().displayImage(path, iv_image_dialog);
    }
}
