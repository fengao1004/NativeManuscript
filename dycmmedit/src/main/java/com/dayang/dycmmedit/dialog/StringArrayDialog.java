package com.dayang.dycmmedit.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.adapter.DialogStringListAdapter;

/**
 * Created by 冯傲 on 2016/8/14.
 * e-mail 897840134@qq.com
 */
public class StringArrayDialog extends android.app.Dialog {
    private Context context;

    public ItemClickListener l = null;
    private RecyclerView recycler_string_list;
    private DialogStringListAdapter dialogStringListAdapter;
    private TextView tv_string_dialog_title;

    public StringArrayDialog(Context context, String[] stringList) {
        this(context, R.style.Dialog, stringList);
    }

    public StringArrayDialog(Context context, int theme, String[] stringList) {
        super(context, theme);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.string_array_dialog, null);
        recycler_string_list = (RecyclerView) layout.findViewById(R.id.recycler_string_list);
        dialogStringListAdapter = new DialogStringListAdapter(stringList, context);
        tv_string_dialog_title = (TextView) layout.findViewById(R.id.tv_string_dialog_title);
        recycler_string_list.setAdapter(dialogStringListAdapter);
        recycler_string_list.setLayoutManager(new LinearLayoutManager(context));
        dialogStringListAdapter.setOnItemClickListener(new DialogStringListAdapter.ItemClickListener() {
            @Override
            public void onItemOnClick(int position) {
                if (l != null) {
                    l.onItemClick(position);
                }
            }
        });
        this.setContentView(layout);
    }

    public void setTitle(String title) {
        tv_string_dialog_title.setText(title);
    }

    public void setOnItemClickListener(ItemClickListener l) {
        this.l = l;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
