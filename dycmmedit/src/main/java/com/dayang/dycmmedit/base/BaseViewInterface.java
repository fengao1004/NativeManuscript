package com.dayang.dycmmedit.base;

import android.app.Activity;

/**
 * Created by 冯傲 on 2017/5/2.
 * e-mail 897840134@qq.com
 */

public interface BaseViewInterface {
    Activity getViewActivity();

    void showWaiting(String text);

    void removeWaiting();

    void makeToast(String text);

}
