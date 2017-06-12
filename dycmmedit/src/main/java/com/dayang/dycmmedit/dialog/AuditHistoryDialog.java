package com.dayang.dycmmedit.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.adapter.AuditHistoryAdapter;
import com.dayang.dycmmedit.info.CensorRecordInfo;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.info.RequestAuditManuscript;
import com.dayang.dycmmedit.info.ResultLoadManuscriptInfo;
import com.dayang.dycmmedit.info.UserListAndTargetSystem;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/24.
 * e-mail 897840134@qq.com
 */

public class AuditHistoryDialog {
    Context context;
    private MaterialDialog createDialog;
    List<CensorRecordInfo> list;
    private View customView;
    private RecyclerView rl_audit_history;

    public AuditHistoryDialog(Context context, List<CensorRecordInfo> list) {
        this.context = context;
        this.list = list;
    }

    public MaterialDialog build() {
        createDialog = new MaterialDialog.Builder(context)
                .title("审核历史")
                .titleGravity(GravityEnum.START)
                .customView(R.layout.dialog_audit_history, true)
                .cancelable(true)
                .build();
        initViews();
        return createDialog;
    }

    private void initViews() {
        customView = createDialog.getCustomView();
        rl_audit_history = (RecyclerView) customView.findViewById(R.id.rl_audit_history);
        rl_audit_history.setLayoutManager(new LinearLayoutManager(context));
        rl_audit_history.setAdapter(new AuditHistoryAdapter(context,list));
    }
}
