package com.dayang.dycmmedit.main.view;

import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Point;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.base.BaseActivity;
import com.dayang.dycmmedit.utils.StatusBarUtil;
import com.elvishew.xlog.XLog;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.root_view)
    FrameLayout rootView;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    private MainFragment mainFragment;
    private DrawerLayout drawer;
    private View headerView;
    private int height;
    private LinearLayout ll_about;
    private LinearLayout ll_all;
    private LinearLayout ll_shenhe;
    private LinearLayout ll_my_gaojian;

    @Override
    public void initView() {
        XLog.i("initView: " + this);
//        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#ffffff"), false);
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        height = point.y;
        StatusBarUtil.setStatusBarLightMode(this, Color.parseColor("#ffffff"));
        mainFragment = MainFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.root_view, mainFragment)
                .show(mainFragment).commit();
        IntentFilter filter = new IntentFilter();
        headerView = mNavigationView.getHeaderView(0);
        XLog.i("count: " + mNavigationView.getChildCount());
        headerView.setMinimumHeight(height);
        ll_about = (LinearLayout) headerView.findViewById(R.id.ll_about);
        ll_my_gaojian = (LinearLayout) headerView.findViewById(R.id.ll_my_gaojian);
        ll_shenhe = (LinearLayout) headerView.findViewById(R.id.ll_shenhe);
        ll_all = (LinearLayout) headerView.findViewById(R.id.ll_all);
        ll_about.setOnClickListener(this);
        ll_my_gaojian.setOnClickListener(this);
        ll_shenhe.setOnClickListener(this);
        ll_all.setOnClickListener(this);

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void removeLoading() {

    }

    public void initDrawerLayout(Toolbar toolbar) {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public View getUserView() {
        return mNavigationView.getHeaderView(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_about:
                //TODO 进入关于
                drawer.closeDrawers();
                break;
            case R.id.ll_my_gaojian:
                mainFragment.setResourceType("1");
                drawer.closeDrawers();
                break;
            case R.id.ll_all:
                mainFragment.setResourceType("0");
                drawer.closeDrawers();
                break;
            case R.id.ll_shenhe:
                mainFragment.setResourceType("8");
                drawer.closeDrawers();
                break;
        }
    }
}
