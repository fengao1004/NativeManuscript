package com.dayang.uploadlib;

import android.content.Context;

import com.dayang.uploadlib.iterface.UpLoadLibInterface;
import com.dayang.uploadlib.model.MissionInfo;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/25.
 * e-mail 897840134@qq.com
 */

public class UploadFileManager implements UpLoadLibInterface {
    static UploadFileManager manager;
    Context context;

    private UploadFileManager() {

    }

    public static UploadFileManager getInstance() {
        if (manager == null) {
            manager = new UploadFileManager();
        }
        return manager;
    }

    public UploadFileManager init(Context context) {
        this.context = context;
        return manager;
    }

    @Override
    public void pauseAllMission() {

    }

    @Override
    public void startAllMission() {

    }

    @Override
    public void setMaxUploadMissionCount(int max) {

    }

    @Override
    public void deleteMission(MissionInfo info) {

    }

    @Override
    public void deleteMission(String sessionId) {

    }

    @Override
    public void deleteAllMission() {

    }

    @Override
    public void deleteCompleteMission() {

    }

    @Override
    public void addMission(MissionInfo info) {

    }

    @Override
    public void addMissions(List<MissionInfo> infoList) {

    }

    @Override
    public List<MissionInfo> getMissions() {
        return null;
    }

    @Override
    public void exchangMissionPosition(MissionInfo info1, MissionInfo info2) {

    }

    @Override
    public void isForbidMobileNetworkUpload(boolean isForbid) {

    }

    @Override
    public void networkStateChange(int networkState) {

    }
}
