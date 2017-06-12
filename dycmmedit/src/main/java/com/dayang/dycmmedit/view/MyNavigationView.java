package com.dayang.dycmmedit.view;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 冯傲 on 2017/6/5.
 * e-mail 897840134@qq.com
 */

public class MyNavigationView extends NavigationView {
    private float x;
    private float y;

    public MyNavigationView(Context context) {
        super(context);
    }

    public MyNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = super.onInterceptTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = ev.getX();
                y = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                return true;
        }
        return b;
    }
}
