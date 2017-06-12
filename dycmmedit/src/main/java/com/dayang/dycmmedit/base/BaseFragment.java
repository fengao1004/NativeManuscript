package com.dayang.dycmmedit.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 冯傲 on 2017/5/11.
 * e-mail 897840134@qq.com
 */

public abstract class BaseFragment extends Fragment implements BaseViewInterface {

    public ProgressDialog progressDialog;
    public Unbinder bind;

    @Override
    public void showWaiting(String text) {
        getProgressDialog(text);
        progressDialog.show();
    }

    @Override
    public void removeWaiting() {
        if (progressDialog != null) {
            progressDialog.hide();
        }
    }

    private DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };

    public ProgressDialog getProgressDialog(String text) {
        if (progressDialog != null) {
            progressDialog.setMessage(text);
            return progressDialog;
        } else {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(text);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setOnKeyListener(keylistener);
            return progressDialog;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayoutId(), container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        init(view, savedInstanceState);
    }

    public abstract int getLayoutId();

    public abstract void init(final View view, @Nullable Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public void setBackKeyListener(BaseActivity.BackKeyListener backKeyListener){
        getBaseActivity().setBackKeyListener(backKeyListener);
    }

}
