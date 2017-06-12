package com.dayang.dycmmedit.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.afollestad.materialdialogs.internal.MDButton;
import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.info.RequestSubmitManuscript;
import com.dayang.dycmmedit.info.UserListAndTargetSystem;
import com.dayang.dycmmedit.utils.PublicResource;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_spinner_dropdown_item;

/**
 * Created by 冯傲 on 2017/5/19.
 * e-mail 897840134@qq.com
 */

public class SubmitDialog{
    Context context;
    UserListAndTargetSystem system;
    private MaterialDialog createDialog;
    private View view;
    private ImageView iv_line_submit_dialog;
    private RadioGroup system_names;
    private LinearLayout ll_auditor;
    private Spinner spinner_auditor;
    private SubmitListener listener;
    private RequestSubmitManuscript requestSubmitManuscript;
    private ManuscriptListInfo info;
    private ArrayAdapter<String> arr_adapter;
    private TextView tv_cancel;
    private TextView tv_submit;

    public SubmitDialog(Context context, UserListAndTargetSystem system, ManuscriptListInfo info) {
        this.context = context;
        this.system = system;
        this.info = info;
    }

    public MaterialDialog build() {
        createDialog = new MaterialDialog.Builder(context)
                .title("提交到")
                .titleGravity(GravityEnum.START)
                .customView(R.layout.dialog_submit, true)
                .cancelable(false)
                .build();
        initViews();
        return createDialog;
    }

    private void initViews() {
        requestSubmitManuscript = new RequestSubmitManuscript();
        requestSubmitManuscript.setManuscriptIds(info.manuscriptid);
        requestSubmitManuscript.setTokenid(PublicResource.getInstance().getToken());
        requestSubmitManuscript.setUserCode(PublicResource.getInstance().getUserCode());
        requestSubmitManuscript.setUsername(PublicResource.getInstance().getUserName());
        view = createDialog.getCustomView();
        iv_line_submit_dialog = (ImageView) view.findViewById(R.id.iv_line_submit_dialog);
        system_names = (RadioGroup) view.findViewById(R.id.radioGroup_system_names);
        tv_submit = (TextView) view.findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSubmit(requestSubmitManuscript);
                }
            }
        });
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog.dismiss();
            }
        });
        system_names = (RadioGroup) view.findViewById(R.id.radioGroup_system_names);
        addButtons();
        system_names.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int i = 0;
                for (; i < system.targetSystemNames.size(); i++) {
                    if (group.getChildAt(i).getId() == checkedId) {
                        break;
                    }
                }
                requestSubmitManuscript.setTargetSystemIds(system.targetSystemIds.get(i));
            }
        });
        ll_auditor = (LinearLayout) view.findViewById(R.id.ll_auditor);
        spinner_auditor = (Spinner) view.findViewById(R.id.spinner_auditor);
        if (system.showChooseAuditor) {
            iv_line_submit_dialog.setVisibility(View.VISIBLE);
            ll_auditor.setVisibility(View.VISIBLE);
        } else {
            iv_line_submit_dialog.setVisibility(View.GONE);
            ll_auditor.setVisibility(View.GONE);
        }
        arr_adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, system.getAuditorNames());
        arr_adapter.setDropDownViewResource(simple_spinner_dropdown_item);
        spinner_auditor.setAdapter(arr_adapter);
        spinner_auditor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                }
                String censorAuditor = system.getAuditorNames().get(position);
                String censorAuditorId = system.getAuditors().get(position - 1).getId();
                requestSubmitManuscript.setCensorAuditor(censorAuditor);
                requestSubmitManuscript.setCensorAuditorId(censorAuditorId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_auditor.setSelection(-1);
    }

    public void setSubmitListener(SubmitListener listener) {
        this.listener = listener;
    }

    public interface SubmitListener {
        void onSubmit(RequestSubmitManuscript submitManuscript);
    }

    private void addButtons() {
        ArrayList<String> targetSystemNames = system.targetSystemNames;
        for (int i = 0; i < targetSystemNames.size(); i++) {
            RadioButton tempButton = new RadioButton(context);
            tempButton.setPadding(80, 0, 0, 0);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 8, 0, 8);
            tempButton.setLayoutParams(layoutParams);
            tempButton.setText(targetSystemNames.get(i));
            tempButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            system_names.addView(tempButton);
        }
    }
}
