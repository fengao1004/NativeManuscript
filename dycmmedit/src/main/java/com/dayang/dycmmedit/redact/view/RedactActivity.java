package com.dayang.dycmmedit.redact.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;

import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.base.BaseActivity;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.utils.Constant;
import com.dayang.dycmmedit.utils.StatusBarUtil;
import com.elvishew.xlog.XLog;

public class RedactActivity extends BaseActivity {

    private ManuscriptListInfo manuscriptListInfo;

    @Override
    public void initView() {
        manuscriptListInfo = (ManuscriptListInfo) getIntent().getSerializableExtra("manuscriptListInfo");
        RedactFragment redactFragment = RedactFragment.newInstance(manuscriptListInfo);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_view, redactFragment)
                .show(redactFragment).commit();
        StatusBarUtil.setStatusBarLightMode(this, Color.parseColor("#ffffff"));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_redact;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void removeLoading() {

    }

    public void back(ManuscriptListInfo manuscriptListInfo, Fragment fragment, boolean hasChange) {
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
