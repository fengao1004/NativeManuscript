package com.dayang.dycmmedit.redact.presenter;

import android.text.TextUtils;

import com.dayang.dycmmedit.adapter.WeChatListAdapter;
import com.dayang.dycmmedit.http.BaseObserver;
import com.dayang.dycmmedit.info.ExchangeManuscript;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.info.ResultCommonInfo;
import com.dayang.dycmmedit.info.ResultLoadManuscriptInfo;
import com.dayang.dycmmedit.info.ResultSaveManuscriptInfo;
import com.dayang.dycmmedit.info.UserInfo;
import com.dayang.dycmmedit.redact.model.WeChatListModel;
import com.dayang.dycmmedit.redact.view.WeChatListActivity;
import com.dayang.dycmmedit.redact.view.WeChatListView;
import com.dayang.dycmmedit.utils.PrivilegeUtil;
import com.dayang.dycmmedit.utils.PublicResource;
import com.elvishew.xlog.XLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 冯傲 on 2017/5/13.
 * e-mail 897840134@qq.com
 */

public class WeChatListPresenterImpl implements WeChatListPresenterInterface {

    private WeChatListActivity activity;
    private WeChatListView weChatListView;
    private final WeChatListModel weChatListModel;
    private List<ManuscriptListInfo> manuscriptList;
    private int count;

    @Override
    public void loadList(ManuscriptListInfo createManuscriptInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("manuscriptId", createManuscriptInfo.manuscriptid);
        weChatListView.showLoading();
        weChatListModel.loadManuscript(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ResultLoadManuscriptInfo, List<ManuscriptListInfo>>() {
                    @Override
                    public List<ManuscriptListInfo> apply(@NonNull ResultLoadManuscriptInfo resultManuscriptListInfo) throws Exception {
                        boolean status = resultManuscriptListInfo.isStatus();
                        if (!status) {
                            throw new Exception("信息获取错误");
                        }
                        List<ManuscriptListInfo> list = resultManuscriptListInfo.getData().getManuscriptList();
                        List<UserInfo.DataEntity.ColumnListModelEntity> columnListModel = PublicResource.getInstance().getColumnNameList();
                        for (int i = 0; i < list.size(); i++) {
                            ManuscriptListInfo manuscriptListInfo = list.get(i);
                            manuscriptListInfo.init(manuscriptListInfo.manuscripttype);
                            ArrayList<String> columnNameList = new ArrayList<>();
                            columnNameList.add("暂不指派栏目");
                            ArrayList<String> columnIdList = new ArrayList<>();
                            for (int j = 0; j < columnListModel.size(); j++) {
                                columnNameList.add(columnListModel.get(j).getName());
                                columnIdList.add(columnListModel.get(j).getId());
                            }
                            manuscriptListInfo.columns = columnNameList;
                            manuscriptListInfo.columnsID = columnIdList;
                        }
                        return list;
                    }
                })
                .subscribe(new BaseObserver<List<ManuscriptListInfo>>() {
                    @Override
                    public void onNext(@NonNull List<ManuscriptListInfo> list) {
                        weChatListView.removeLoading();
                        manuscriptList = list;
                        weChatListView.refreshList(manuscriptList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        weChatListView.removeLoading();
                        weChatListView.makeToast("获取微信稿件列表失败");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        weChatListView.removeWaiting();
                    }
                });
    }

    @Override
    public void addManuscript(final WeChatListAdapter weChatListAdapter) {
        if (manuscriptList.size() >= 8) {
            weChatListView.makeToast("已经达到微信稿件最大数目,无法创建更多稿件");
            return;
        }
        if (manuscriptList.size() == 0) {
            weChatListView.makeToast("未知错误");
            return;
        }
        boolean hasPrivilege = PrivilegeUtil.hasPrivilege(PrivilegeUtil.PRIVILEGE_WRITE, manuscriptList.get(0));
        if (!hasPrivilege) {
            weChatListView.makeToast("您没有新建稿件权限");
            return;
        }
        final ManuscriptListInfo manuscriptListInfo = new ManuscriptListInfo(ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT);
        List<UserInfo.DataEntity.ColumnListModelEntity> columnListModel = PublicResource.getInstance().getColumnNameList();
        String userCode = PublicResource.getInstance().getUserCode();
        String userName = PublicResource.getInstance().getUserName();
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
        manuscriptListInfo.username = TextUtils.isEmpty(manuscriptListInfo.username) ? userName : manuscriptListInfo.username;
        manuscriptListInfo.header = TextUtils.isEmpty(manuscriptListInfo.header) ? "新建稿件_" + time2 : manuscriptListInfo.header;
        manuscriptListInfo.usercode = TextUtils.isEmpty(manuscriptListInfo.usercode) ? userCode : manuscriptListInfo.usercode;
        Map<String, String> map = new HashMap<>();
        map.put("manuscripttype", manuscriptListInfo.manuscripttype + "");
        map.put("usercode", PublicResource.getInstance().getUserCode());
        map.put("username", PublicResource.getInstance().getUserName());
        map.put("fatherid", manuscriptList.get(0).manuscriptid);
        int fathersonmark;
        if (manuscriptList.size() == 0) {
            fathersonmark = 0;
        } else {
            fathersonmark = 1;
        }
        weChatListView.showWaiting("创建稿件中");
        weChatListModel.createManuscript(map, fathersonmark).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseObserver<ResultSaveManuscriptInfo>() {
            @Override
            public void onNext(@NonNull ResultSaveManuscriptInfo resultSaveManuscriptInfo) {
                weChatListView.removeWaiting();
                boolean status = resultSaveManuscriptInfo.isStatus();
                if (status) {
                    weChatListView.setHasChange(true);
                    manuscriptListInfo.manuscriptid = resultSaveManuscriptInfo.getData().manuscriptid;
                    manuscriptListInfo.arrayindex = resultSaveManuscriptInfo.getData().arrayindex;
                    manuscriptListInfo.fatherid = resultSaveManuscriptInfo.getData().fatherid;
                    manuscriptListInfo.fathersonmark = resultSaveManuscriptInfo.getData().fathersonmark;
                    manuscriptListInfo.header = resultSaveManuscriptInfo.getData().header;
                    manuscriptList.add(manuscriptListInfo);
                    weChatListAdapter.notifyItemInserted(manuscriptList.size() - 1);
                    weChatListView.enterRedact(manuscriptListInfo);
                } else {
                    weChatListView.makeToast(resultSaveManuscriptInfo.getMsg());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                weChatListView.removeWaiting();
                weChatListView.makeToast("创建稿件失败");
            }

            @Override
            public void onComplete() {
                weChatListView.removeWaiting();
            }
        });
    }

    @Override
    public void deleteManuscript(final WeChatListAdapter weChatListAdapter, final ManuscriptListInfo createManuscriptInfo) {
        if (manuscriptList.size() == 0) {
            weChatListView.makeToast("请从主列表删除");
            return;
        }
        if (createManuscriptInfo.fathersonmark == 0) {
            weChatListView.makeToast("删除父稿件请从主列表删除");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("manuscriptIds", createManuscriptInfo.manuscriptid);
        map.put("userid", createManuscriptInfo.usercode);
        weChatListView.showWaiting("删除稿件");
        weChatListModel.delManuscript(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResultCommonInfo>() {
                               @Override
                               public void onNext(@NonNull ResultCommonInfo resultCommonInfo) {

                                   weChatListView.removeWaiting();
                                   boolean status = resultCommonInfo.isStatus();
                                   if (status) {
                                       weChatListView.setHasChange(true);
                                       manuscriptList.remove(createManuscriptInfo);
                                       weChatListAdapter.notifyDataSetChanged();
                                   } else {
                                       weChatListView.makeToast("删除失败");
                                   }
                               }

                               @Override
                               public void onError(@NonNull Throwable e) {
                                   weChatListView.removeWaiting();
                                   weChatListView.makeToast("删除失败");
                               }
                           }
                );
    }

    @Override
    public void exchange(ArrayList<ExchangeManuscript> exchangeManuscripts) {
        boolean hasPrivilege = PrivilegeUtil.hasPrivilege(PrivilegeUtil.PRIVILEGE_WRITE, manuscriptList.get(0));
        if (!hasPrivilege) {
            weChatListView.makeToast("您没有权限交换稿件");
            weChatListView.refresh();
            return;
        }
        final int size = exchangeManuscripts.size();
        count = 0;
        Observable.fromIterable(exchangeManuscripts)
                .flatMap(new Function<ExchangeManuscript, Observable<ResultCommonInfo>>() {
                    @Override
                    public Observable<ResultCommonInfo> apply(@NonNull ExchangeManuscript exchangeManuscript) throws Exception {
                        String downManuscriptId = exchangeManuscript.downManuscriptId;
                        String upManuscriptId = exchangeManuscript.upManuscriptId;
                        int isNO1 = exchangeManuscript.isNO1;
                        return weChatListModel.exchange(upManuscriptId, downManuscriptId, isNO1);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResultCommonInfo>() {
                    @Override
                    public void onNext(@NonNull ResultCommonInfo resultCommonInfo) {
                        XLog.i("onNext: " + resultCommonInfo.getMsg());
                        count++;
                        if (count == size) {
                            weChatListView.refresh();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public WeChatListPresenterImpl(WeChatListView weChatListView) {
        this.weChatListView = weChatListView;
        activity = (WeChatListActivity) weChatListView.getViewActivity();
        weChatListModel = new WeChatListModel(activity);
    }
}
