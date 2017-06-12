package com.dayang.dycmmedit.main.model;

import android.content.Context;

import com.dayang.dycmmedit.http.RetrofitHelper;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.info.RequestAuditManuscript;
import com.dayang.dycmmedit.info.RequestCheckDogInfo;
import com.dayang.dycmmedit.info.RequestLoginNewH5Info;
import com.dayang.dycmmedit.info.ResultCensorStrategyExist;
import com.dayang.dycmmedit.info.ResultCommonInfo;
import com.dayang.dycmmedit.info.ResultGetCreFolder;
import com.dayang.dycmmedit.info.ResultGetCreUserList;
import com.dayang.dycmmedit.info.ResultGetParamInfo;
import com.dayang.dycmmedit.info.ResultGetTVTargetSystem;
import com.dayang.dycmmedit.info.ResultGetTargetweiboSystem;
import com.dayang.dycmmedit.info.ResultGetTargetweixinSystem;
import com.dayang.dycmmedit.info.ResultGetWebTargetSystem;
import com.dayang.dycmmedit.info.ResultLoadManuscriptInfo;
import com.dayang.dycmmedit.info.ResultManuscriptListInfo;
import com.dayang.dycmmedit.info.ResultSaveManuscriptInfo;
import com.dayang.dycmmedit.info.UserInfo;
import com.dayang.dycmmedit.info.UserListAndTargetSystem;
import com.dayang.dycmmedit.info.UserModel;
import com.dayang.dycmmedit.main.presenter.MainPresenterImpl;
import com.dayang.dycmmedit.utils.PublicResource;
import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.net.util.Base64;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * Created by 冯傲 on 2017/5/2.
 * e-mail 897840134@qq.com
 */

public class MainModel {
    Context context;
    final String uploadFilePahtFlagFtp = "FTPUPLOADURL";
    final String uploadFilePahtFlagHttp = "HTTPUPLOADURL";
    final String streamPahtFlag = "streamAddr";
    final String ftpCallBackPahtFlag = "FTPCALLBACK";
    final MainPresenterImpl mainPresenter;

    public MainModel(Context context, MainPresenterImpl mainPresenter) {
        this.mainPresenter = mainPresenter;
        this.context = context;
    }

    public Observable<ResultGetCreFolder> login(final RequestLoginNewH5Info requestLoginNewH5Info) {
        return RetrofitHelper.getInstance(context).checkDog()
                .flatMap(new Function<RequestCheckDogInfo, Observable<ResultGetParamInfo>>() {
                    @Override
                    public Observable<ResultGetParamInfo> apply(@NonNull RequestCheckDogInfo requestCheckDogInfo) throws Exception {
                        if (!requestCheckDogInfo.isStatus()) {
                            throw new Exception("请开启加密狗");
                        }
                        return RetrofitHelper.getInstance(context).getParamInfo();
                    }
                }).flatMap(new Function<ResultGetParamInfo, Observable<UserInfo>>() {
                    @Override
                    public Observable<UserInfo> apply(@NonNull ResultGetParamInfo resultGetParamInfo) throws Exception {
                        List<ResultGetParamInfo.DataEntity> data = resultGetParamInfo.getData();
                        String ftpPath = "";
                        String httpPath = "";
                        String streamPath = "";
                        String ftpCallBackPath = "";
                        for (int i = 0; i < data.size(); i++) {
                            String servicecode = data.get(i).getServicecode();
                            switch (servicecode) {
                                case uploadFilePahtFlagFtp:
                                    ftpPath = data.get(i).getStreampath();
                                    break;
                                case uploadFilePahtFlagHttp:
                                    httpPath = data.get(i).getStreampath();
                                    break;
                                case streamPahtFlag:
                                    streamPath = data.get(i).getStreampath();
                                    break;
                                case ftpCallBackPahtFlag:
                                    ftpCallBackPath = data.get(i).getStreampath();
                                    break;
                            }
                        }
                        if (httpPath.equals("") && !ftpCallBackPath.equals("")) {
                            PublicResource.getInstance().setStorageURL(ftpPath + "/phone");
                            PublicResource.getInstance().setFileStatusNotifyURL(ftpCallBackPath);
                        } else {
                            PublicResource.getInstance().setStorageURL(httpPath);
                            PublicResource.getInstance().setFileStatusNotifyURL("");
                        }
                        PublicResource.getInstance().setStreamPath(streamPath);
                        return RetrofitHelper.getInstance(context).loginNewH5(requestLoginNewH5Info.getUserId(), requestLoginNewH5Info.getTokenId());
                    }
                }).flatMap(new Function<UserInfo, Observable<ResultGetCreUserList>>() {
                    @Override
                    public Observable<ResultGetCreUserList> apply(@NonNull UserInfo info) throws Exception {
                        if (info.isStatus()) {
                            mainPresenter.setUserInfo(info);
                            PublicResource.getInstance().setUserCode(info.getData().getUserLoadModel().get(0).getId());
                            PublicResource.getInstance().setUserName(info.getData().getUserLoadModel().get(0).getName());
                            PublicResource.getInstance().setColumnNameList(info.getData().getColumnListModel());
                            PublicResource.getInstance().setPassword("0003");
                            PublicResource.getInstance().setToken("asdgw-fvwe-cwsewf");
                            PublicResource.getInstance().setAuthorityList(info.getData().getPrivilegeLoadModel());
                        } else {
                            throw new Exception("获取用户信息出错");
                        }
                        Map<String, String> map = new HashMap<>();
                        map.put("userId", PublicResource.getInstance().getUserCode());
                        map.put("userName", PublicResource.getInstance().getUserName());
                        map.put("tokenId", PublicResource.getInstance().getToken());
                        map.put("password", PublicResource.getInstance().getPassword());
                        return RetrofitHelper.getInstance(context).getCreUserList(map);
                    }
                }).flatMap(new Function<ResultGetCreUserList, Observable<ResultGetCreFolder>>() {
                    @Override
                    public Observable<ResultGetCreFolder> apply(@NonNull ResultGetCreUserList resultGetCreUserList) throws Exception {
                        boolean status = resultGetCreUserList.isStatus();
                        List<UserModel> data = resultGetCreUserList.getData();
                        if (status && data != null && data.size() > 0) {
                            PublicResource.getInstance().setUserList(data);
                        }
                        Map<String, String> map = new HashMap<>();
                        map.put("userId", PublicResource.getInstance().getUserCode());
                        map.put("userName", PublicResource.getInstance().getUserName());
                        map.put("tokenId", PublicResource.getInstance().getToken());
                        return RetrofitHelper.getInstance(context).getCreFolder(map);
                    }
                });
    }

    public Observable<ResultManuscriptListInfo> getListData(Map<String, String> fields, String url) {
        return RetrofitHelper.getInstance(context).getManuscriptByPage(fields, url);
    }

    public Observable<ResultSaveManuscriptInfo> saveManuscript(Map<String, String> fields) {
        return RetrofitHelper.getInstance(context).saveManuscript(fields);
    }

    public Observable<ResultSaveManuscriptInfo> copyManuscript(Map<String, String> fields) {
        return RetrofitHelper.getInstance(context).copyManuscript(fields);
    }

    public Observable<ResultCommonInfo> delManuscript(Map<String, String> fields) {
        return RetrofitHelper.getInstance(context).delManuscript(fields);
    }

    public Observable<ResultCommonInfo> submitManuscript(Map<String, String> fields) {
        return RetrofitHelper.getInstance(context).submitManuscript(fields);
    }

    public Observable<ResultCommonInfo> auditManuscript(final RequestAuditManuscript auditManuscript) {
        return Observable.just(auditManuscript).flatMap(new Function<RequestAuditManuscript, Observable<ResultCommonInfo>>() {
            @Override
            public Observable<ResultCommonInfo> apply(@NonNull RequestAuditManuscript auditManuscript) throws Exception {
                ResultCommonInfo resultCommonInfo = new ResultCommonInfo();
                resultCommonInfo.setStatus(true);
                if (auditManuscript.auditorName.equals("")) {
                    return Observable.just(resultCommonInfo);
                } else {
                    Map<String, String> map = new HashMap();
                    map.put("manuscriptId", auditManuscript.manuscriptIds);
                    map.put("censorAuditorId", auditManuscript.auditorId);
                    map.put("censorAuditor", auditManuscript.auditorName);
                    return RetrofitHelper.getInstance(context).auditAddCensorAuditor(map);
                }
            }
        }).flatMap(new Function<ResultCommonInfo, Observable<ResultCommonInfo>>() {
            @Override
            public Observable<ResultCommonInfo> apply(@NonNull ResultCommonInfo resultCommonInfo) throws Exception {
                if (!resultCommonInfo.isStatus()) {
                    throw new Exception("保存下级审核员失败");
                }
                Map<String, String> map1 = new HashMap();
                map1.put("manuscriptids", auditManuscript.manuscriptIds);
                map1.put("censorOpinion", auditManuscript.censorOpinion);
                map1.put("userCode", PublicResource.getInstance().getUserCode());
                map1.put("userName", PublicResource.getInstance().getUserName());
                map1.put("tokenId", PublicResource.getInstance().getToken());
                Map<String, Integer> map2 = new HashMap();
                map2.put("censorResultType", auditManuscript.censorResultType);
                return RetrofitHelper.getInstance(context).auditManuscript(map1, map2);
            }
        });
    }

    public Observable<ResultGetParamInfo> getParamInfo(Map<String, String> fields) {
        return RetrofitHelper.getInstance(context).getParamInfo();
    }

    public Observable<ResultLoadManuscriptInfo> lockManuscript(final Map<String, String> fields) {
        return RetrofitHelper.getInstance(context)
                .lockManuscript(fields)
                .flatMap(new Function<ResultCommonInfo, Observable<ResultLoadManuscriptInfo>>() {
                    @Override
                    public Observable<ResultLoadManuscriptInfo> apply(@NonNull ResultCommonInfo resultCommonInfo) throws Exception {
                        if (resultCommonInfo.isStatus()) {
                            Map<String, String> map = new HashMap();
                            map.put("manuscriptId", fields.get("manuscriptid"));
                            return RetrofitHelper.getInstance(context).loadManuscript(map);
                        } else {
                            throw new Exception(resultCommonInfo.getMsg());
                        }
                    }
                });
    }

    public Observable<ResultCommonInfo> unlockManuscript(Map<String, String> fields) {
        return RetrofitHelper.getInstance(context).unlockManuscript(fields);
    }

    public Observable<ResultCommonInfo> isCanAuditManuscript(Map<String, String> fields) {
        return RetrofitHelper.getInstance(context).isCanAuditManuscript(fields);
    }

    public Observable<ResultCensorStrategyExist> censorStrategyExist(ManuscriptListInfo manuscriptListInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("manuscriptId", manuscriptListInfo.manuscriptid);
        return RetrofitHelper.getInstance(context).censorStrategyExist(map);
    }

    public Observable<UserListAndTargetSystem> getSubmitMessage(final ManuscriptListInfo manuscriptListInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", manuscriptListInfo.usercode);
        map.put("userName", manuscriptListInfo.username);
        map.put("tokenId", PublicResource.getInstance().getToken());
        map.put("columnId", manuscriptListInfo.columnid);
        map.put("privilegeIds", "PID_CMEDIT_AUDITSCRIPTS");
        map.put("password", PublicResource.getInstance().getPassword());
        return RetrofitHelper.getInstance(context).getCreUserList(map)
                .zipWith(censorStrategyExist(manuscriptListInfo), new BiFunction<ResultGetCreUserList, ResultCensorStrategyExist, UserListAndTargetSystem>() {
                    @Override
                    public UserListAndTargetSystem apply(@NonNull ResultGetCreUserList resultGetCreUserList, @NonNull ResultCensorStrategyExist resultCensorStrategyExist) throws Exception {
                        if (!resultCensorStrategyExist.isStatus()) {
                            throw new Exception("审核策略不存在！");
                        }
                        UserListAndTargetSystem system = new UserListAndTargetSystem();
                        //如果审核方式为人工审核 1 并且 为指定审核人方式 1则显示选择审核人的界面
                        ResultCensorStrategyExist.DataEntity.CensorsTrategyTempEntity temp = resultCensorStrategyExist.getData().getCensorsTrategyTemp();
                        if (temp.getCensormode() == 1 && temp.getDesignatedauditor() == 1) {
                            system.showChooseAuditor = true;
                        } else {
                            system.showChooseAuditor = false;
                        }
                        if (!resultGetCreUserList.isStatus()) {
                            throw new Exception("获取审核人信息失败");
                        } else {
                            system.setTargetSystemNames(resultGetCreUserList.getData());
                        }
                        return system;
                    }
                }).zipWith(getTargetSystem(manuscriptListInfo), new BiFunction<UserListAndTargetSystem, JsonObject, UserListAndTargetSystem>() {
                    @Override
                    public UserListAndTargetSystem apply(@NonNull UserListAndTargetSystem system, @NonNull JsonObject jsonObject) throws Exception {
                        String json = jsonObject.toString();
                        ArrayList<String> targetSystemNames = new ArrayList<>();
                        ArrayList<String> targetSystemIds = new ArrayList<>();
                        switch (manuscriptListInfo.manuscripttype) {
                            case ManuscriptListInfo.MANUSCRIPT_TYPE_CMS:
                                ResultGetWebTargetSystem resultGetWebTargetSystem = new Gson().fromJson(json, ResultGetWebTargetSystem.class);
                                List<ResultGetWebTargetSystem.DataEntity> data1 = resultGetWebTargetSystem.getData();
                                for (ResultGetWebTargetSystem.DataEntity entity : data1) {
                                    String systemid = entity.getTargetsystemid();
                                    String systemName = entity.getTargetsystemname();
                                    targetSystemNames.add(systemName);
                                    targetSystemIds.add(systemid);
                                }
                                break;
                            case ManuscriptListInfo.MANUSCRIPT_TYPE_TV:
                                ResultGetTVTargetSystem tvTargetSystem = new Gson().fromJson(json, ResultGetTVTargetSystem.class);
                                List<ResultGetTVTargetSystem.DataEntity> data2 = tvTargetSystem.getData();
                                for (ResultGetTVTargetSystem.DataEntity entity : data2) {
                                    String systemid = entity.getTargetsystemid();
                                    String systemName = entity.getTargetsystemname();
                                    targetSystemNames.add(systemName);
                                    targetSystemIds.add(systemid);
                                }
                                break;
                            case ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT:
                                ResultGetTargetweixinSystem targetweixinSystem = new Gson().fromJson(json, ResultGetTargetweixinSystem.class);
                                List<ResultGetTargetweixinSystem.DataEntity> data3 = targetweixinSystem.getData();
                                for (ResultGetTargetweixinSystem.DataEntity entity : data3) {
                                    String systemid = entity.getTargetweixinid();
                                    String systemName = entity.getTargetweixinname();
                                    targetSystemNames.add(systemName);
                                    targetSystemIds.add(systemid);
                                }
                                break;
                            case ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO:
                                ResultGetTargetweiboSystem targetweiboSystem = new Gson().fromJson(json, ResultGetTargetweiboSystem.class);
                                List<ResultGetTargetweiboSystem.DataEntity> data4 = targetweiboSystem.getData();
                                for (ResultGetTargetweiboSystem.DataEntity entity : data4) {
                                    String systemid = entity.getTargetweiboid();
                                    String systemName = entity.getTargetweiboname();
                                    targetSystemNames.add(systemName);
                                    targetSystemIds.add(systemid);
                                }
                                break;
                        }

                        system.targetSystemNames = targetSystemNames;
                        system.targetSystemIds = targetSystemIds;
                        return system;
                    }
                });

    }


    public Observable<UserListAndTargetSystem> getAuditMessage(final ManuscriptListInfo manuscriptListInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", manuscriptListInfo.usercode);
        map.put("userName", manuscriptListInfo.username);
        map.put("tokenId", PublicResource.getInstance().getToken());
        map.put("columnId", manuscriptListInfo.columnid);
        map.put("privilegeIds", "PID_CMEDIT_AUDITSCRIPTS");
        map.put("password", PublicResource.getInstance().getPassword());
        return RetrofitHelper.getInstance(context).getCreUserList(map)
                .zipWith(censorStrategyExist(manuscriptListInfo), new BiFunction<ResultGetCreUserList, ResultCensorStrategyExist, UserListAndTargetSystem>() {
                    @Override
                    public UserListAndTargetSystem apply(@NonNull ResultGetCreUserList resultGetCreUserList, @NonNull ResultCensorStrategyExist resultCensorStrategyExist) throws Exception {
                        if (!resultCensorStrategyExist.isStatus()) {
                            throw new Exception("审核策略不存在！");
                        }
                        UserListAndTargetSystem system = new UserListAndTargetSystem();
                        //如果审核方式为人工审核 1 并且 为指定审核人方式 1则显示选择审核人的界面
                        ResultCensorStrategyExist.DataEntity.CensorsTrategyTempEntity temp = resultCensorStrategyExist.getData().getCensorsTrategyTemp();
                        if (temp.getCensormode() == 1 && temp.getDesignatedauditor() == 1) {
                            system.showChooseAuditor = true;
                        } else {
                            system.showChooseAuditor = false;
                        }
                        if (!resultGetCreUserList.isStatus()) {
                            throw new Exception("获取审核人信息失败");
                        } else {
                            system.setTargetSystemNames(resultGetCreUserList.getData());
                        }
                        return system;
                    }
                }).zipWith(getTargetSystem(manuscriptListInfo), new BiFunction<UserListAndTargetSystem, JsonObject, UserListAndTargetSystem>() {
                    @Override
                    public UserListAndTargetSystem apply(@NonNull UserListAndTargetSystem system, @NonNull JsonObject jsonObject) throws Exception {
                        String json = jsonObject.toString();
                        ArrayList<String> targetSystemNames = new ArrayList<>();
                        ArrayList<String> targetSystemIds = new ArrayList<>();
                        switch (manuscriptListInfo.manuscripttype) {
                            case ManuscriptListInfo.MANUSCRIPT_TYPE_CMS:
                                ResultGetWebTargetSystem resultGetWebTargetSystem = new Gson().fromJson(json, ResultGetWebTargetSystem.class);
                                List<ResultGetWebTargetSystem.DataEntity> data1 = resultGetWebTargetSystem.getData();
                                for (ResultGetWebTargetSystem.DataEntity entity : data1) {
                                    String systemid = entity.getTargetsystemid();
                                    String systemName = entity.getTargetsystemname();
                                    targetSystemNames.add(systemName);
                                    targetSystemIds.add(systemid);
                                }
                                break;
                            case ManuscriptListInfo.MANUSCRIPT_TYPE_TV:
                                ResultGetTVTargetSystem tvTargetSystem = new Gson().fromJson(json, ResultGetTVTargetSystem.class);
                                List<ResultGetTVTargetSystem.DataEntity> data2 = tvTargetSystem.getData();
                                for (ResultGetTVTargetSystem.DataEntity entity : data2) {
                                    String systemid = entity.getTargetsystemid();
                                    String systemName = entity.getTargetsystemname();
                                    targetSystemNames.add(systemName);
                                    targetSystemIds.add(systemid);
                                }
                                break;
                            case ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT:
                                ResultGetTargetweixinSystem targetweixinSystem = new Gson().fromJson(json, ResultGetTargetweixinSystem.class);
                                List<ResultGetTargetweixinSystem.DataEntity> data3 = targetweixinSystem.getData();
                                for (ResultGetTargetweixinSystem.DataEntity entity : data3) {
                                    String systemid = entity.getTargetweixinid();
                                    String systemName = entity.getTargetweixinname();
                                    targetSystemNames.add(systemName);
                                    targetSystemIds.add(systemid);
                                }
                                break;
                            case ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO:
                                ResultGetTargetweiboSystem targetweiboSystem = new Gson().fromJson(json, ResultGetTargetweiboSystem.class);
                                List<ResultGetTargetweiboSystem.DataEntity> data4 = targetweiboSystem.getData();
                                for (ResultGetTargetweiboSystem.DataEntity entity : data4) {
                                    String systemid = entity.getTargetweiboid();
                                    String systemName = entity.getTargetweiboname();
                                    targetSystemNames.add(systemName);
                                    targetSystemIds.add(systemid);
                                }
                                break;
                        }

                        system.targetSystemNames = targetSystemNames;
                        system.targetSystemIds = targetSystemIds;
                        return system;
                    }
                });

    }


    public Observable<JsonObject> getTargetSystem(ManuscriptListInfo manuscriptListInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("manuscriptid", "");
        switch (manuscriptListInfo.manuscripttype) {
            case ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO:
                return RetrofitHelper.getInstance(context).getTargetweiboSystem(map);
            case ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT:
                return RetrofitHelper.getInstance(context).getTargetweixinSystem(map);
            case ManuscriptListInfo.MANUSCRIPT_TYPE_TV:
                return RetrofitHelper.getInstance(context).getTVTargetSystem(map);
            case ManuscriptListInfo.MANUSCRIPT_TYPE_CMS:
                return RetrofitHelper.getInstance(context).getWebTargetSystem(map);
        }
        return null;
    }

    public Observable<ResultSaveManuscriptInfo> save(final ManuscriptListInfo manuscriptListInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("manuscripttype", manuscriptListInfo.manuscripttype + "");
        map.put("manuscriptid", manuscriptListInfo.manuscriptid);
        map.put("header", manuscriptListInfo.header);
        map.put("usercode", manuscriptListInfo.usercode);
        map.put("username", manuscriptListInfo.username);
        map.put("columnname", manuscriptListInfo.columnname);
        String columnid = "";
        for (int i = 0; i < manuscriptListInfo.columns.size(); i++) {
            if (i != 0 && manuscriptListInfo.columnname.equals(manuscriptListInfo.columns.get(i))) {
                columnid = manuscriptListInfo.columnsID.get(i - 1);
                break;
            }
        }
        byte[] bytes = Base64.encodeBase64(manuscriptListInfo.h5content.getBytes());
        String h5Content = new String(bytes);
        map.put("columnid", columnid);
        map.put("textcontent", manuscriptListInfo.textcontent);
        map.put("h5content", h5Content);
        XLog.i("save: " + map.toString());
        return saveManuscript(map);
    }


}
