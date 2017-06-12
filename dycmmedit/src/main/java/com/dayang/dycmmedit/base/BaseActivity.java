package com.dayang.dycmmedit.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.dayang.dycmmedit.R;
import com.elvishew.xlog.XLog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 冯傲 on 2017/4/26.
 * e-mail 897840134@qq.com
 */

public abstract class BaseActivity extends AppCompatActivity {
    public abstract void initView();

    public BaseFragment baseFragment;

    BackKeyListener backKeyListener;
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        setContentView(layoutId);
        bind = ButterKnife.bind(this);
        initView();
    }

    public abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    public abstract void showLoading();

    public abstract void removeLoading();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (backKeyListener == null || keyCode != KeyEvent.KEYCODE_BACK || backKeyListener.onBackKeyDown() == false) {
            return super.onKeyDown(keyCode, event);
        } else {
            return true;
        }
    }


    public void setBackKeyListener(BackKeyListener backKeyListener) {
        this.backKeyListener = backKeyListener;
    }

    public interface BackKeyListener {
        boolean onBackKeyDown();
    }
}
