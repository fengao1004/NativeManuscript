package com.dayang.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

/**
 * Created by 冯傲 on 2017/4/26.
 * e-mail 897840134@qq.com
 */

public class LoginManger {
    static LoginManger loginManger;
    public static  String USERINFO = "USERINFO";

    private LoginManger() {

    }

    public static LoginManger getInstance() {
        if (loginManger == null) {
            loginManger = new LoginManger();
        }
        return loginManger;
    }

    public void login(Class clazz, Activity activity) {
        boolean alreadyLogin = alreadyLogin(activity);
        if (alreadyLogin) {
            activity.startActivity(new Intent(activity, clazz));
            activity.finish();
        } else {
            Intent intent1 = new Intent(activity, LoginActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("aimActivityClass", clazz);
            intent1.putExtras(bundle);
            activity.startActivity(intent1);
        }
    }

    private boolean alreadyLogin(Context context) {
        String param = SharedPreferencesUtils.getParam(context, USERINFO, "");
        boolean empty = TextUtils.isEmpty(param);
        return !empty;
    }

}
