package com.dayang.dycmmedit.splash;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.base.BaseActivity;
import com.dayang.login.LoginActivity;
import com.dayang.login.LoginManger;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.image_splash)
    ImageView splashImageView;
    int ENTERMAIN = 123;
    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == ENTERMAIN) {
                //TODO 进入主页面
                LoginManger.getInstance().login(null, SplashActivity.this);
            }
        }
    };

    @Override
    public void initView() {
        String imageUri = "drawable://" + R.drawable.start;
        ImageLoader.getInstance().displayImage(imageUri, splashImageView);
        handle.sendEmptyMessageDelayed(ENTERMAIN, 1000 * 2);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void removeLoading() {
    }
}
