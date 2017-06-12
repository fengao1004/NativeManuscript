package com.dayang.uploadlib.model;


/**
 * Created by 冯傲 on 2017/6/1.
 * e-mail 897840134@qq.com
 * 上传任务的实体对象
 * 设计思路
 * 用尽量少的回调保证逻辑的简单，所以该类只具备暂停方法（具体由真正到上传Task中实现PauseListener的抽象方法实现），删除任务，继续传输都由 uploadManager去完成
 * 支持设置状态与进度监听
 */

public class MissionInfo {
    public static final int UPLOADING = 2149;
    public static final int PAUSEING = 2148;
    public static final int WAITINGUPLOAD = 2147;
    public static final int WAITINGNETWORDK = 2179;
    public static final int WAITINGNETWORDK_WIFI = 2179;
    public static final int UPLOADERROR = 2146;
    public static final int UPLOADCOMPLETED = 2145;
    public static final int REMOVED = 2145;

    int status;
    int priority;
    //任务唯一标识
    String sessionId;
    UploadStatusListener listener;

    public MissionInfo() {

    }

    public void pauseMission() {
        if (status == UPLOADING) {
            //TODO 通知上传任务停止任务
        }
        setStatus(PAUSEING);
    }


    public void setUploadStatueListener(UploadStatusListener listener) {

    }

    public void promotePriority() {

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
        this.status = status;
    }

    public interface UploadStatusListener {
        void uploadStatus(int status, int progress, String message);
    }

}
