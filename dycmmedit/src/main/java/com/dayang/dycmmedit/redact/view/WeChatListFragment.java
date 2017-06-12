package com.dayang.dycmmedit.redact.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.adapter.WeChatListAdapter;
import com.dayang.dycmmedit.base.BaseActivity;
import com.dayang.dycmmedit.base.BaseFragment;
import com.dayang.dycmmedit.info.ExchangeManuscript;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.redact.presenter.WeChatListPresenterImpl;
import com.dayang.dycmmedit.redact.presenter.WeChatListPresenterInterface;
import com.dayang.dycmmedit.utils.Constant;
import com.dayang.dycmmedit.utils.PrivilegeUtil;
import com.elvishew.xlog.XLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 冯傲 on 2017/5/12.
 * e-mail 897840134@qq.com
 */

public class WeChatListFragment extends BaseFragment implements WeChatListView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_tool_title)
    TextView iv_tool_title;

    @BindView(R.id.rl_wechat_list)
    RecyclerView rl_wechat_list;


    @BindView(R.id.srl_wechat_list)
    SwipeRefreshLayout srl_wechat_list;

    boolean hasChange = false;
    int originalPosition = -1;
    int targetPosition = -1;
    private ManuscriptListInfo manuscriptListInfo;
    private WeChatListPresenterInterface weChatListPresenterInterface;
    private List<ManuscriptListInfo> list;
    private WeChatListAdapter weChatListAdapter;

    public static WeChatListFragment newInstance(ManuscriptListInfo manuscriptListInfo) {
        WeChatListFragment newFragment = new WeChatListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("manuscriptListInfo", manuscriptListInfo);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    private WeChatListActivity activity;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wechat_list;
    }

    @Override
    public void init(View view, @Nullable Bundle savedInstanceState) {
        weChatListPresenterInterface = new WeChatListPresenterImpl(this);
        activity = (WeChatListActivity) getActivity();
        Bundle arguments = getArguments();
        manuscriptListInfo = (ManuscriptListInfo) arguments.getSerializable("manuscriptListInfo");
        setBackKeyListener(null);
        initView();
    }

    private void initView() {
        setHasOptionsMenu(true);
        rl_wechat_list.setLayoutManager(new LinearLayoutManager(activity));
        helper.attachToRecyclerView(rl_wechat_list);
        weChatListPresenterInterface.loadList(manuscriptListInfo);
        rl_wechat_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    exchangeItemIndex();
                }
                return false;
            }
        });
        iv_tool_title.setText("微信列表");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        srl_wechat_list.setColorSchemeColors(getResources().getColor(R.color.colorDYBlue));
        srl_wechat_list.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                weChatListPresenterInterface.loadList(manuscriptListInfo);
            }
        });
    }

    private void back() {
        if (list.size() > 0) {
            activity.back(hasChange, list.get(0));
        } else {
            activity.back(hasChange, null);
        }
    }

    private void exchangeItemIndex() {
        XLog.i("targetPosition: " + targetPosition);
        if (list != null && list.size() > 0) {
            manuscriptListInfo = list.get(0);
        } else {
            originalPosition = -1;
            targetPosition = -1;
            return;
        }
        if (originalPosition == targetPosition) {
            originalPosition = -1;
            targetPosition = -1;
            return;
        }

        ArrayList<ExchangeManuscript> exchangeManuscripts = new ArrayList<>();
        int isNO1 = -1;
        while (originalPosition != targetPosition) {
            if ((originalPosition == 0 || targetPosition == 0) && isNO1 == -1) {
                isNO1 = 0;
            } else {
                isNO1 = 1;
            }
            if (originalPosition > targetPosition) {
                exchangeManuscripts.add(new ExchangeManuscript(list.get(targetPosition).manuscriptid, list.get(originalPosition).manuscriptid, isNO1));
            } else {
                exchangeManuscripts.add(new ExchangeManuscript(list.get(originalPosition).manuscriptid, list.get(targetPosition).manuscriptid, isNO1));
            }
            originalPosition = originalPosition + (targetPosition - originalPosition) / Math.abs(targetPosition - originalPosition);
        }
        if (exchangeManuscripts.size() != 0) {
            weChatListPresenterInterface.exchange(exchangeManuscripts);
        }
        originalPosition = -1;
        targetPosition = -1;
    }

    @Override
    public Activity getViewActivity() {
        return getActivity();
    }

    @Override
    public void makeToast(String text) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshList(final List<ManuscriptListInfo> list) {
        this.list = list;
        weChatListAdapter = new WeChatListAdapter(rl_wechat_list, activity, list, this);
        if (list.size() > 0) {
            manuscriptListInfo = list.get(0);
        } else {
            Toast.makeText(activity, "获取列表错误", Toast.LENGTH_SHORT).show();
        }
        weChatListAdapter.setDeleteListener(new WeChatListAdapter.DeleteListener() {
            @Override
            public void delete(ManuscriptListInfo info) {
                weChatListPresenterInterface.deleteManuscript(weChatListAdapter, info);
            }
        });
        weChatListAdapter.setAddListener(new WeChatListAdapter.AddListener() {
            @Override
            public void add() {
                weChatListPresenterInterface.addManuscript(weChatListAdapter);
            }
        });
        weChatListAdapter.setGoToRedactListener(new WeChatListAdapter.GoToRedactListener() {
            @Override
            public void goToRedact(ManuscriptListInfo manuscriptListInfo) {
                enterRedact(manuscriptListInfo);
            }
        });
        rl_wechat_list.setAdapter(weChatListAdapter);
    }

    @Override
    public void enterRedact(ManuscriptListInfo manuscriptListInfo) {
        Intent intent = new Intent(activity, RedactActivity.class);
        intent.putExtra("manuscriptListInfo", manuscriptListInfo);
        this.startActivityForResult(intent, Constant.REQUESTCODE_FROM_WECHAT_REDACT);
    }

    @Override
    public void showLoading() {
        srl_wechat_list.post(new Runnable() {
            @Override
            public void run() {
                srl_wechat_list.setRefreshing(true);
            }
        });
    }

    @Override
    public void removeLoading() {
        srl_wechat_list.post(new Runnable() {
            @Override
            public void run() {
                srl_wechat_list.setRefreshing(false);
            }
        });
    }

    @Override
    public void setHasChange(boolean hasChange) {
        this.hasChange = hasChange;
    }

    @Override
    public void refresh() {
        setHasChange(true);
        weChatListPresenterInterface.loadList(manuscriptListInfo);
    }

    ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;//拖拽
            int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;//侧滑删除
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int viewHolderPosition = viewHolder.getAdapterPosition();
            if (viewHolderPosition == list.size() || target.getAdapterPosition() == list.size()) {
                weChatListAdapter.notifyDataSetChanged();
                return false;
            }
            XLog.i("onMove: " + viewHolderPosition);
            originalPosition = originalPosition == -1 ? viewHolderPosition : originalPosition;
            XLog.i("originalPosition: " + originalPosition);
            targetPosition = target.getAdapterPosition();
            Collections.swap(list, viewHolder.getAdapterPosition(), target.getAdapterPosition());
            weChatListAdapter.notifyItemMoved(viewHolderPosition, targetPosition);
            return false;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

    });

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.RESULTCODE_NEED_REFRESH) {
            hasChange = true;
            weChatListPresenterInterface.loadList(manuscriptListInfo);
        } else if (resultCode == Constant.RESULTCODE_NOT_NEED_REFRESH) {

        }
    }

    @Override
    public void setBackKeyListener(BaseActivity.BackKeyListener backKeyListener) {
        if (backKeyListener == null) {
            super.setBackKeyListener(new BaseActivity.BackKeyListener() {
                @Override
                public boolean onBackKeyDown() {
                    back();
                    return true;
                }
            });
        } else {
            super.setBackKeyListener(backKeyListener);
        }
    }
}
