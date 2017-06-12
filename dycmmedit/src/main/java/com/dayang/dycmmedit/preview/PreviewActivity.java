package com.dayang.dycmmedit.preview;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.utils.PublicResource;
import com.dayang.dycmmedit.utils.StatusBarUtil;
import com.dayang.richeditor.RichEditor;
import com.elvishew.xlog.XLog;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class PreviewActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private RichEditor mTVNewsContent;
    private ManuscriptListInfo info;
    private ImageView ivImage;
    private CollapsingToolbarLayout collapsingToolbar1;
    private AppBarLayout bar_layout;
    private TextView tv_title;
    private Toolbar toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        info = (ManuscriptListInfo) getIntent().getSerializableExtra("info");
        ivImage = (ImageView) findViewById(R.id.ivImage);
        tv_title = (TextView) findViewById(R.id.tv_title);
        toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        bar_layout = (AppBarLayout) findViewById(R.id.bar_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        StatusBarUtil.setStatusBarLightMode(this, Color.WHITE);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTVNewsContent = (RichEditor) findViewById(R.id.htNewsContent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//此FLAG可使状态栏透明，且当前视图在绘制时，从屏幕顶端开始即top = 0开始绘制，这也是实现沉浸效果的基础
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//可不加
        }

        String streamPath = PublicResource.getInstance().getStreamPath();
        streamPath = streamPath.endsWith("/") ? streamPath : streamPath + "/";
        String url;
        url = info.weixinlowimage.startsWith("http") ? info.weixinlowimage : streamPath + "/" + info.weixinlowimage;
        ImageLoader.getInstance().displayImage(url, ivImage);
        collapsingToolbar1 = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar1.setTitle(info.header);
        collapsingToolbar1.setCollapsedTitleTextColor(Color.BLACK);//设置收缩后Toolbar上字体的颜
        collapsingToolbar1.setExpandedTitleColor(Color.TRANSPARENT);
        mTVNewsContent.setHtml(info.h5content);
        mTVNewsContent.setInputEnabled(false);
        tv_title.setText(info.header);
        if (info.manuscripttype == ManuscriptListInfo.MANUSCRIPT_TYPE_CMS || (info.manuscripttype == ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT && info.camerist.equals("1"))) {
            bar_layout.setVisibility(View.VISIBLE);
            tv_title.setVisibility(View.VISIBLE);
            toolbar1.setVisibility(View.GONE);
            collapsingToolbar1.setVisibility(View.VISIBLE);
            bar_layout.addOnOffsetChangedListener(this);
        } else {
            tv_title.setVisibility(View.GONE);
            collapsingToolbar1.setVisibility(View.GONE);
            toolbar1.setTitle(info.header);
            toolbar1.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
            toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int totalScrollRange = Math.abs(appBarLayout.getTotalScrollRange());
        verticalOffset = Math.abs(verticalOffset);
        int i = (100 * (totalScrollRange - verticalOffset)) / totalScrollRange;
        i = Math.abs(i);
        float v = ((float) i) / 100.0f;
        XLog.i("onOffsetChanged: " + v);
        tv_title.setAlpha(v);
    }
}
