package com.dayang.uploadlib.task;

import android.os.SystemClock;
import android.util.Log;

import com.dayang.dyhfileuploader.DYHFileUploadInfo;
import com.dayang.dyhfileuploader.DYHFileUploadTask;
import com.dayang.dyhfileuploader.DYHFileUploader;
import com.dayang.uploadlib.model.MissionInfo;
import com.dayang.uploadlib.service.UpLoadService;
import com.dayang.uploadlib.util.NetWorkState;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by 冯傲 on 2017/6/1.
 * e-mail 897840134@qq.com
 */

public class NginxHttpUploadTask extends BaseTask {
    MissionInfo missionInfo;
    UpLoadService service;
    private static final String TAG = "cmtools_log";
    private boolean isRename;
    private String uploadTrunkInfoURL;
    String tenantIdPath = "";
    String filePath;
    private String taskId;
    private String customParam;
    private String sessionId;
    private String newFileName;
    private String remoteRootPath;
    private String fileStatusNotifyURL;
    private String storageURL;
    private DYHFileUploader fileUploader = null;
    private DYHFileUploadTask taskInfo;

    private boolean working;
    private boolean error;
    private boolean finished;
    private boolean pause;
    private boolean del;

    private DYHFileUploader.OnInfoUpdatedListener mStatusUpdatedListener = new DYHFileUploader.OnInfoUpdatedListener() {
        @Override
        public void onInfoUpdated(DYHFileUploadInfo dyhFileUploadInfo) {
            int status = dyhFileUploadInfo.status;
            //TODO 输出log日志
            switch (status) {
                case DYHFileUploadInfo.StatusError:
                    Log.i(TAG, "StatusError");
                    error = true;
                    break;
                case DYHFileUploadInfo.StatusFinished:
                    String json = dyhFileUploadInfo.uploadMigrateInfo;
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(json);
                        newFileName = jsonObject.getString("migrateFileName");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onInfoUpdated: 新文件名获取错误");
                    }
                    Log.i(TAG, "StatusFinished");
                    working = false;
                    finished = true;
                    break;
                case DYHFileUploadInfo.StatusProcessing:
                    //TODO 可能存在进度上不去的问题或者log打印过快
                    Log.i(TAG, "StatusProcessing" + dyhFileUploadInfo.uploadedBytes);
                    break;
                case DYHFileUploadInfo.StatusStopped:
                    Log.i(TAG, "StatusStopped");
                    working = false;
                    break;
                case DYHFileUploadInfo.StatusUnkown:
                    Log.i(TAG, "StatusUnkown");
                    error = true;
                    break;
            }
        }
    };

    public NginxHttpUploadTask(MissionInfo missionInfo, UpLoadService service) {
        super();
        this.storageURL = missionInfo.getStorageURL();
        super.tenantId = missionInfo.getTenantId();
        this.fileStatusNotifyURL = missionInfo.getFileStatusNotifyURL();
        this.taskId = missionInfo.getTaskId();
        this.customParam = missionInfo.getCustomParam();
        this.remoteRootPath = missionInfo.getRemoteRootPath();
        this.uploadTrunkInfoURL = missionInfo.getUploadTrunkInfoURL();
        this.tenantIdPath = tenantId.equals("") ? "" : tenantId + "/";
        this.isRename = missionInfo.isRename();
        this.sessionId = missionInfo.getSessionId();
        this.service = service;
        fileUploader = new DYHFileUploader();
        fileUploader.init(DYHFileUploader.TypeHTTP_nginxResume);
        fileUploader.setOnInfoUpdatedListener(mStatusUpdatedListener);
        missionInfo.setPauseListener(new MissionInfo.PauseListener() {
            @Override
            public void pause() {
                pause = true;
                fileUploader.stop();
            }
        });
        missionInfo.setDelListener(new MissionInfo.DelListener() {
            @Override
            public void del() {
                del = true;
                fileUploader.stop();
            }
        });
    }

    /**
     * 拼接http路径地址
     *
     * @param localPath 文件本地路径
     */
    public String solveHttpPath(String localPath) {
        String[] split = localPath.split("/");
        filePath = split[split.length - 1];
        if (!storageURL.endsWith("/")) {
            storageURL = storageURL + "/";
        }
        String url = storageURL + filePath + "?redirectParam={\"fileSessionId\":\"" + sessionId + "\",\"fileSavePath\":\"" + remoteRootPath + "\",\"isRename\":\"" + isRename + "\"}";
        return url;
    }

    /**
     * 文件上传功能的实现，单个文件上传
     *
     * @param
     * @return
     * @throws Exception
     */
    public void uploadSingleFile() {
        taskInfo = getTaskInfoFromUI();
        Log.i(TAG, "sessionID: " + taskInfo.sessionID);
        Log.i(TAG, "localUrl: " + taskInfo.localUrl);
        Log.i(TAG, "remoteUrl: " + taskInfo.remoteUrl);
        Log.i(TAG, "password: " + taskInfo.password);
        Log.i(TAG, "user: " + taskInfo.user);
        Log.i(TAG, "FTPMode: " + taskInfo.FTPMode);
        Log.i(TAG, "resume: " + taskInfo.resume);
        Log.i(TAG, "timeOut: " + taskInfo.timeOut);
        Log.i(TAG, "uploadTrunkInfoURL: " + taskInfo.uploadTrunkInfoURL);
        //TODO 提前判断任务状态
        working = true;
        fileUploader.setTask(taskInfo);
        boolean ret = fileUploader.start(false);
        if (!ret) {
            working = false;
            error = true;
        }
        while (working) {
            SystemClock.sleep(50);
            if (error) {
                break;
            }
        }
        if (error) {
            int i = NetWorkState.GetNetype(service);
            if (i == NetWorkState.NONE) {
                missionInfo.setStatus(MissionInfo.WAITINGNETWORDK);
                return;
            }
        }
        if (del) {
            //TODO 发送删除通知
            missionInfo.setStatus(MissionInfo.REMOVED);
            return;
        }
        if (pause) {
            missionInfo.setStatus(MissionInfo.PAUSEING);
            return;
        }
        if (finished) {
            missionInfo.setStatus(MissionInfo.UPLOADCOMPLETED);
            fileStatusNotifyCallBack(fileStatusNotifyURL,
                    getNotifyRequestParam(false, filePath, taskId));
        } else {
            missionInfo.setStatus(MissionInfo.UPLOADERROR);
            fileStatusNotifyCallBack(fileStatusNotifyURL,
                    getNotifyRequestParam(true, filePath, taskId));
        }
        return;
    }


    public void run() {
        this.uploadSingleFile();
        service.taskComplete();
    }

    private DYHFileUploadTask getTaskInfoFromUI() {
        DYHFileUploadTask task = new DYHFileUploadTask();
        task.sessionID = missionInfo.getSessionId();
        String path = this.sessionId;
        task.FTPMode = DYHFileUploadTask.FTPModePassivePASV;
        task.remoteUrl = solveHttpPath(path);
        task.timeOut = 0;
        task.localUrl = path;
        task.resume = true;
        if (!uploadTrunkInfoURL.equals("")) {
            task.uploadTrunkInfoURL = uploadTrunkInfoURL;
        }
        task.user = "";
        task.password = "";
        task.bucketName = "";
        task.regionName = "";
        task.fileKey = "";
        task.host = "";
        task.awsAccessKey = "";
        task.awsSecretKey = "";
        task.uploadId = "";
        taskInfo = task;
        return task;
    }
}
