package com.dayang.dycmmedit.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.holder.AuditHistoryHolder;
import com.dayang.dycmmedit.info.CensorRecordInfo;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/24.
 * e-mail 897840134@qq.com
 */

public class AuditHistoryAdapter extends RecyclerView.Adapter<AuditHistoryHolder> {
    List<CensorRecordInfo> list;
    Context context;

    public AuditHistoryAdapter(Context context, List<CensorRecordInfo> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public AuditHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_audit_history_list, parent, false);
        return new AuditHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(AuditHistoryHolder holder, int position) {
        int index1 = 0;
        int count = 0;
        int index2 = 0;
        for (int i = 0; i < list.size(); i++) {
            index1 = i;
            for (int j = 0; j < list.get(i).getCensorDetailOpition().size(); j++) {
                index2 = j;
                if (count == position) {
                    break;
                }
                count++;
            }
        }
        CensorRecordInfo censorRecordInfo = list.get(index1);
        CensorRecordInfo.CensorDetailOpitionEntity entity = censorRecordInfo.getCensorDetailOpition().get(index2);
        holder.tv_audit_idea.setText(entity.getCensoropinion());
        holder.tv_audit_strategy.setText(entity.getCensortype() == 0 ? "单审即过" : "全审即过");
        int censortype = entity.getCensorstate();
        switch (censortype) {
            case 3:
                holder.tv_audit_status.setText("打回");
                holder.tv_audit_status.setTextColor(Color.parseColor("#FF0000"));
                break;
            case 1:
                holder.tv_audit_status.setText("待审核");
                holder.tv_audit_status.setTextColor(Color.parseColor("#FEAB36"));
                break;
            case 2:
                holder.tv_audit_status.setText("通过");
                holder.tv_audit_status.setTextColor(Color.GREEN);
                break;
        }
        holder.tv_submit_times.setText("第" + censorRecordInfo.getSubmitnum() + "次提交");
        holder.tv_audit_level.setText(entity.getLevelname());
        holder.tv_auditor.setText(entity.getUsername());
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getCensorDetailOpition().size(); j++) {
                count++;
            }
        }
        return count;
    }
}
