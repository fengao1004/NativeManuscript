package com.dayang.uploadlib.model;


import android.os.Handler;
import android.os.Message;

import com.dayang.uploadlib.UploadFileManager;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 冯傲 on 2017/6/1.
 * e-mail 897840134@qq.com
 * 上传任务的实体对象
 * 设计思路
 * 用尽量少的回调保证逻辑的简单，所以该类只具备暂停方法（具体由真正到上传Task中实现PauseListener的抽象方法实现），删除任务，继续传输都由 uploadManager去完成
 * 支持设置状态与进度监听
 */
@Entity
public class MissionInfo {
    public static final int UPLOADING = 2149;
    public static final int PAUSEING = 2148;
    public static final int WAITINGUPLOAD = 2147;
    public static final int WAITINGNETWORDK = 2179;
    public static final int WAITINGNETWORDK_WIFI = 2109;
    public static final int UPLOADERROR = 2146;
    public static final int UPLOADCOMPLETED = 2196;
    public static final int REMOVED = 2145;
    public static final int CLOSEAPP_UPLOADING = 2136;
    public static final int CLOSEAPP_WAITINGUPLOAD = 2169;
    public static final int UPDATESTATUS = 969;
    @Transient
    PauseListener pauseListener;
    public String taskName;
    @Transient
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATESTATUS:
                    if (uploadStatusListener != null) {
                        uploadStatusListener.uploadStatus(status, progress, "");
                    }
                    MissionInfoDao dbHelper = UploadFileManager.getInstance().getDBHelper();
                    dbHelper.update(MissionInfo.this);
                    break;
            }
        }
    };
    @Id
    private Long id;
    int length;
    int progress;
    int status;
    int priority;
    String sessionId;
    @Transient
    UploadStatusListener uploadStatusListener;

    public MissionInfo() {

    }

    public MissionInfo(String taskName) {
        this.taskName = taskName;
        this.status = WAITINGUPLOAD;
    }

    @Generated(hash = 1014643628)
    public MissionInfo(String taskName, Long id, int length, int progress, int status, int priority,
                       String sessionId) {
        this.taskName = taskName;
        this.id = id;
        this.length = length;
        this.progress = progress;
        this.status = status;
        this.priority = priority;
        this.sessionId = sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setLength(int length) {
        this.length = length;
    }


    public String getSessionId() {
        return sessionId;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        Message mes = new Message();
        mes.what = UPDATESTATUS;
        mes.obj = progress;
        handler.sendMessage(mes);
    }

    public void pauseMission() {
        if (status == UPLOADING) {
            if (pauseListener != null) {
                pauseListener.pause();
            }
        }
        if (getStatus() == UPLOADCOMPLETED || getStatus() == UPLOADERROR || getStatus() == REMOVED) {

        } else {
            setStatus(PAUSEING);
        }
    }

    public int getProgress() {
        return progress;
    }

    public void setUploadStatueListener(UploadStatusListener listener) {
        this.uploadStatusListener = listener;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        //TODO 更新数据库
        this.status = status;
        handler.sendEmptyMessage(UPDATESTATUS);
    }

    public void setPauseListener(PauseListener pauseListener) {
        this.pauseListener = pauseListener;

    }

    public interface UploadStatusListener {
        void uploadStatus(int status, int progress, String message);
    }

    public interface PauseListener {
        void pause();
    }

    public PauseListener getPauseListener() {
        return pauseListener;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLength() {
        return this.length;
    }
}
