package com.dayang.dycmmedit.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dayang.dycmmedit.R;

/**
 * Created by 冯傲 on 2017/5/24.
 * e-mail 897840134@qq.com
 */

public class AuditHistoryHolder extends RecyclerView.ViewHolder {

    public LinearLayout ll_audit_history_list;
    public TextView tv_audit_idea;
    public TextView tv_audit_level;
    public TextView tv_audit_status;
    public TextView tv_audit_strategy;
    public TextView tv_submit_times;
    public TextView tv_auditor;

    public AuditHistoryHolder(View itemView) {
        super(itemView);
        tv_audit_idea = (TextView) itemView.findViewById(R.id.tv_audit_idea);
        tv_audit_level = (TextView) itemView.findViewById(R.id.tv_audit_level);
        tv_audit_status = (TextView) itemView.findViewById(R.id.tv_audit_status);
        tv_audit_strategy = (TextView) itemView.findViewById(R.id.tv_audit_strategy);
        tv_submit_times = (TextView) itemView.findViewById(R.id.tv_submit_times);
        tv_auditor = (TextView) itemView.findViewById(R.id.tv_auditor);
        ll_audit_history_list = (LinearLayout) itemView.findViewById(R.id.ll_audit_history_list);
    }
}
