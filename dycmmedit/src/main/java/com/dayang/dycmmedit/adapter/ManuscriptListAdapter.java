package com.dayang.dycmmedit.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.holder.ManuscriptListViewHolder;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.elvishew.xlog.XLog;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 冯傲 on 2017/4/27.
 * e-mail 897840134@qq.com
 */

public class ManuscriptListAdapter extends RecyclerView.Adapter<ManuscriptListViewHolder> {
    private Context context;
    private int page;
    private int maxPage;
    private boolean status;
    private List<ManuscriptListInfo> list;
    public static final int ITEM_TYPE_NORMAL = 0;
    public static final int ITEM_TYPE_LAST = 1;
    private ErrorItemOnClickListener listener;
    private ManuscriptItemOnClickListener manuscriptItemOnClickListener;
    private ManuscriptItemLongClickListener itemLongClickListener;
    private final Vibrator vibrator;

    public void setManuscriptItemOnClickListener(ManuscriptItemOnClickListener manuscriptItemOnClickListener) {
        this.manuscriptItemOnClickListener = manuscriptItemOnClickListener;
    }

    public void setManuscriptItemLongClickListener(ManuscriptItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public ManuscriptListAdapter(Context context, List<ManuscriptListInfo> list, int page, int maxPage, boolean status) {
        this.context = context;
        this.page = page;
        this.maxPage = maxPage;
        this.list = list;
        this.status = status;
        //震动管理
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    public ManuscriptListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ManuscriptListViewHolder manuscriptListViewHolder;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(context).inflate(R.layout.item_manuscript_list, parent, false);
            manuscriptListViewHolder = new ManuscriptListViewHolder(view, viewType);
        } else if (viewType == ITEM_TYPE_LAST) {
            view = LayoutInflater.from(context).inflate(R.layout.item_load_more, parent, false);
            manuscriptListViewHolder = new ManuscriptListViewHolder(view, viewType);
        } else {
            manuscriptListViewHolder = null;
        }
        return manuscriptListViewHolder;
    }

    @Override
    public void onBindViewHolder(final ManuscriptListViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case ITEM_TYPE_LAST:
                if (!status) {
                    holder.loadMore.setVisibility(View.VISIBLE);
                    holder.loadMore.setText("加载失败，点击重试");
                    holder.loadMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onClick(v);
                            }
                        }
                    });
                } else {
                    if (page >= maxPage - 1) {
                        holder.loadMore.setText("没有更多数据");
                        holder.loadMore.setOnClickListener(null);
                    } else {
                        holder.loadMore.setText("加载中....");
                        holder.loadMore.setOnClickListener(null);
                    }
                }
                break;
            case ITEM_TYPE_NORMAL:
                View ll_manuscript_item = holder.itemView.findViewById(R.id.ll_manuscript_item);
                ll_manuscript_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        manuscriptItemOnClickListener.onClick(list.get(position));
                    }
                });
                ll_manuscript_item.setLongClickable(true);
                ll_manuscript_item.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        long[] pattern = {0, 70}; // 停止 开启 停止 开启
                        vibrator.vibrate(pattern, -1);
                        itemLongClickListener.onLongClick(list.get(position));
                        return true;
                    }
                });
                ManuscriptListInfo info = list.get(position);
                int type = info.manuscripttype;
                int status = info.status;
                switch (type) {
                    case ManuscriptListInfo.MANUSCRIPT_TYPE_CMS:
                        holder.iv_icon.setImageResource(R.drawable.icon_hlw);
                        break;
                    case ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT:
                        holder.iv_icon.setImageResource(R.drawable.icon_wx);
                        break;
                    case ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO:
                        holder.iv_icon.setImageResource(R.drawable.icon_wb);
                        break;
                    case ManuscriptListInfo.MANUSCRIPT_TYPE_TV:
                        holder.iv_icon.setImageResource(R.drawable.icon_wy);
                        break;
                }
                ForegroundColorSpan handleBlue = new ForegroundColorSpan(Color.parseColor("#0F90D2"));
                ForegroundColorSpan waitingAudit = new ForegroundColorSpan(Color.parseColor("#FF9600"));
                ForegroundColorSpan passGreen = new ForegroundColorSpan(Color.parseColor("#0C9D3C"));
                ForegroundColorSpan failRed = new ForegroundColorSpan(Color.parseColor("#FF0006"));
                SpannableStringBuilder ssBuilder;
                switch (status) {
                    case ManuscriptListInfo.MANUSCRIPT_STATUS_WAITING_SUBMIT:
                        ssBuilder = new SpannableStringBuilder("待提交");
                        ssBuilder.setSpan(handleBlue, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        holder.status.setText(ssBuilder);
                        break;
                    case ManuscriptListInfo.MANUSCRIPT_STATUS_WAITING_PENDING:
                        ssBuilder = new SpannableStringBuilder("待审核");
                        ssBuilder.setSpan(waitingAudit, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        holder.status.setText(ssBuilder);
                        break;
                    case ManuscriptListInfo.MANUSCRIPT_STATUS_PASS:
                        ssBuilder = new SpannableStringBuilder("已通过");
                        ssBuilder.setSpan(passGreen, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        holder.status.setText(ssBuilder);
                        break;
                    case ManuscriptListInfo.MANUSCRIPT_STATUS_FAIL:
                        ssBuilder = new SpannableStringBuilder("已打回");
                        ssBuilder.setSpan(failRed, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        holder.status.setText(ssBuilder);
                        break;
                }
                holder.status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemLongClickListener.onLongClick(list.get(position));
                    }
                });
                String createTime = info.createtime;
                String userName = info.username;
                holder.time.setText(createTime);
                holder.name.setText(userName);
                holder.title.setText(info.header);
                break;
        }

    }

    public static String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    private Spanned colorText(String text) {
        return Html.fromHtml(String.format("<font color='#3399FF'>%1$s</font>", text));
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (position == list.size()) {
            type = ITEM_TYPE_LAST;
        } else {
            type = ITEM_TYPE_NORMAL;
        }
        return type;
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setListener(ErrorItemOnClickListener listener) {
        this.listener = listener;
    }

    public interface ErrorItemOnClickListener {
        void onClick(View view);
    }

    public interface ManuscriptItemOnClickListener {
        void onClick(ManuscriptListInfo manuscriptListInfo);
    }

    public interface ManuscriptItemLongClickListener {
        void onLongClick(ManuscriptListInfo manuscriptListInfo);
    }
}
