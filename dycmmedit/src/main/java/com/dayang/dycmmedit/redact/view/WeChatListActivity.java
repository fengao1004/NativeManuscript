package com.dayang.dycmmedit.redact.view;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.base.BaseActivity;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.utils.Constant;
import com.dayang.dycmmedit.utils.StatusBarUtil;

import butterknife.BindView;

public class WeChatListActivity extends BaseActivity {
    @BindView(R.id.activity_we_chat_list)
    View activity_we_chat_list;


    @Override
    public void initView() {
        ManuscriptListInfo manuscriptListInfo = (ManuscriptListInfo) getIntent().getSerializableExtra("manuscriptListInfo");
        WeChatListFragment weChatListFragment = WeChatListFragment.newInstance(manuscriptListInfo);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_we_chat_list, weChatListFragment)
                .show(weChatListFragment).commit();
        StatusBarUtil.setStatusBarLightMode(this, Color.parseColor("#ffffff"));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_we_chat_list;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void removeLoading() {

    }

    public void back(boolean hasChange, ManuscriptListInfo manuscriptListInfo) {
        Intent data = new Intent();
        data.putExtra("manuscriptListInfo", manuscriptListInfo);
        if (hasChange) {
            setResult(Constant.RESULTCODE_NEED_REFRESH, data);
        } else {
            setResult(Constant.RESULTCODE_NOT_NEED_REFRESH, data);
        }
        finish();
    }

}
