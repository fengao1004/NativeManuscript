package com.dayang.dycmmedit.dialog;

import android.content.Context;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.info.RequestAuditManuscript;
import com.dayang.dycmmedit.info.UserListAndTargetSystem;

import static android.R.layout.simple_spinner_dropdown_item;

/**
 * Created by 冯傲 on 2017/5/22.
 * e-mail 897840134@qq.com
 */

public class AuditDialog {
    Context context;
    ManuscriptListInfo info;
    private MaterialDialog createDialog;
    private View view;
    private EditText ev_audit_opinion;
    private Spinner spinner_auditor;
    private TextView tv_target_system;
    private TextView tv_pass;
    private TextView tv_fail;
    private LinearLayout ll_auditor;

    public void setListener(AuditListener listener) {
        this.listener = listener;
    }

    private AuditListener listener;
    UserListAndTargetSystem system;
    private RequestAuditManuscript auditManuscript;

    public AuditDialog(Context context, ManuscriptListInfo info, UserListAndTargetSystem system) {
        this.context = context;
        this.info = info;
        this.system = system;
    }

    public MaterialDialog build() {
        createDialog = new MaterialDialog.Builder(context)
                .title("审核稿件")
                .titleGravity(GravityEnum.START)
                .customView(R.layout.dialog_audit, true)
                .cancelable(true)
                .build();
        initViews();
        return createDialog;
    }

    private void initViews() {
        auditManuscript = new RequestAuditManuscript();
        auditManuscript.manuscriptIds = info.manuscriptid;
        view = createDialog.getCustomView();
        ev_audit_opinion = (EditText) view.findViewById(R.id.ev_audit_opinion);
        spinner_auditor = (Spinner) view.findViewById(R.id.spinner_auditor);
        ll_auditor = (LinearLayout) view.findViewById(R.id.ll_auditor);
        tv_target_system = (TextView) view.findViewById(R.id.tv_target_system);
        tv_pass = (TextView) view.findViewById(R.id.tv_pass);
        tv_fail = (TextView) view.findViewById(R.id.tv_fail);
        if (!system.showChooseAuditor) {
            ll_auditor.setVisibility(View.GONE);
        }
        initEvent();
    }

    private void initEvent() {
        ev_audit_opinion.addTextChangedListener(new ListViewCompat(context) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                auditManuscript.censorOpinion = ev_audit_opinion.getText().toString();
            }
        });
        tv_target_system.setText(system.targetSystemNames.get(0));
        tv_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass();
            }
        });
        tv_fail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fail();
            }
        });

        ArrayAdapter<String> arr_adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, system.getAuditorNames());
        arr_adapter.setDropDownViewResource(simple_spinner_dropdown_item);
        spinner_auditor.setAdapter(arr_adapter);
        spinner_auditor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    auditManuscript.auditorName = "";
                    auditManuscript.auditorId = "";
                    return;
                }
                String censorAuditor = system.getAuditorNames().get(position);
                String censorAuditorId = system.getAuditors().get(position - 1).getId();
                auditManuscript.auditorName = censorAuditor;
                auditManuscript.auditorId = censorAuditorId;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_auditor.setSelection(-1);

    }

    private void pass() {
        auditManuscript.censorResultType = 0;
        if (listener != null) {
            listener.onAudit(info, auditManuscript);
        }
    }

    public void fail() {
        auditManuscript.censorResultType = 1;
        if (listener != null) {
            listener.onAudit(info, auditManuscript);
        }
    }

    public interface AuditListener {
        void onAudit(ManuscriptListInfo manuscriptListInfo, RequestAuditManuscript auditManuscript);
    }
}
