package com.dayang.dycmmedit.adapter;

import android.content.Context;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.holder.DialogCreateListHolder;
import com.dayang.dycmmedit.info.CreateDialogItemInfo;
import com.dayang.dycmmedit.info.FolderInfo;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.utils.PublicResource;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_spinner_dropdown_item;

/**
 * Created by 冯傲 on 2017/5/5.
 * e-mail 897840134@qq.com
 */

public class DialogCreateListAdapter extends RecyclerView.Adapter<DialogCreateListHolder> {
    Context context;
    ManuscriptListInfo info;
    boolean canRedact;
    ArrayList<CreateDialogItemInfo> list;
    SaveListener saveListener;
    UpdateListener updateListener;
    DelListener delListener;

    public void setDelListener(DelListener delListener) {
        this.delListener = delListener;
    }

    private ImageView iv_thumbnail;
    private ImageView iv_del;

    public void setSaveListener(SaveListener saveListener) {
        this.saveListener = saveListener;
    }

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public DialogCreateListAdapter(Context context, ManuscriptListInfo info, boolean canRedact) {
        this.context = context;
        this.info = info;
        this.canRedact = canRedact;
        list = info.list;
//        if (info.manuscripttype == ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT && info.arrayindex != 0) {
//            list = info.wechatChildList;
//        }

    }

    @Override
    public DialogCreateListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dialog_create, parent, false);
        return new DialogCreateListHolder(view);
    }

    @Override
    public void onBindViewHolder(final DialogCreateListHolder holder, int position) {
        int itemCount = list.size();
        holder.ll_normal.setVisibility(View.GONE);
        if (itemCount == position && (info.manuscripttype == ManuscriptListInfo.MANUSCRIPT_TYPE_TV || info.manuscripttype == ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO)) {
            holder.button_save.setVisibility(View.VISIBLE);
            holder.button_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (saveListener != null) {
                        saveListener.save();
                    }
                }
            });
            return;
        } else if (position >= itemCount) {
            if (position == itemCount) {
                iv_thumbnail = holder.iv_thumbnail;
                iv_del = holder.iv_del_thumbnail;
                String streamPath = PublicResource.getInstance().getStreamPath();
                streamPath = streamPath.endsWith("/") ? streamPath : streamPath + "/";
                String url = info.weixinlowimage.startsWith("http") ? info.weixinlowimage : info.weixinlowimage.trim().equals("") ? "" : streamPath + "/" + info.weixinlowimage;
                ImageLoader.getInstance().displayImage(url, holder.iv_thumbnail);
                holder.iv_del_thumbnail.setVisibility(info.weixinlowimage.equals("") ? View.GONE : View.VISIBLE);
                holder.rl_thumbnail.setVisibility(View.VISIBLE);
                holder.iv_del_thumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (delListener != null) {
                            delListener.del();
                        }
                    }
                });
                iv_thumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (updateListener != null) {
                            updateListener.updateImage();
                        }
                    }
                });
            } else if (position == itemCount + 1) {
                holder.button_save.setVisibility(View.VISIBLE);
                holder.button_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (saveListener != null) {
                            saveListener.save();
                        }
                    }
                });
            }
            return;
        } else {
            holder.ll_normal.setVisibility(View.VISIBLE);
            holder.button_save.setVisibility(View.GONE);
        }
        holder.title.setText(list.get(position).title);
        int type = list.get(position).type;
        switch (type) {
            case CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_CHECKBOX:
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.ll_dialog_create_item_cb_spinner.setVisibility(View.GONE);
                holder.ll_dialog_create_item_cb_edit.setVisibility(View.GONE);
                break;
            case CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT:
                holder.checkBox.setVisibility(View.GONE);
                holder.ll_dialog_create_item_cb_spinner.setVisibility(View.GONE);
                holder.ll_dialog_create_item_cb_edit.setVisibility(View.VISIBLE);
                break;
            case CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_PULL_LIST:
                holder.checkBox.setVisibility(View.GONE);
                holder.ll_dialog_create_item_cb_spinner.setVisibility(View.VISIBLE);
                holder.ll_dialog_create_item_cb_edit.setVisibility(View.GONE);
                break;
        }
        final Spinner spinner = holder.spinner;
        String title = list.get(position).title;
        final ArrayList<String> data_list = new ArrayList<>();
        int defaultItemIndex = -1;
        ArrayAdapter<String> arr_adapter;
        if (!canRedact) {
            holder.edit.setKeyListener(null);
            holder.checkBox.setClickable(false);
            holder.spinner.setEnabled(false);
        }
        switch (title) {
            case "标题":
                holder.edit.setText(info.header);
                holder.edit.addTextChangedListener(new ListViewCompat(context) {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        super.onTextChanged(s, start, before, count);
                        info.header = holder.edit.getText().toString().trim();
                    }
                });
                break;
            case "副标题":
                holder.edit.setText(info.subtitle);
                holder.edit.addTextChangedListener(new ListViewCompat(context) {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        super.onTextChanged(s, start, before, count);
                        info.subtitle = holder.edit.getText().toString().trim();
                    }
                });
                break;
            case "选题":
                final List<FolderInfo> folderInfos = PublicResource.getInstance().getFolderInfos();
                for (int i = 0; i < folderInfos.size(); i++) {
                    data_list.add(folderInfos.get(i).getName());
                    if (folderInfos.get(i).getName().equals(info.foldername)) {
                        defaultItemIndex = i;
                    }
                }
                arr_adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, data_list);
                arr_adapter.setDropDownViewResource(simple_spinner_dropdown_item);
                spinner.setAdapter(arr_adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        info.foldername = folderInfos.get(position).getName();
                        info.folderid = folderInfos.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spinner.setSelection(defaultItemIndex);

                break;
            case "所属栏目":
                for (int i = 0; i < info.columns.size(); i++) {
                    data_list.add(info.columns.get(i));
                    if (info.columns.get(i).equals(info.columnname)) {
                        defaultItemIndex = i;
                    }
                }
                arr_adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, info.columns);
                arr_adapter.setDropDownViewResource(simple_spinner_dropdown_item);
                spinner.setAdapter(arr_adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        info.columnname = data_list.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spinner.setSelection(defaultItemIndex);
                break;
            case "昵称":
                holder.edit.setText(info.reporter);
                holder.edit.addTextChangedListener(new ListViewCompat(context) {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        super.onTextChanged(s, start, before, count);
                        info.reporter = holder.edit.getText().toString().trim();
                    }
                });
                break;
            case "摘要":
                holder.edit.setText(info.summary);
                holder.edit.addTextChangedListener(new ListViewCompat(context) {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        super.onTextChanged(s, start, before, count);
                        info.summary = holder.edit.getText().toString().trim();
                    }
                });
                break;
            case "作者":
                holder.edit.setText(info.editor);
                holder.edit.addTextChangedListener(new ListViewCompat(context) {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        super.onTextChanged(s, start, before, count);
                        info.editor = holder.edit.getText().toString().trim();
                    }
                });
                break;
            case "缩略图发布到正文":
                holder.checkBox.setChecked(info.camerist.equals("1"));
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        info.camerist = isChecked ? "1" : "0";
                    }
                });
                break;
            case "开启评论列表":
                holder.checkBox.setChecked(info.iscomment == 1);
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        info.iscomment = isChecked ? 1 : 0;
                    }
                });
                break;
            case "记者":
                holder.edit.setText(info.reporter);
                holder.edit.addTextChangedListener(new ListViewCompat(context) {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        super.onTextChanged(s, start, before, count);
                        info.reporter = holder.edit.getText().toString().trim();
                    }
                });
            case "摄像员":
                holder.edit.setText(info.camerist);
                holder.edit.addTextChangedListener(new ListViewCompat(context) {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        super.onTextChanged(s, start, before, count);
                        info.camerist = holder.edit.getText().toString().trim();
                    }
                });
            case "新媒体栏目":
                //TODO 新媒体栏目
                data_list.add("");
                arr_adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, data_list);
                arr_adapter.setDropDownViewResource(simple_spinner_dropdown_item);
                spinner.setAdapter(arr_adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            return;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spinner.setSelection(defaultItemIndex);
                break;
            case "来源":
                holder.edit.setText(info.sources);
                holder.edit.addTextChangedListener(new ListViewCompat(context) {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        super.onTextChanged(s, start, before, count);
                        info.sources = holder.edit.getText().toString().trim();
                    }
                });
                break;
            case "关键字":
                holder.edit.setText(info.keywords);
                holder.edit.addTextChangedListener(new ListViewCompat(context) {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        super.onTextChanged(s, start, before, count);
                        info.keywords = holder.edit.getText().toString().trim();
                    }
                });
                break;
            case "原文链接":
                holder.edit.setText(info.sourceurl);
                holder.edit.addTextChangedListener(new ListViewCompat(context) {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        super.onTextChanged(s, start, before, count);
                        info.sourceurl = holder.edit.getText().toString().trim();
                    }
                });
                break;
            case "剪辑员":
                holder.edit.setText(info.editor);
                holder.edit.addTextChangedListener(new ListViewCompat(context) {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        super.onTextChanged(s, start, before, count);
                        info.editor = holder.edit.getText().toString().trim();
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        switch (info.manuscripttype) {
            case ManuscriptListInfo.MANUSCRIPT_TYPE_CMS:
                return list.size() + 2;
            case ManuscriptListInfo.MANUSCRIPT_TYPE_TV:
                return list.size() + 1;
            case ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT:
                return list.size() + 2;
            case ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO:
                return list.size() + 1;
        }
        return list.size() + 1;
    }

    public void setImageUrl() {
        if (info.weixinlowimage.equals("")) {
            ImageLoader.getInstance().displayImage("", iv_thumbnail);
            iv_del.setVisibility(View.GONE);
        } else {
            iv_del.setVisibility(View.VISIBLE);
            String streamPath = PublicResource.getInstance().getStreamPath();
            streamPath = streamPath.endsWith("/") ? streamPath : streamPath + "/";
            String url;
            url = info.weixinlowimage.startsWith("http") ? info.weixinlowimage : streamPath + "/" + info.weixinlowimage;
            ImageLoader.getInstance().displayImage(url, iv_thumbnail);
        }
    }

    public interface UpdateListener {
        void updateImage();
    }

    public interface SaveListener {
        void save();
    }

    public interface DelListener {
        void del();
    }
}
