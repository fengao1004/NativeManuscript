package com.dayang.dycmmedit.info;

import java.io.Serializable;

/**
 * Created by 冯傲 on 2017/5/5.
 * e-mail 897840134@qq.com
 */

public class CreateDialogItemInfo implements Serializable {
    public static final int CREATE_DIALOG_ITEM_TYPE_INPUT = 0;
    public static final int CREATE_DIALOG_ITEM_TYPE_PULL_LIST = 1;
    public static final int CREATE_DIALOG_ITEM_TYPE_CHECKBOX = 2;
    public String title;
    public int type;

    public CreateDialogItemInfo(String title, int type) {
        this.title = title;
        this.type = type;
    }


}
