package com.dayang.dycmmedit.redact.model;

import android.content.Context;

import com.dayang.dycmmedit.http.RetrofitHelper;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.info.RequestAuditManuscript;
import com.dayang.dycmmedit.info.RequestSubmitManuscript;
import com.dayang.dycmmedit.info.ResultCensorStrategyExist;
import com.dayang.dycmmedit.info.ResultCommonInfo;
import com.dayang.dycmmedit.info.ResultGetCreUserList;
import com.dayang.dycmmedit.info.ResultGetTVTargetSystem;
import com.dayang.dycmmedit.info.ResultGetTargetweiboSystem;
import com.dayang.dycmmedit.info.ResultGetTargetweixinSystem;
import com.dayang.dycmmedit.info.ResultGetUseAttachmentInfo;
import com.dayang.dycmmedit.info.ResultGetWebTargetSystem;
import com.dayang.dycmmedit.info.ResultLoadManuscriptInfo;
import com.dayang.dycmmedit.info.ResultSaveManuscriptInfo;
import com.dayang.dycmmedit.info.UserListAndTargetSystem;
import com.dayang.dycmmedit.utils.PublicResource;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
 * Created by 冯傲 on 2017/5/9.
 * e-mail 897840134@qq.com
 */

public class RedactModel {
    private Context context;

    public RedactModel(Context context) {
        this.context = context;
    }

    public Observable<ResultLoadManuscriptInfo> loadManuscript(Map<String, String> fields) {
        return RetrofitHelper.getInstance(context).loadManuscript(fields);
    }

    public Observable<ResultSaveManuscriptInfo> saveManuscript(Map<String, String> fields, Map<String, Integer> fields2) {
        return RetrofitHelper.getInstance(context).saveManuscript(fields, fields2);
    }

    public Observable<ResultGetUseAttachmentInfo> getUseAttachment(Map<String, String> fields) {
        return RetrofitHelper.getInstance(context).getUseAttachment(fields);
    }

    public Observable<ResultCommonInfo> delPicByManuscript(Map<String, String> fields) {
        return RetrofitHelper.getInstance(context).delPicByManuscript(fields);
    }

    public Observable<ResultCommonInfo> submitManuscript(RequestSubmitManuscript system, final ManuscriptListInfo info) {
        final Map<String, String> map = new HashMap<>();
        map.put("manuscriptIds", info.manuscriptid);
        map.put("userCode", info.usercode);
        map.put("targetSystemIds", system.getTargetSystemIds());
        map.put("tokenid", PublicResource.getInstance().getToken());
        map.put("username", PublicResource.getInstance().getUserName());
        map.put("censorAuditor", system.getCensorAuditor());
        map.put("censorAuditorId", system.getCensorAuditorId());
        if (info.manuscripttype == ManuscriptListInfo.MANUSCRIPT_TYPE_CMS) {
            Map<String, String> map2 = new HashMap<>();
            map2.put("username", PublicResource.getInstance().getUserName());
            map2.put("usercode", PublicResource.getInstance().getUserCode());
            map2.put("systemid", system.getTargetSystemIds());
            return RetrofitHelper.getInstance(context).IsOutOfMemoryOfTVManuscript(map2)
                    .flatMap(new Function<ResultCommonInfo, Observable<ResultCommonInfo>>() {
                        @Override
                        public Observable<ResultCommonInfo> apply(@NonNull ResultCommonInfo commonInfo) throws Exception {
                            if (commonInfo.isStatus()) {
                                return RetrofitHelper.getInstance(context).submitManuscript(map);
                            } else {
                                throw new Exception("没有足够的空间");
                            }
                        }
                    });
        } else {
            return RetrofitHelper.getInstance(context).submitManuscript(map);
        }
    }

    public Observable<UserListAndTargetSystem> getSubmitMessage(final ManuscriptListInfo manuscriptListInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", manuscriptListInfo.usercode);
        map.put("userName", PublicResource.getInstance().getUserName());
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

    public Observable<ResultCensorStrategyExist> censorStrategyExist(ManuscriptListInfo manuscriptListInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("manuscriptId", manuscriptListInfo.manuscriptid);
        return RetrofitHelper.getInstance(context).censorStrategyExist(map);
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


    public Observable<UserListAndTargetSystem> getAuditMessage(final ManuscriptListInfo manuscriptListInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", manuscriptListInfo.usercode);
        map.put("userName", PublicResource.getInstance().getUserName());
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
}
