package com.dayang.dycmmedit.main.presenter;

import android.app.Activity;

import com.dayang.dycmmedit.http.BaseObserver;
import com.dayang.dycmmedit.http.RetrofitHelper;
import com.dayang.dycmmedit.info.FolderInfo;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.info.RequestAuditManuscript;
import com.dayang.dycmmedit.info.RequestLoginNewH5Info;
import com.dayang.dycmmedit.info.RequestSubmitManuscript;
import com.dayang.dycmmedit.info.ResultCommonInfo;
import com.dayang.dycmmedit.info.ResultGetCreFolder;
import com.dayang.dycmmedit.info.ResultGetCreUserList;
import com.dayang.dycmmedit.info.ResultLoadManuscriptInfo;
import com.dayang.dycmmedit.info.ResultManuscriptListInfo;
import com.dayang.dycmmedit.info.ResultSaveManuscriptInfo;
import com.dayang.dycmmedit.info.UserInfo;
import com.dayang.dycmmedit.info.UserListAndTargetSystem;
import com.dayang.dycmmedit.main.model.MainModel;
import com.dayang.dycmmedit.main.view.MainViewInterface;
import com.dayang.dycmmedit.utils.PrivilegeUtil;
import com.dayang.dycmmedit.utils.PublicResource;
import com.elvishew.xlog.XLog;

import org.apache.commons.net.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by 冯傲 on 2017/5/2.
 * e-mail 897840134@qq.com
 */

public class MainPresenterImpl implements MainPresenter {
    Activity activity;
    UserInfo info;
    private final MainModel mainModel;
    private final RequestLoginNewH5Info requestLoginNewH5Info;
    private final MainViewInterface mainViewInterface;
    String url1 = "getManuscriptByPage.do";
    String url2 = "getCensorTaskManuscriptByPage.do";

    public MainPresenterImpl(MainViewInterface mainViewInterface, String userId, String tokenId) {
        activity = mainViewInterface.getViewActivity();
        this.mainViewInterface = mainViewInterface;
        mainModel = new MainModel(activity, this);
        requestLoginNewH5Info = new RequestLoginNewH5Info(userId, tokenId);
    }

    @Override
    public void loadData() {
        mainViewInterface.setIsLoadData(true);
        if (info == null) {
            mainModel.login(requestLoginNewH5Info)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<ResultGetCreFolder>() {
                        @Override
                        public void onNext(@NonNull ResultGetCreFolder resultGetCreFolder) {
                            boolean status = resultGetCreFolder.isStatus();
                            if (status) {
                                ResultGetCreFolder.DataEntity data = resultGetCreFolder.getData();
                                List<FolderInfo> list = data.getList();
                                int totalPage = data.getTotalPage();
                                if (totalPage > 1) {
                                    PublicResource.getInstance().setAllFolder(false);
                                } else {
                                    PublicResource.getInstance().setAllFolder(true);
                                }
                                if (list != null && list.size() > 0) {
                                    FolderInfo folderInfo = new FolderInfo();
                                    folderInfo.setName("暂不选择选题");
                                    list.add(folderInfo);
                                    PublicResource.getInstance().setFolderInfos(list);
                                } else {
                                    List<FolderInfo> list1 = new ArrayList<>();
                                    FolderInfo folderInfo = new FolderInfo();
                                    folderInfo.setName("暂无选题");
                                    list1.add(folderInfo);
                                    PublicResource.getInstance().setFolderInfos(list1);
                                }
                            }
                            loadList();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            if (info == null) {
                                loadError("获取用户信息出错");
                            } else {
                                loadList();
                            }
                        }
                    });
        } else {
            loadList();
        }
    }

    @Override
    public void createManuscript(final ManuscriptListInfo info) {
        Map<String, String> map = new HashMap<>();
        map.put("manuscripttype", info.manuscripttype + "");
        map.put("usercode", this.info.getData().getUserLoadModel().get(0).getId());
        map.put("username", this.info.getData().getUserLoadModel().get(0).getName());
        if (info.manuscripttype == ManuscriptListInfo.MANUSCRIPT_TYPE_CMS) {
            map.put("iscomment", "0");
        }
        if (info.manuscripttype == ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT) {
            map.put("fathersonmark", "0");
        }
        mainViewInterface.showWaiting("创建稿件中");
        mainModel.saveManuscript(map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseObserver<ResultSaveManuscriptInfo>() {
            @Override
            public void onNext(@NonNull ResultSaveManuscriptInfo resultSaveManuscriptInfo) {
                mainViewInterface.removeWaiting();
                boolean status = resultSaveManuscriptInfo.isStatus();
                info.manuscriptid = resultSaveManuscriptInfo.getData().manuscriptid;
                info.header = resultSaveManuscriptInfo.getData().header;
                if (status) {
                    mainViewInterface.enterRedact(info);
                } else {
                    mainViewInterface.makeToast(resultSaveManuscriptInfo.getMsg());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mainViewInterface.removeWaiting();
                mainViewInterface.makeToast("创建稿件失败");
            }

            @Override
            public void onComplete() {
                mainViewInterface.removeWaiting();
            }
        });
    }

    @Override
    public void copyManuscript(final ManuscriptListInfo info, final int type) {
        Map<String, String> map = new HashMap<>();
        map.put("manuscripttype", type + "");
        map.put("manuscriptid", info.manuscriptid);
        map.put("usercode", PublicResource.getInstance().getUserCode());
        map.put("username", PublicResource.getInstance().getUserName());
        mainViewInterface.showWaiting("复制稿件中");
        mainModel.copyManuscript(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResultSaveManuscriptInfo>() {
                               @Override
                               public void onNext(@NonNull ResultSaveManuscriptInfo resultSaveManuscriptInfo) {
                                   try {
                                       mainViewInterface.removeWaiting();
                                       boolean status = resultSaveManuscriptInfo.isStatus();
                                       ManuscriptListInfo manuscriptListInfo;
                                       ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                       ObjectOutputStream oos = new ObjectOutputStream(bos);
                                       oos.writeObject(info);
                                       ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                                       ObjectInputStream ois = new ObjectInputStream(bis);
                                       manuscriptListInfo = (ManuscriptListInfo) ois.readObject();
                                       manuscriptListInfo.columnname = resultSaveManuscriptInfo.getData().columnname;
                                       manuscriptListInfo.columnid = resultSaveManuscriptInfo.getData().columnid;
                                       manuscriptListInfo.manuscriptid = resultSaveManuscriptInfo.getData().manuscriptid;
                                       manuscriptListInfo.manuscripttype = type;
                                       manuscriptListInfo.init(manuscriptListInfo.manuscripttype);
                                       if (status) {
                                           mainViewInterface.enterRedact(manuscriptListInfo);
                                       } else {
                                           mainViewInterface.makeToast(resultSaveManuscriptInfo.getMsg());
                                       }
                                   } catch (Exception e) {

                                   }
                               }

                               @Override
                               public void onError(@NonNull Throwable e) {
                                   mainViewInterface.removeWaiting();
                                   mainViewInterface.makeToast("复制稿件失败");
                               }

                               @Override
                               public void onComplete() {
                                   mainViewInterface.removeWaiting();
                               }
                           }

                );
    }

    @Override
    public void deleteManuscript(ManuscriptListInfo info) {
        boolean hasPrivilege = PrivilegeUtil.hasPrivilege(PrivilegeUtil.PRIVILEGE_DELETE, info);
        if (!hasPrivilege) {
            mainViewInterface.showTextDialog("对不起，您没有删除权限");
        }
        Map<String, String> map = new HashMap<>();
        map.put("manuscriptIds", info.manuscriptid);
        map.put("userid", info.usercode);
        mainModel.delManuscript(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResultCommonInfo>() {
                    @Override
                    public void onNext(@NonNull ResultCommonInfo resultCommonInfo) {
                        if (resultCommonInfo.isStatus()) {
                            mainViewInterface.makeToast("删除成功");
                            mainViewInterface.refresh();
                        } else {
                            mainViewInterface.makeToast("删除失败");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mainViewInterface.makeToast("删除失败");
                    }
                });
    }

    @Override
    public void submitManuscript(RequestSubmitManuscript system, ManuscriptListInfo info) {
        Map<String, String> map = new HashMap<>();
        map.put("manuscriptIds", info.manuscriptid);
        map.put("userCode", info.usercode);
        map.put("targetSystemIds", system.getTargetSystemIds());
        map.put("tokenid", PublicResource.getInstance().getToken());
        map.put("username", PublicResource.getInstance().getUserName());
        map.put("censorAuditor", system.getCensorAuditor());
        map.put("censorAuditorId", system.getCensorAuditorId());
        mainViewInterface.showWaiting("提交稿件中");
        mainModel.submitManuscript(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResultCommonInfo>() {
                    @Override
                    public void onNext(@NonNull ResultCommonInfo resultCommonInfo) {
                        mainViewInterface.removeWaiting();
                        boolean status = resultCommonInfo.isStatus();
                        if (status) {
                            mainViewInterface.showTextDialog("提交稿件成功");
                            mainViewInterface.refresh();
                        } else {
                            mainViewInterface.showTextDialog("提交稿件失败" + resultCommonInfo.getMsg());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mainViewInterface.removeWaiting();
                        mainViewInterface.showTextDialog("提交稿件失败" + e.getMessage());
                    }
                });
    }

    @Override
    public void getSubmitMessage(final ManuscriptListInfo info) {

        //TODO 判断微信稿件长度 web稿件发布方式 新媒体栏目
        boolean hasPrivilege = PrivilegeUtil.hasPrivilege(PrivilegeUtil.PRIVILEGE_SUBMIT, info);
        if (!hasPrivilege) {
            mainViewInterface.showTextDialog("对不起你没有权限提交");
            return;
        }
        mainViewInterface.showWaiting("获取提交信息");
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("manuscriptId", info.manuscriptid);
        RetrofitHelper.getInstance(activity).loadManuscript(stringMap).flatMap(new Function<ResultLoadManuscriptInfo, Observable<UserListAndTargetSystem>>() {
            @Override
            public Observable<UserListAndTargetSystem> apply(@NonNull ResultLoadManuscriptInfo resultLoadManuscriptInfo) throws Exception {

                if (!resultLoadManuscriptInfo.isStatus()) {
                    throw new Exception("获取稿件详情失败");
                }
                ManuscriptListInfo manuscript = resultLoadManuscriptInfo.getData().getManuscript();
                manuscript.init(manuscript.manuscripttype);
                ArrayList<String> columnNameList = new ArrayList<>();
                columnNameList.add("暂不指派栏目");
                ArrayList<String> columnIdList = new ArrayList<>();
                for (int j = 0; j < MainPresenterImpl.this.info.getData().getColumnListModel().size(); j++) {
                    columnNameList.add(MainPresenterImpl.this.info.getData().getColumnListModel().get(j).getName());
                    columnIdList.add(MainPresenterImpl.this.info.getData().getColumnListModel().get(j).getId());
                }
                manuscript.columns = columnNameList;
                manuscript.columnsID = columnIdList;

                String columnname = manuscript.columnname;
                String header = manuscript.header;
                String h5content = manuscript.h5content;
                String textcontent = manuscript.textcontent;
                if (columnname.equals("")) {
                    throw new Exception("栏目不能为空");
                }
                List<UserInfo.DataEntity.ColumnListModelEntity> columnNameList1 = PublicResource.getInstance().getColumnNameList();
                int flag = -1;
                for (UserInfo.DataEntity.ColumnListModelEntity entity : columnNameList1) {
                    if (entity.getName().equals(columnname)) {
                        flag = 1;
                    }
                }
                if (flag != 1) {
                    throw new Exception("栏目不合法");
                }
                if (header.equals("")) {
                    throw new Exception("标题不能为空");
                }
                if (h5content.equals("") && manuscript.manuscripttype != ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO) {
                    throw new Exception("内容不能为空");
                }
                if (textcontent.equals("") && manuscript.manuscripttype == ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO) {
                    throw new Exception("内容不能为空");
                }
                return mainModel.getSubmitMessage(manuscript);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserListAndTargetSystem>() {
                    @Override
                    public void onNext(@NonNull UserListAndTargetSystem userListAndTargetSystem) {
                        mainViewInterface.removeWaiting();
                        mainViewInterface.showSubmitDialog(userListAndTargetSystem, info);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mainViewInterface.removeWaiting();
                        mainViewInterface.showTextDialog(e.getMessage());
                    }
                });
    }

    @Override
    public void getAuditMessage(final ManuscriptListInfo info) {
        boolean hasPrivilege = PrivilegeUtil.hasPrivilege(PrivilegeUtil.PRIVILEGE_AUDIT, info);
        if (!hasPrivilege) {
            mainViewInterface.showTextDialog("对不起你没有审核提交");
            return;
        }
        mainViewInterface.showWaiting("获取审核信息");
        mainModel.getAuditMessage(info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserListAndTargetSystem>() {
                    @Override
                    public void onNext(@NonNull UserListAndTargetSystem userListAndTargetSystem) {
                        mainViewInterface.removeWaiting();
                        mainViewInterface.showAuditDialog(userListAndTargetSystem, info);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mainViewInterface.removeWaiting();
                        mainViewInterface.showTextDialog(e.getMessage());
                    }
                });
    }


    @Override
    public void auditManuscript(ManuscriptListInfo info, RequestAuditManuscript auditManuscript) {
        boolean hasPrivilege = PrivilegeUtil.hasPrivilege(PrivilegeUtil.PRIVILEGE_AUDIT, info);
        if (!hasPrivilege) {
            mainViewInterface.showTextDialog("对不起，您没有审核权限");
        }
        mainViewInterface.showWaiting("审核稿件中");
        mainModel.auditManuscript(auditManuscript).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResultCommonInfo>() {
                    @Override
                    public void onNext(@NonNull ResultCommonInfo resultCommonInfo) {
                        mainViewInterface.removeWaiting();
                        if (resultCommonInfo.isStatus()) {
                            mainViewInterface.showTextDialog("审核成功");
                            mainViewInterface.refresh();
                        } else {
                            mainViewInterface.showTextDialog("审核失败");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mainViewInterface.removeWaiting();
                        mainViewInterface.showTextDialog(e.getMessage());
                    }
                });
    }

    @Override
    public void lockManuscript(final ManuscriptListInfo info) {
        mainViewInterface.showWaiting("锁定稿件中");
        Map<String, String> map = new HashMap<>();
        map.put("manuscriptid", info.manuscriptid);
        map.put("userid", info.usercode);
        map.put("username", PublicResource.getInstance().getUserName());
        map.put("manuscriptCreater", info.username);
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("manuscriptStatus", info.status);
        List<String> authorityList = PublicResource.getInstance().getAuthorityList();
        String privilegeIds = "";
        for (int i = 0; i < authorityList.size(); i++) {
            privilegeIds = i + 1 == authorityList.size() ? privilegeIds + authorityList.get(i) : privilegeIds + authorityList.get(i) + ",";
        }
        map.put("privilegeIds", privilegeIds);
        mainModel.lockManuscript(map, map2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResultLoadManuscriptInfo>() {
                    @Override
                    public void onNext(@NonNull ResultLoadManuscriptInfo resultCommonInfo) {
                        mainViewInterface.removeWaiting();
                        if (!resultCommonInfo.isStatus()) {
                            mainViewInterface.makeToast("稿件详情获取失败");
                            mainViewInterface.enterRedact(false, info);
                            return;
                        }
                        ManuscriptListInfo manuscript = resultCommonInfo.getData().getManuscript();
                        manuscript.init(manuscript.manuscripttype);
                        ArrayList<String> columnNameList = new ArrayList<>();
                        columnNameList.add("暂不指派栏目");
                        ArrayList<String> columnIdList = new ArrayList<>();
                        for (int j = 0; j < MainPresenterImpl.this.info.getData().getColumnListModel().size(); j++) {
                            columnNameList.add(MainPresenterImpl.this.info.getData().getColumnListModel().get(j).getName());
                            columnIdList.add(MainPresenterImpl.this.info.getData().getColumnListModel().get(j).getId());
                        }
                        manuscript.columns = columnNameList;
                        manuscript.columnsID = columnIdList;
                        info.copy(manuscript);
                        mainViewInterface.enterRedact(true, info);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mainViewInterface.makeToast(e.getMessage());
                        mainViewInterface.removeWaiting();
                        mainViewInterface.enterRedact(false, info);
                    }
                });
    }

    @Override
    public void unLockManuscript(final ManuscriptListInfo info) {
        mainViewInterface.showWaiting("解锁稿件中");
        Map<String, String> map = new HashMap<>();
        map.put("manuscriptid", info.manuscriptid);
        map.put("userid", info.usercode);
        mainModel.unlockManuscript(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResultCommonInfo>() {
                    @Override
                    public void onNext(@NonNull ResultCommonInfo resultCommonInfo) {
                        mainViewInterface.removeWaiting();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mainViewInterface.removeWaiting();
                    }
                });
    }

    @Override
    public void queryIsCanAuditManuscript(final ManuscriptListInfo info) {
        mainViewInterface.showWaiting("获取审核权限");
        Map<String, String> map = new HashMap<>();
        map.put("manuscriptid", info.manuscriptid);
        map.put("userid", info.usercode);
        mainModel.isCanAuditManuscript(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResultCommonInfo>() {
                    @Override
                    public void onNext(@NonNull ResultCommonInfo resultCommonInfo) {
                        mainViewInterface.showAuditDialog(info, resultCommonInfo.isStatus());
                        mainViewInterface.removeWaiting();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mainViewInterface.showAuditDialog(info, false);
                        mainViewInterface.removeWaiting();
                    }
                });
    }

    private void loadError(String text) {
        mainViewInterface.makeToast(text);
        mainViewInterface.refreshList(null);
    }

    public void loadList() {
        int page = mainViewInterface.getPage();
        String dateranges = mainViewInterface.getDateranges();
        String resourceType = mainViewInterface.getResourceType();
        String states = mainViewInterface.getStates();
        String searchTitle = mainViewInterface.getSearchTitle();
        String manuscriptType = mainViewInterface.getManuscriptType();
        String id = info.getData().getUserLoadModel().get(0).getWorkNo();
        List<UserInfo.DataEntity.PrivilegeLoadModelEntity> privilegeLoadModel = info.getData().getPrivilegeLoadModel();
        String privilegeIds = "";
        for (int i = 0; i < privilegeLoadModel.size(); i++) {
            privilegeIds = i + 1 == privilegeLoadModel.size() ? privilegeIds + privilegeLoadModel.get(i).getId() : privilegeIds + privilegeLoadModel.get(i).getId() + ",";
        }
        List<UserInfo.DataEntity.ColumnListModelEntity> columnListModel = info.getData().getColumnListModel();
        String columnListIds = "";
        for (int i = 0; i < columnListModel.size(); i++) {
            columnListIds = i + 1 == columnListModel.size() ? columnListIds + columnListModel.get(i).getId() : columnListIds + columnListModel.get(i).getId() + ",";
        }
        Map<String, String> fields = new HashMap<>();
        fields.put("resourceType", resourceType);
        fields.put("page", page + "");
        fields.put("states", states);
        fields.put("manuscriptType", manuscriptType);
        fields.put("title", searchTitle);
        fields.put("dateranges", dateranges);
        fields.put("usercode", PublicResource.getInstance().getUserCode());
        fields.put("privilegeIds", privilegeIds);
        fields.put("columnListIds", columnListIds);
        fields.put("allColumnIds", "");
        String url = url1;
        if (resourceType.equals("8")) {
            url = url2;
        }
        mainModel.getListData(fields, url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ResultManuscriptListInfo, List<ManuscriptListInfo>>() {
                    @Override
                    public List<ManuscriptListInfo> apply(@NonNull ResultManuscriptListInfo resultManuscriptListInfo) throws Exception {
                        boolean status = resultManuscriptListInfo.isStatus();
                        if (!status) {
                            throw new Exception("信息获取错误");
                        }
                        mainViewInterface.setMaxPage(resultManuscriptListInfo.getData().getTotalPage());
                        List<ManuscriptListInfo> list = resultManuscriptListInfo.getData().getList();
                        for (int i = 0; i < list.size(); i++) {
                            ManuscriptListInfo manuscriptListInfo = list.get(i);
                            manuscriptListInfo.init(manuscriptListInfo.manuscripttype);
                            ArrayList<String> columnNameList = new ArrayList<>();
                            columnNameList.add("暂不指派栏目");
                            ArrayList<String> columnIdList = new ArrayList<>();
                            for (int j = 0; j < info.getData().getColumnListModel().size(); j++) {
                                columnNameList.add(info.getData().getColumnListModel().get(j).getName());
                                columnIdList.add(info.getData().getColumnListModel().get(j).getId());
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
                        mainViewInterface.refreshList(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        loadError(e.getMessage());
                    }
                });
    }

    public void setUserInfo(UserInfo userInfo) {
        this.info = userInfo;
        mainViewInterface.setUserInfo(info);
    }
}
