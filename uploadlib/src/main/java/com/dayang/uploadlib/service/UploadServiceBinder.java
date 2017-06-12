package com.dayang.uploadlib.service;

import android.os.Binder;

import com.dayang.uploadlib.iterface.UpLoadServiceInterface;
import com.dayang.uploadlib.model.MissionInfo;

import java.util.List;

/**
 * Created by 冯傲 on 2017/6/1.
 * e-mail 897840134@qq.com
 */

public class UploadServiceBinder extends Binder implements UpLoadServiceInterface {
    UpLoadService service;

    public UploadServiceBinder(UpLoadService service) {
        this.service = service;
    }

    @Override
    public void pauseAllMission() {
        service.pauseAllMission();
    }

    @Override
    public void startAllMission() {
        service.startAllMission();
    }

    @Override
    public void setMaxUploadMissionCount(int max) {
        service.setMaxUploadMissionCount(max);
    }

    @Override
    public void deleteMission(MissionInfo info) {
        service.deleteMission(info);
    }

    @Override
    public void deleteMission(String sessionId) {
        service.deleteMission(sessionId);
    }

    @Override
    public void deleteAllMission() {
        service.deleteAllMission();
    }

    @Override
    public void deleteCompleteMission() {
        service.deleteCompleteMission();
    }

    @Override
    public void addMission(MissionInfo info) {
        service.addMission(info);
    }

    @Override
    public void addMissions(List<MissionInfo> infoList) {
        service.addMissions(infoList);
    }

    @Override
    public List<MissionInfo> getMissions() {
        return service.getMissions();
    }

    @Override
    public void exchangeMissionPosition(MissionInfo info1, MissionInfo info2) {
        service.exchangeMissionPosition(info1, info2);
    }

    @Override
    public void isForbidMobileNetworkUpload(boolean isForbid) {
        service.isForbidMobileNetworkUpload(isForbid);
    }
}
