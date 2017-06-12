package com.dayang.dycmmedit.main.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.adapter.ManuscriptListAdapter;
import com.dayang.dycmmedit.base.BaseActivity;
import com.dayang.dycmmedit.base.BaseFragment;
import com.dayang.dycmmedit.dialog.AuditDialog;
import com.dayang.dycmmedit.dialog.CreateManuscriptDialog;
import com.dayang.dycmmedit.dialog.StringArrayDialog;
import com.dayang.dycmmedit.dialog.SubmitDialog;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.info.RequestAuditManuscript;
import com.dayang.dycmmedit.info.RequestSubmitManuscript;
import com.dayang.dycmmedit.info.UserInfo;
import com.dayang.dycmmedit.info.UserListAndTargetSystem;
import com.dayang.dycmmedit.main.presenter.MainPresenter;
import com.dayang.dycmmedit.main.presenter.MainPresenterImpl;
import com.dayang.dycmmedit.redact.view.RedactActivity;
import com.dayang.dycmmedit.redact.view.WeChatListActivity;
import com.dayang.dycmmedit.utils.Constant;
import com.dayang.dycmmedit.utils.DisplayUtils;
import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 冯傲 on 2017/4/27.
 * e-mail 897840134@qq.com
 */

public class MainFragment extends BaseFragment implements MainViewInterface, MaterialDialog.ListCallback {
    private boolean openAddbutton = false;
    private List<ManuscriptListInfo> manuscriptList;
    public static final String RESOURCETYPE_MANUSCRIPT_ALL = "0";
    public static final String RESOURCETYPE_MANUSCRIPT_MAIN = "1";
    public static final String RESOURCETYPE_CENSORTASK = "8";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.search_view)
    MaterialSearchView search_view;

    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipe_refresh_widget;

    @BindView(R.id.recycler_view_list)
    RecyclerView recycler_view_list;

    @BindView(R.id.ll_add_tv)
    LinearLayout ll_add_tv;

    @BindView(R.id.ll_add_web)
    LinearLayout ll_add_web;

    @BindView(R.id.ll_add_weibo)
    LinearLayout ll_add_weibo;

    @BindView(R.id.iv_tool_title)
    TextView iv_tool_title;

    @BindView(R.id.ll_add_wechat)
    LinearLayout ll_add_wechat;

    @BindView(R.id.iv_shadow)
    ImageView iv_shadow;

    @BindView(R.id.tv_search_by_state)
    TextView tv_search_by_state;

    @BindView(R.id.tv_search_by_type)
    TextView tv_search_by_type;

    @BindView(R.id.tv_search_by_time)
    TextView tv_search_by_time;

    private FragmentActivity activity;
    private int unitDistance;
    private float origin;
    private String resourceType = "0";
    private Integer page = 1;
    private Integer maxPage = 1;
    private String states = "";
    private String manuscriptType = "";
    private String dateranges = "";
    private String searchTitle = "";
    private MainPresenter mainPresenter;
    private boolean isLoadData = false;
    private boolean loadStatus;
    private StringArrayDialog timeDialog;
    private StringArrayDialog statusDialog;
    private StringArrayDialog typeDialog;
    private String[] statesValue = new String[]{"", "0", "1", "2", "3"};
    private String[] timeValue = new String[]{"", "1", "2", "3", "7"};
    private String[] typeValue = new String[]{"", "0", "1", "2", "3"};
    private UserInfo userInfo;
    private String[] statusStrings = new String[]{"全部", "待提交", "待审核", "已通过", "已打回"};
    private String[] timeStrings = new String[]{"全部", "一天", "两天", "三天", "七天"};
    private String[] typeStrings = new String[]{"全部", "网页", "微信", "微博", "电视"};
    private ManuscriptListInfo selectedManuscriptListInfo;
    private MaterialDialog operationDialog_tfsq;
    private MaterialDialog operationDialog_fsq;
    private MaterialDialog operationDialog_fssq;
    private MaterialDialog operationDialog_ssq;
    private MaterialDialog operationDialog_sq;
    private MaterialDialog copyTypeDialog;
    private MaterialDialog filtrateTypeDialog;
    private MaterialDialog filtrateStatusDialog;
    private MaterialDialog filtrateTimeDialog;


    public static MainFragment newInstance() {
        MainFragment newFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", "");
        newFragment.setArguments(bundle);
        return newFragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void init(View view, @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        mainPresenter = new MainPresenterImpl(this, "0003", "TGT-27-FhK9AGlayKNLFoiuLutbcNUNPPcb3IfotmQEy2SFzVk5Gu5jkN-cas");
        initView();
        //TODO fromDB
        swipe_refresh_widget.post(new Runnable() {
            @Override
            public void run() {
                swipe_refresh_widget.setRefreshing(true);
            }
        });
        mainPresenter.loadData();
    }

    private void initView() {

        operationDialog_tfsq = new MaterialDialog.Builder(getActivity())
                .itemsGravity(GravityEnum.CENTER)
                .items(new String[]{"提交", "复制", "删除", "取消"})
                .itemsColor(Color.parseColor("#333333"))
                .itemsCallback(this)
                .build();
        operationDialog_fsq = new MaterialDialog.Builder(getActivity())
                .itemsGravity(GravityEnum.CENTER)
                .items(new String[]{"复制", "删除", "取消"})
                .itemsColor(Color.parseColor("#333333"))
                .itemsCallback(this)
                .build();
        operationDialog_fssq = new MaterialDialog.Builder(getActivity())
                .itemsGravity(GravityEnum.CENTER)
                .items(new String[]{"复制", "删除", "审核", "取消"})
                .itemsColor(Color.parseColor("#333333"))
                .itemsCallback(this)
                .build();
        operationDialog_ssq = new MaterialDialog.Builder(getActivity())
                .itemsGravity(GravityEnum.CENTER)
                .items(new String[]{"审核", "删除", "取消"})
                .itemsColor(Color.parseColor("#333333"))
                .itemsCallback(this)
                .build();
        operationDialog_sq = new MaterialDialog.Builder(getActivity())
                .itemsGravity(GravityEnum.CENTER)
                .items(new String[]{"删除", "取消"})
                .itemsColor(Color.parseColor("#333333"))
                .itemsCallback(this)
                .build();
        copyTypeDialog = new MaterialDialog.Builder(getActivity())
                .itemsGravity(GravityEnum.CENTER)
                .items(new String[]{"复制 电视 稿件", "复制 网页 稿件", "复制 微博 稿件", "复制 微信 稿件"})
                .itemsColor(Color.parseColor("#333333"))
                .itemsCallback(this)
                .build();
        filtrateTypeDialog = new MaterialDialog.Builder(getActivity())
                .itemsGravity(GravityEnum.CENTER)
                .items(new String[]{"全部", "网页", "微信", "微博", "电视"})
                .itemsColor(Color.parseColor("#333333"))
                .itemsCallback(this)
                .build();
        filtrateStatusDialog = new MaterialDialog.Builder(getActivity())
                .itemsGravity(GravityEnum.CENTER)
                .items(new String[]{"全部", "待提交", "待审核", "已通过", "已打回"})
                .itemsColor(Color.parseColor("#333333"))
                .itemsCallback(this)
                .build();
        filtrateTimeDialog = new MaterialDialog.Builder(getActivity())
                .itemsGravity(GravityEnum.CENTER)
                .items(new String[]{"全部", "一天", "两天", "三天", "七天"})
                .itemsColor(Color.parseColor("#333333"))
                .itemsCallback(this)
                .build();
        recycler_view_list.setLayoutManager(new LinearLayoutManager(activity));
        recycler_view_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!isLoadData & loadStatus) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && page < maxPage - 1) {
                        page += 1;
                        mainPresenter.loadData();
                    }
                }
            }
        });
        swipe_refresh_widget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                searchTitle = "";
                mainPresenter.loadData();
            }
        });
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.colorText));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorText));
        toolbar.setDrawingCacheBackgroundColor(getResources().getColor(R.color.colorText));
//        toolbar.setTitle("全部稿件");
        iv_tool_title.setText("全部稿件");
        ((MainActivity) activity).setSupportActionBar(toolbar);
        ((MainActivity) activity).initDrawerLayout(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!openAddbutton) {
                    showAdd();
                } else {
                    closeAdd();
                }
            }
        });

        ll_add_tv.setAlpha(0.0f);
        ll_add_web.setAlpha(0.0f);
        ll_add_weibo.setAlpha(0.0f);
        ll_add_wechat.setAlpha(0.0f);
        ll_add_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createManuscript(ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT);
                closeAdd();
            }
        });
        ll_add_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createManuscript(ManuscriptListInfo.MANUSCRIPT_TYPE_TV);
                closeAdd();
            }
        });
        ll_add_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createManuscript(ManuscriptListInfo.MANUSCRIPT_TYPE_CMS);
                closeAdd();
            }
        });
        ll_add_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createManuscript(ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO);
                closeAdd();
            }
        });
        ll_add_tv.setClickable(false);
        ll_add_web.setClickable(false);
        ll_add_weibo.setClickable(false);
        ll_add_wechat.setClickable(false);
        iv_shadow.setAlpha(0.0f);
        unitDistance = DisplayUtils.dip2px(activity, 56);
        origin = ll_add_tv.getTranslationY();

        iv_shadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAdd();
            }
        });
        iv_shadow.setClickable(false);

        swipe_refresh_widget.setColorSchemeResources(R.color.colorDYBlue, R.color.colorDarkDYBlue);

        //搜索栏设置
        search_view.setVoiceSearch(false);
        search_view.setCursorDrawable(R.drawable.shape_custom_cursor);
        search_view.setBackgroundResource(R.drawable.shape_search_background);
        search_view.setEllipsize(true);
        search_view.setHint("搜索");
//        search_view.setSuggestions(new String[]{"111", "222"}); TODO 添加下拉选项
        search_view.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchTitle = query;
                search_view.closeSearch();
                page = 1;
                mainPresenter.loadData();
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        setHasOptionsMenu(true);

        timeDialog = new StringArrayDialog(activity, timeStrings);
        timeDialog.setOnItemClickListener(new StringArrayDialog.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {
                    tv_search_by_time.setText("时间");
                } else {
                    tv_search_by_time.setText(timeStrings[position]);
                }
                dateranges = timeValue[position];
                page = 1;
                mainPresenter.loadData();
                timeDialog.dismiss();
            }
        });
        timeDialog.setTitle("时间");

        statusDialog = new StringArrayDialog(activity, statusStrings);
        statusDialog.setOnItemClickListener(new StringArrayDialog.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {
                    tv_search_by_state.setText("状态");
                } else {
                    tv_search_by_state.setText(statusStrings[position]);
                }
                states = statesValue[position];
                page = 1;
                mainPresenter.loadData();
                statusDialog.dismiss();
            }
        });
        statusDialog.setTitle("状态");

        typeDialog = new StringArrayDialog(activity, typeStrings);
        typeDialog.setOnItemClickListener(new StringArrayDialog.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {
                    tv_search_by_type.setText("类型");
                } else {
                    tv_search_by_type.setText(typeStrings[position]);
                }
                manuscriptType = typeValue[position];
                page = 1;
                mainPresenter.loadData();
                typeDialog.dismiss();
            }
        });
        typeDialog.setTitle("类型");

        tv_search_by_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtrateTimeDialog.show();
            }
        });
        tv_search_by_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtrateTypeDialog.show();
            }
        });
        tv_search_by_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtrateStatusDialog.show();
            }
        });
    }

    public void createManuscript(int type) {
        ManuscriptListInfo manuscriptListInfo = new ManuscriptListInfo(type);
        if (userInfo == null) {
            makeToast("还未登陆");
        }
        List<UserInfo.DataEntity.ColumnListModelEntity> columnListModel = userInfo.getData().getColumnListModel();
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
        long createTime = new Date().getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmm");
        String time1 = format1.format(createTime);
        String time2 = format2.format(createTime);
        manuscriptListInfo.createtime = TextUtils.isEmpty(manuscriptListInfo.createtime) ? time1 : manuscriptListInfo.createtime;
        manuscriptListInfo.username = TextUtils.isEmpty(manuscriptListInfo.username) ? userInfo.getData().getUserLoadModel().get(0).getName() : manuscriptListInfo.username;
        manuscriptListInfo.header = TextUtils.isEmpty(manuscriptListInfo.header) ? "新建稿件_" + time2 : manuscriptListInfo.header;
        manuscriptListInfo.usercode = TextUtils.isEmpty(manuscriptListInfo.usercode) ? userInfo.getData().getUserLoadModel().get(0).getId() : manuscriptListInfo.usercode;
        mainPresenter.createManuscript(manuscriptListInfo);
    }

    private void initUserView() {
        View userView = ((MainActivity) activity).getUserView();
        final TextView tv_user_name = (TextView) userView.findViewById(R.id.tv_user_name);
        final TextView tv_description = (TextView) userView.findViewById(R.id.tv_description);
        tv_user_name.post(new Runnable() {
            @Override
            public void run() {
                tv_user_name.setText(userInfo.getData().getUserLoadModel().get(0).getName());
            }
        });
        tv_description.post(new Runnable() {
            @Override
            public void run() {
                tv_description.setText(userInfo.getData().getUserLoadModel().get(0).getDescription());
            }
        });
    }

    @Override
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        initUserView();
    }

    @Override
    public void showTextDialog(String title, String message) {
        new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(message)
                .positiveText("确定")
                .show();
    }

    @Override
    public void showTextDialog(String message) {
        showTextDialog("提示", message);
    }


    @Override
    public void showSubmitDialog(final UserListAndTargetSystem system, final ManuscriptListInfo manuscriptListInfo) {
        SubmitDialog submitDialog = new SubmitDialog(activity, system, manuscriptListInfo);
        final MaterialDialog build = submitDialog.build();
        submitDialog.setSubmitListener(new SubmitDialog.SubmitListener() {
            @Override
            public void onSubmit(final RequestSubmitManuscript submitManuscript) {
                if (system.showChooseAuditor) {
                    String censorAuditor = submitManuscript.getCensorAuditor();
                    if (censorAuditor == null || censorAuditor.equals("") || censorAuditor.equals("请指定审核人")) {
                        new MaterialDialog.Builder(activity)
                                .title("提示")
                                .content("您还未选择审核人")
                                .positiveColor(getResources().getColor(R.color.colorDYBlue))
                                .positiveText("确定")
                                .build().show();
                        return;
                    }
                }
                if (submitManuscript.getTargetSystemIds() == null || submitManuscript.getTargetSystemIds().equals("")) {
                    new MaterialDialog.Builder(activity)
                            .title("提示")
                            .content("您还未选择分发目标")
                            .positiveText("继续提交")
                            .positiveColor(getResources().getColor(R.color.colorDYBlue))
                            .negativeText("返回修改")
                            .negativeColor(getResources().getColor(R.color.colorDYBlue))
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    mainPresenter.submitManuscript(submitManuscript, manuscriptListInfo);
                                    build.dismiss();
                                }
                            })
                            .build().show();
                    return;
                }
                mainPresenter.submitManuscript(submitManuscript, manuscriptListInfo);
                build.dismiss();
            }
        });
        build.show();
    }

    @Override
    public void showAuditDialog(UserListAndTargetSystem system, ManuscriptListInfo manuscriptListInfo) {
        AuditDialog auditDialog = new AuditDialog(activity, manuscriptListInfo, system);
        final MaterialDialog build = auditDialog.build();
        auditDialog.setListener(new AuditDialog.AuditListener() {
            @Override
            public void onAudit(ManuscriptListInfo info, RequestAuditManuscript auditManuscript) {
                build.dismiss();
                mainPresenter.auditManuscript(info, auditManuscript);
            }
        });
        build.show();
    }

    private void closeAdd() {
        openAddbutton = false;
        iv_shadow.setClickable(false);
        setBackKeyListener(null);
        ValueAnimator valueAnimator = ObjectAnimator
                .ofFloat(1.0F, 0.0F)
                .setDuration(170);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float v1 = -(unitDistance * value * 1) + origin;
                float v2 = -(unitDistance * value * 2) + origin;
                float v3 = -(unitDistance * value * 3) + origin;
                float v4 = -(unitDistance * value * 4) + origin;
                float angle = value * -(180.0f + 22.5f);
                iv_shadow.setAlpha(value);
                ll_add_tv.setAlpha(value);
                ll_add_web.setAlpha(value);
                ll_add_weibo.setAlpha(value);
                ll_add_wechat.setAlpha(value);
                ll_add_wechat.setTranslationY(v1);
                ll_add_weibo.setTranslationY(v2);
                ll_add_web.setTranslationY(v3);
                ll_add_tv.setTranslationY(v4);
                fab.setRotation(angle);

            }
        });
        valueAnimator.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                float v = input * 90;
                double v1 = v * 2 * (Math.PI / 360);
                double sin = Math.sin(v1);
                return (float) sin;
            }
        });

        ll_add_tv.setClickable(false);
        ll_add_web.setClickable(false);
        ll_add_weibo.setClickable(false);
        ll_add_wechat.setClickable(false);
        valueAnimator.start();
    }

    private void showAdd() {
        openAddbutton = true;
        iv_shadow.setClickable(true);
        setBackKeyListener(new BaseActivity.BackKeyListener() {
            @Override
            public boolean onBackKeyDown() {
                closeAdd();
                return true;
            }
        });
        ValueAnimator valueAnimator = ObjectAnimator
                .ofFloat(0.0F, 1.0F)
                .setDuration(170);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float v1 = -(unitDistance * value * 1) + origin;
                float v2 = -(unitDistance * value * 2) + origin;
                float v3 = -(unitDistance * value * 3) + origin;
                float v4 = -(unitDistance * value * 4) + origin;
                float angle = value * (180.0f + 22.5f);
                ll_add_tv.setAlpha(value);
                ll_add_web.setAlpha(value);
                iv_shadow.setAlpha(value);
                ll_add_weibo.setAlpha(value);
                ll_add_wechat.setAlpha(value);
                ll_add_wechat.setTranslationY(v1);
                ll_add_weibo.setTranslationY(v2);
                ll_add_web.setTranslationY(v3);
                ll_add_tv.setTranslationY(v4);
                fab.setRotation(angle);
            }
        });
        valueAnimator.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                float v = input * 90;
                double v1 = v * 2 * (Math.PI / 360);
                double sin = Math.sin(v1);
                return (float) sin;
            }
        });

        ll_add_tv.setClickable(true);
        ll_add_web.setClickable(true);
        ll_add_weibo.setClickable(true);
        ll_add_wechat.setClickable(true);
        valueAnimator.start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden == false) {
            setBackKeyListener(null);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        search_view.setMenuItem(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void refreshList(List<ManuscriptListInfo> list) {
        isLoadData = false;
        //防止在第一次加载时关闭刷新按钮失败
        swipe_refresh_widget.post(new Runnable() {
            @Override
            public void run() {
                swipe_refresh_widget.setRefreshing(false);
            }
        });
        loadStatus = true;
        if (list == null) {
            loadStatus = false;
            list = new ArrayList<>();
        }
        if (page == 1 && manuscriptList == null) {
            this.manuscriptList = list;
        } else if (page == 1 && manuscriptList != null) {
            this.manuscriptList.clear();
            this.manuscriptList.addAll(list);
        } else {
            this.manuscriptList.addAll(list);
        }
        ManuscriptListAdapter adapter = (ManuscriptListAdapter) recycler_view_list.getAdapter();
        if (adapter == null) {
            adapter = new ManuscriptListAdapter(activity, manuscriptList, page, maxPage, loadStatus);
            recycler_view_list.setAdapter(adapter);
            adapter.setManuscriptItemOnClickListener(new ManuscriptListAdapter.ManuscriptItemOnClickListener() {
                @Override
                public void onClick(ManuscriptListInfo manuscriptListInfo) {
                    enterRedact(manuscriptListInfo);
                }
            });
            adapter.setManuscriptItemLongClickListener(new ManuscriptListAdapter.ManuscriptItemLongClickListener() {
                @Override
                public void onLongClick(final ManuscriptListInfo manuscriptListInfo) {
                    MainFragment.this.selectedManuscriptListInfo = manuscriptListInfo;
                    int status = manuscriptListInfo.status;
                    if (resourceType.equals(RESOURCETYPE_MANUSCRIPT_ALL) || resourceType.equals(RESOURCETYPE_MANUSCRIPT_MAIN)) {
                        switch (status) {
                            case ManuscriptListInfo.MANUSCRIPT_STATUS_FAIL:
                                operationDialog_tfsq.show();
                                break;
                            case ManuscriptListInfo.MANUSCRIPT_STATUS_PASS:
                                operationDialog_fsq.show();
                                break;
                            case ManuscriptListInfo.MANUSCRIPT_STATUS_WAITING_SUBMIT:
                                operationDialog_tfsq.show();
                                break;
                            case ManuscriptListInfo.MANUSCRIPT_STATUS_WAITING_PENDING:
                                mainPresenter.queryIsCanAuditManuscript(manuscriptListInfo);
                                break;
                        }
                    } else if (resourceType.equals(RESOURCETYPE_CENSORTASK)) {
                        switch (status) {
                            case ManuscriptListInfo.MANUSCRIPT_STATUS_FAIL:
                                operationDialog_sq.show();
                                break;
                            case ManuscriptListInfo.MANUSCRIPT_STATUS_PASS:
                                operationDialog_sq.show();
                                break;
                            case ManuscriptListInfo.MANUSCRIPT_STATUS_WAITING_SUBMIT:
                                operationDialog_sq.show();
                                break;
                            case ManuscriptListInfo.MANUSCRIPT_STATUS_WAITING_PENDING:
                                operationDialog_ssq.show();
                                break;
                        }
                    }
                }
            });
        } else {
            adapter.setMaxPage(maxPage);
            adapter.setPage(page);
            adapter.setStatus(loadStatus);
            adapter.notifyDataSetChanged();
        }
        if (!loadStatus) {
            adapter.setListener(new ManuscriptListAdapter.ErrorItemOnClickListener() {
                @Override
                public void onClick(View view) {
                    mainPresenter.loadData();
                }
            });
        }
        if (resourceType.equals("0")) {
            iv_tool_title.setText("全部稿件");
        } else if (resourceType.equals("1")) {
            iv_tool_title.setText("我的稿件");
        } else if (resourceType.equals("8")) {
            iv_tool_title.setText("审核稿件");
        }
    }


    @Override
    public void removeLoading() {
        swipe_refresh_widget.setRefreshing(false);
    }

    @Override
    public void enterRedact(ManuscriptListInfo manuscriptListInfo) {
        mainPresenter.lockManuscript(manuscriptListInfo);
    }

    @Override
    public void enterRedact(boolean isLock, ManuscriptListInfo manuscriptListInfo) {
        if (!isLock) {
            return;
        }
        if (manuscriptListInfo.manuscripttype == ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT) {
            Intent intent = new Intent(activity, WeChatListActivity.class);
            intent.putExtra("manuscriptListInfo", manuscriptListInfo);
            this.startActivityForResult(intent, 0);
        } else {
            Intent intent = new Intent(activity, RedactActivity.class);
            intent.putExtra("manuscriptListInfo", manuscriptListInfo);
            this.startActivityForResult(intent, 0);
        }
    }

    @Override
    public void setMaxPage(int page) {
        this.maxPage = page;
    }

    @Override
    public void showAuditDialog(ManuscriptListInfo manuscriptListInfo, boolean isCanAuditManuscript) {
        if (isCanAuditManuscript) {
            operationDialog_fssq.show();
        } else {
            operationDialog_fsq.show();
        }
    }

    @Override
    public void refresh() {
        page = 1;
        searchTitle = "";
        mainPresenter.loadData();
    }

    @Override
    public int getPage() {
        return page;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getStates() {
        return states;
    }

    public String getManuscriptType() {
        return manuscriptType;
    }

    @Override
    public void showLoading() {
        swipe_refresh_widget.post(new Runnable() {
            @Override
            public void run() {
                swipe_refresh_widget.setRefreshing(true);
            }
        });
    }

    @Override
    public void makeToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setIsLoadData(boolean isLoadData) {
        this.isLoadData = isLoadData;
        showLoading();
    }

    public String getDateranges() {
        return dateranges;
    }

    @Override
    public String getSearchTitle() {
        return searchTitle;
    }

    @Override
    public Activity getViewActivity() {
        return this.getActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            ManuscriptListInfo manuscriptListInfo = (ManuscriptListInfo) data.getSerializableExtra("manuscriptListInfo");
            if (manuscriptListInfo != null) {
                mainPresenter.unLockManuscript(manuscriptListInfo);
            }
        }
        if (resultCode == Constant.RESULTCODE_NEED_REFRESH) {
            refresh();
        }
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
        page = 1;
        mainPresenter.loadData();
    }

    @Override
    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {

        if (dialog == filtrateStatusDialog) {
            if (text.equals("全部")) {
                tv_search_by_state.setText("状态");
                states = statesValue[0];
                page = 1;
                mainPresenter.loadData();
            } else if (text.equals("待提交")) {
                tv_search_by_state.setText("待提交");
                states = statesValue[1];
                page = 1;
                mainPresenter.loadData();
            } else if (text.equals("待审核")) {
                tv_search_by_state.setText("待审核");
                states = statesValue[2];
                page = 1;
                mainPresenter.loadData();
            } else if (text.equals("已通过")) {
                tv_search_by_state.setText("已通过");
                states = statesValue[3];
                page = 1;
                mainPresenter.loadData();
            } else if (text.equals("已打回")) {
                tv_search_by_state.setText("已打回");
                states = statesValue[4];
                page = 1;
                mainPresenter.loadData();
            }
            return;
        }
        if (dialog == filtrateTimeDialog) {
            if (text.equals("全部")) {
                tv_search_by_time.setText("时间");
                dateranges = timeValue[0];
                page = 1;
                mainPresenter.loadData();
            } else if (text.equals("一天")) {
                tv_search_by_time.setText("一天");
                dateranges = timeValue[1];
                page = 1;
                mainPresenter.loadData();
            } else if (text.equals("两天")) {
                tv_search_by_time.setText("两天");
                dateranges = timeValue[2];
                page = 1;
                mainPresenter.loadData();
            } else if (text.equals("三天")) {
                tv_search_by_time.setText("三天");
                dateranges = timeValue[3];
                page = 1;
                mainPresenter.loadData();
            } else if (text.equals("七天")) {
                tv_search_by_time.setText("七天");
                dateranges = timeValue[4];
                page = 1;
                mainPresenter.loadData();
            }
            return;
        }
        if (dialog == filtrateTypeDialog) {
            if (text.equals("全部")) {
                tv_search_by_type.setText("类型");
                manuscriptType = typeValue[0];
                page = 1;
                mainPresenter.loadData();
            } else if (text.equals("网页")) {
                tv_search_by_type.setText("网页");
                manuscriptType = typeValue[1];
                page = 1;
                mainPresenter.loadData();
            } else if (text.equals("微信")) {
                tv_search_by_type.setText("微信");
                manuscriptType = typeValue[2];
                page = 1;
                mainPresenter.loadData();
            } else if (text.equals("微博")) {
                tv_search_by_type.setText("微博");
                manuscriptType = typeValue[3];
                page = 1;
                mainPresenter.loadData();
            } else if (text.equals("电视")) {
                tv_search_by_type.setText("电视");
                manuscriptType = typeValue[4];
                page = 1;
                mainPresenter.loadData();
            }
            return;
        }
        if (selectedManuscriptListInfo == null) {
            return;
        }
        if (text.equals("取消")) {
            dialog.hide();
        } else if (text.equals("删除")) {
            mainPresenter.deleteManuscript(selectedManuscriptListInfo);
        } else if (text.equals("审核")) {
            mainPresenter.getAuditMessage(selectedManuscriptListInfo);
        } else if (text.equals("提交")) {
            mainPresenter.getSubmitMessage(selectedManuscriptListInfo);
        } else if (text.equals("复制")) {
            copyTypeDialog.show();
        } else if (text.equals("复制 网页 稿件")) {
            mainPresenter.copyManuscript(selectedManuscriptListInfo, ManuscriptListInfo.MANUSCRIPT_TYPE_CMS);
        } else if (text.equals("复制 微博 稿件")) {
            mainPresenter.copyManuscript(selectedManuscriptListInfo, ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO);
        } else if (text.equals("复制 微信 稿件")) {
            mainPresenter.copyManuscript(selectedManuscriptListInfo, ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT);
        } else if (text.equals("复制 电视 稿件")) {
            mainPresenter.copyManuscript(selectedManuscriptListInfo, ManuscriptListInfo.MANUSCRIPT_TYPE_TV);
        }


    }


}
