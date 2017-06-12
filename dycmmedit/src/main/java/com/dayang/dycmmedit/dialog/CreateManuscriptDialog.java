package com.dayang.dycmmedit.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.adapter.DialogCreateListAdapter;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.info.UserInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 冯傲 on 2017/5/5.
 * e-mail 897840134@qq.com
 */

public class CreateManuscriptDialog {

    private final static String DEFAULT_POSITIVE = "创建";
    private final static String DEFAULT_NEVER = "取消";
    private final Context context;
    private int type;
    private MaterialDialog createDialog;
    private int buttonsColor = R.color.colorDYBlue;
    private View positiveButton;
    private View neutralButton;
    private RecyclerView dialog_create_list;
    private CreateListener listener;
    private ManuscriptListInfo manuscriptListInfo;
    private String userName;
    private String userCode;
    private int flagFromWhere = 0;
    private int FROMLIST = 123;
    private int FROMMANUSCRIPT = 122;
    private boolean canRedact;
    private List<UserInfo.DataEntity.ColumnListModelEntity> columnListModel;
    private TextView tv_message;

    public CreateManuscriptDialog(Context context, ManuscriptListInfo manuscriptListInfo, boolean canRedact) {
        this.type = manuscriptListInfo.manuscripttype;
        this.context = context;
        this.manuscriptListInfo = manuscriptListInfo;
        this.userName = manuscriptListInfo.username;
        this.canRedact = canRedact;
        flagFromWhere = FROMMANUSCRIPT;
    }

    private void build() {
        if (flagFromWhere == FROMMANUSCRIPT) {
            createDialog = new MaterialDialog.Builder(context)
                    .customView(R.layout.dialog_create_manuscript, true)
                    .build();
        } else if (flagFromWhere == FROMLIST) {
            createDialog = new MaterialDialog.Builder(context)
                    .customView(R.layout.dialog_create_manuscript, true)
                    .positiveText(DEFAULT_POSITIVE)
                    .positiveColorRes(buttonsColor)
                    .neutralColorRes(buttonsColor)
                    .neutralText(DEFAULT_NEVER).build();
        }
        initViews();
    }

    private void initViews() {
        if (manuscriptListInfo == null) {
            manuscriptListInfo = new ManuscriptListInfo(type);
            if (columnListModel != null) {
                ArrayList<String> columnNameList = new ArrayList<>();
                columnNameList.add("暂不指派栏目");
                ArrayList<String> columnIdList = new ArrayList<>();
                for (int i = 0; i < columnListModel.size(); i++) {
                    columnNameList.add(columnListModel.get(i).getName());
                    columnIdList.add(columnListModel.get(i).getId());
                }
                manuscriptListInfo.columns = columnNameList;
                manuscriptListInfo.columnsID = columnIdList;
            }
        }
        positiveButton = createDialog.getActionButton(DialogAction.POSITIVE);
        neutralButton = createDialog.getActionButton(DialogAction.NEUTRAL);
        dialog_create_list = (RecyclerView) createDialog.getCustomView().findViewById(R.id.dialog_create_list);
        tv_message = (TextView) createDialog.getCustomView().findViewById(R.id.tv_message);
        if (canRedact) {
            tv_message.setVisibility(View.GONE);
        } else {
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText("用户没有权限编辑稿件信息");
        }
        dialog_create_list.setLayoutManager(new LinearLayoutManager(context));
        long createTime = new Date().getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmm");
        String time1 = format1.format(createTime);
        String time2 = format2.format(createTime);
        manuscriptListInfo.createtime = TextUtils.isEmpty(manuscriptListInfo.createtime) ? time1 : manuscriptListInfo.createtime;
        manuscriptListInfo.username = TextUtils.isEmpty(manuscriptListInfo.username) ? userName : manuscriptListInfo.username;
        manuscriptListInfo.header = TextUtils.isEmpty(manuscriptListInfo.header) ? "新建稿件_" + time2 : manuscriptListInfo.header;
        manuscriptListInfo.usercode = TextUtils.isEmpty(manuscriptListInfo.usercode) ? userCode : manuscriptListInfo.usercode;
        DialogCreateListAdapter adapter = new DialogCreateListAdapter(context, manuscriptListInfo, canRedact);
        dialog_create_list.setItemViewCacheSize(20);
        dialog_create_list.setAdapter(adapter);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onCreateManuscript(manuscriptListInfo);
                }
            }
        });

        neutralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog.hide();
            }
        });
    }

    public void setPositiveText(String positiveText) {
        createDialog.setActionButton(DialogAction.POSITIVE, positiveText);
    }

    public void show() {
        build();
        createDialog.setCanceledOnTouchOutside(true);
        createDialog.show();
    }

    public void setCreateListener(CreateListener listener) {
        this.listener = listener;
    }

    public interface CreateListener {
        void onCreateManuscript(ManuscriptListInfo info);
    }

    public void dismiss() {
        createDialog.dismiss();
    }

    public void hide() {
        createDialog.hide();
    }
}
