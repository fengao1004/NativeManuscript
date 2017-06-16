package com.dayang.dycmmedit.redact.presenter;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.dayang.dycmmedit.http.BaseObserver;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.info.RequestAuditManuscript;
import com.dayang.dycmmedit.info.RequestSubmitManuscript;
import com.dayang.dycmmedit.info.ResultCommonInfo;
import com.dayang.dycmmedit.info.ResultGetUseAttachmentInfo;
import com.dayang.dycmmedit.info.ResultLoadManuscriptInfo;
import com.dayang.dycmmedit.info.ResultSaveManuscriptInfo;
import com.dayang.dycmmedit.info.UserInfo;
import com.dayang.dycmmedit.info.UserListAndTargetSystem;
import com.dayang.dycmmedit.redact.model.RedactModel;
import com.dayang.dycmmedit.redact.view.RedactViewInterface;
import com.dayang.dycmmedit.utils.PrivilegeUtil;
import com.dayang.dycmmedit.utils.PublicResource;
import com.dayang.uploadlib.UploadFileManager;
import com.dayang.uploadlib.model.MissionInfo;
import com.elvishew.xlog.XLog;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by 冯傲 on 2017/5/9.
 * e-mail 897840134@qq.com
 */

public class RedactPresenterImpl implements RedactPresenterInterface {

    private static final String TAG = "cmtools_log";
    private RedactViewInterface redactViewInterface;
    private final Activity activity;
    private final RedactModel redactModel;


    @Override
    public void save(final ManuscriptListInfo manuscriptListInfo, final boolean isBack) {
        Map map = manuscriptListInfo.getMap();
        Map<String, String> stringMap = (Map<String, String>) map.get("stringMap");
        Map<String, Integer> integerMap = (Map<String, Integer>) map.get("int");
        redactViewInterface.showWaiting("保存中");
        redactModel.saveManuscript(stringMap, integerMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResultSaveManuscriptInfo>() {
                    @Override
                    public void onNext(@NonNull ResultSaveManuscriptInfo info) {
                        ManuscriptListInfo data = info.getData();
                        redactViewInterface.removeWaiting();
                        boolean status = info.isStatus();
                        redactViewInterface.setHasChange(true);
                        if (status) {
                            redactViewInterface.makeToast("稿件保存成功");
                            redactViewInterface.setTextContent(manuscriptListInfo.textcontent);
                            redactViewInterface.setWeixinImage(data.weixinlowimage);
                            if (isBack) {
                                redactViewInterface.goBack();
                            }
                        } else {
                            redactViewInterface.makeToast(info.getMsg());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        redactViewInterface.removeWaiting();
                        redactViewInterface.makeToast("稿件保存失败");
                    }
                });
    }

    @Override
    public void submit(ManuscriptListInfo createManuscriptInfo) {

    }

    @Override
    public void uploadFile(final String path, String taskId, final int requestCode) {
        redactViewInterface.showWaiting("文件上传中");
        String storageURL = PublicResource.getInstance().getStorageURL();
        String fileStatusNotifyURL = PublicResource.getInstance().getFileStatusNotifyURL();
        Log.i(TAG, "storageURL: " + storageURL);
        Log.i(TAG, "filePath: " + path);
        Log.i(TAG, "taskId: " + taskId);
        Log.i(TAG, "fileStatusNotifyURL: " + fileStatusNotifyURL);
        MissionInfo missionInfo = new MissionInfo();
        missionInfo.setCompleteListener(new MissionInfo.CompleteListener() {
            @Override
            public void UploadComplete(int code) {
                redactViewInterface.removeWaiting();
                if (code == MissionInfo.UPLOADERROR) {
                    Toast.makeText(activity, "上传失败", Toast.LENGTH_SHORT).show();
                } else {
                    redactViewInterface.upLoadFileComplete(path, requestCode);
                    Toast.makeText(activity, "上传成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
        missionInfo.setFilePath(path);
        missionInfo.setSessionId(taskId);
        missionInfo.setFileStatusNotifyURL(fileStatusNotifyURL);
        missionInfo.setTaskId(taskId);
        missionInfo.setStorageURL(storageURL);
        UploadFileManager.getInstance().addMission(missionInfo);
    }


    @Override
    public void getWeiboImage(ManuscriptListInfo createManuscriptInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("manuscriptid", createManuscriptInfo.manuscriptid);

        redactModel.getUseAttachment(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResultGetUseAttachmentInfo>() {
                    @Override
                    public void onNext(@NonNull ResultGetUseAttachmentInfo resultGetUseAttachmentInfo) {
                        if (resultGetUseAttachmentInfo != null && resultGetUseAttachmentInfo.isStatus() && resultGetUseAttachmentInfo.getData().size() > 0) {
                            String url = resultGetUseAttachmentInfo.getData().get(0).getUsesharepath();
                            if (!url.equals("")) {
                                redactViewInterface.insertImageForWeibo(url);
                            }
                        } else {
//                            Toast.makeText(activity, "获取微博图片失败", 0).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
//                        Toast.makeText(activity, "获取微博图片失败", 0).show();
                    }
                });
    }

    @Override
    public void delWeiboImage(ManuscriptListInfo createManuscriptInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("manuscriptId", createManuscriptInfo.manuscriptid);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("manuscriptId",createManuscriptInfo.manuscriptid);
        redactModel.delPicByManuscript(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResultCommonInfo>() {
                    @Override
                    public void onNext(@NonNull ResultCommonInfo resultGetUseAttachmentInfo) {
                        XLog.i("onNext: " + new Gson().toJson(resultGetUseAttachmentInfo));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        XLog.i("onNext: " + e.toString());
                    }
                });
    }

    @Override
    public void getHistoryData(ManuscriptListInfo manuscriptListInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("manuscriptId", manuscriptListInfo.manuscriptid);
        redactViewInterface.showWaiting("获取审核记录");
        redactModel.loadManuscript(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResultLoadManuscriptInfo>() {
                    @Override
                    public void onNext(@NonNull ResultLoadManuscriptInfo resultLoadManuscriptInfo) {
                        redactViewInterface.removeWaiting();
                        if (resultLoadManuscriptInfo.isStatus()) {
                            redactViewInterface.setHistoryMessage(resultLoadManuscriptInfo.getData().getCensorRecordList());
                        } else {
                            redactViewInterface.makeToast("获取审核记录失败");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        redactViewInterface.removeWaiting();
                        redactViewInterface.makeToast("获取审核记录失败");
                    }
                });
    }

    @Override
    public void getSubmitMessage(final ManuscriptListInfo info) {
        String columnname = info.columnname;
        String header = info.header;
        String h5content = info.h5content;
        String textcontent = info.textcontent;
        if (columnname.equals("")) {
            redactViewInterface.showTextDialog("栏目不能为空");
            return;
        }
        List<UserInfo.DataEntity.ColumnListModelEntity> columnNameList = PublicResource.getInstance().getColumnNameList();
        int flag = -1;
        for (UserInfo.DataEntity.ColumnListModelEntity entity : columnNameList) {
            if (entity.getName().equals(columnname)) {
                flag = 1;
            }
        }
        if (flag != 1) {
            redactViewInterface.showTextDialog("栏目不合法");
            return;
        }
        if (columnname.equals("")) {
            redactViewInterface.showTextDialog("栏目不能为空");
            return;
        }
        if (header.equals("")) {
            redactViewInterface.showTextDialog("标题不能为空");
            return;
        }
        if (h5content.equals("") && info.manuscripttype != ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO) {
            redactViewInterface.showTextDialog("内容不能为空");
            return;
        }
        if (textcontent.equals("") && info.manuscripttype == ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO) {
            redactViewInterface.showTextDialog("内容不能为空");
            return;
        }
        //TODO 判断微信稿件长度 web稿件发布方式 新媒体栏目
        boolean hasPrivilege = PrivilegeUtil.hasPrivilege(PrivilegeUtil.PRIVILEGE_SUBMIT, info);
        if (!hasPrivilege) {
            redactViewInterface.showTextDialog("对不起你没有权限提交");
            return;
        }
        redactViewInterface.showWaiting("获取提交信息");
        redactModel.getSubmitMessage(info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserListAndTargetSystem>() {
                    @Override
                    public void onNext(@NonNull UserListAndTargetSystem userListAndTargetSystem) {
                        redactViewInterface.removeWaiting();
                        redactViewInterface.showSubmitDialog(userListAndTargetSystem, info);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        redactViewInterface.removeWaiting();
                        redactViewInterface.showTextDialog(e.getMessage());
                    }
                });
    }

    @Override
    public void submitManuscript(final RequestSubmitManuscript system, final ManuscriptListInfo info) {
        redactViewInterface.showWaiting("提交稿件中");
        Map map = info.getMap();
        Map<String, String> stringMap = (Map<String, String>) map.get("stringMap");
        Map<String, Integer> integerMap = (Map<String, Integer>) map.get("int");
        redactModel.saveManuscript(stringMap, integerMap).flatMap(new Function<ResultSaveManuscriptInfo, Observable<ResultCommonInfo>>() {
            @Override
            public Observable<ResultCommonInfo> apply(@NonNull ResultSaveManuscriptInfo resultSaveManuscriptInfo) throws Exception {
                if (resultSaveManuscriptInfo.isStatus()) {
                    return redactModel.submitManuscript(system, info);
                } else {
                    throw new Exception("稿件保存错误");
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResultCommonInfo>() {
                    @Override
                    public void onNext(@NonNull ResultCommonInfo resultCommonInfo) {
                        redactViewInterface.removeWaiting();
                        boolean status = resultCommonInfo.isStatus();
                        if (status) {
                            redactViewInterface.showTextDialog("提交稿件成功");
                        } else {
                            redactViewInterface.showTextDialog("提交稿件失败" + resultCommonInfo.getMsg());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        redactViewInterface.removeWaiting();
                        redactViewInterface.showTextDialog("提交稿件失败" + e.getMessage());
                    }
                });
    }

    @Override
    public void getAuditMessage(final ManuscriptListInfo info) {
        boolean hasPrivilege = PrivilegeUtil.hasPrivilege(PrivilegeUtil.PRIVILEGE_AUDIT, info);
        if (!hasPrivilege) {
            redactViewInterface.showTextDialog("对不起你没有审核提交");
            return;
        }
        redactViewInterface.showWaiting("获取审核信息");
        redactModel.getAuditMessage(info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserListAndTargetSystem>() {
                    @Override
                    public void onNext(@NonNull UserListAndTargetSystem userListAndTargetSystem) {
                        redactViewInterface.removeWaiting();
                        redactViewInterface.showAuditDialog(userListAndTargetSystem, info);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        redactViewInterface.removeWaiting();
                        redactViewInterface.showTextDialog(e.getMessage());
                    }
                });
    }

    @Override
    public void auditManuscript(ManuscriptListInfo info, final RequestAuditManuscript auditManuscript) {
        boolean hasPrivilege = PrivilegeUtil.hasPrivilege(PrivilegeUtil.PRIVILEGE_AUDIT, info);
        if (!hasPrivilege) {
            redactViewInterface.showTextDialog("对不起，您没有审核权限");
        }
        Map<String, Map> map = info.getMap();

        redactViewInterface.showWaiting("审核中");
        redactModel.saveManuscript((Map<String, String>) map.get("stringMap"), (Map<String, Integer>) map.get("int"))
                .flatMap(new Function<ResultSaveManuscriptInfo, Observable<ResultCommonInfo>>() {
                    @Override
                    public Observable<ResultCommonInfo> apply(@NonNull ResultSaveManuscriptInfo resultSaveManuscriptInfo) throws Exception {
                        if (resultSaveManuscriptInfo.isStatus()) {
                            return redactModel.auditManuscript(auditManuscript);
                        } else {
                            throw new Exception("稿件保存错误");
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResultCommonInfo>() {
                    @Override
                    public void onNext(@NonNull ResultCommonInfo resultCommonInfo) {
                        redactViewInterface.removeWaiting();
                        if (resultCommonInfo.isStatus()) {
                            redactViewInterface.showTextDialog("审核成功");
                        } else {
                            redactViewInterface.showTextDialog("审核失败");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        redactViewInterface.removeWaiting();
                        redactViewInterface.showTextDialog(e.getMessage());
                    }
                });
    }

    public RedactPresenterImpl(RedactViewInterface redactViewInterface) {
        this.redactViewInterface = redactViewInterface;
        activity = redactViewInterface.getViewActivity();
        redactModel = new RedactModel(activity);
    }
}
