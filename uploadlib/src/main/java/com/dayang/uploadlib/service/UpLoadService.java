package com.dayang.uploadlib.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dayang.uploadlib.iterface.UpLoadServiceInterface;
import com.dayang.uploadlib.model.MissionInfo;
import com.dayang.uploadlib.receiver.NetworkConnectChangedReceiver;
import com.dayang.uploadlib.task.HttpUploadTask;
import com.dayang.uploadlib.util.NetWorkState;
import com.dayang.uploadlib.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by 冯傲 on 2017/6/1.
 * e-mail 897840134@qq.com
 */

public class UpLoadService extends Service implements UpLoadServiceInterface {
    String TAG = "UpLoadService";
    List<MissionInfo> infoList;
    //允许同时上传的最大线程数 会从sp中读取 如果读不到就是3
    ExecutorService threadPool;
    private NetworkConnectChangedReceiver mNetworkChangeListener;

    @Override
    public void onCreate() {
        super.onCreate();
        //从SP中获取最大线程数量
        //从SP中获取是否可以在4g环境下上传
        threadPool = Executors.newFixedThreadPool(5);
        //注册广播接收者
        IntentFilter filter = new IntentFilter();
        mNetworkChangeListener = new NetworkConnectChangedReceiver();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(mNetworkChangeListener, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //卸载广播接收者
        unregisterReceiver(mNetworkChangeListener);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new UploadServiceBinder(this);
    }


    @Override
    public synchronized void pauseAllMission() {
        for (MissionInfo info : infoList) {
            if (info.getStatus() == MissionInfo.UPLOADING || info.getStatus() == MissionInfo.WAITINGUPLOAD || info.getStatus() == MissionInfo.WAITINGNETWORDK || info.getStatus() == MissionInfo.WAITINGNETWORDK_WIFI)
                info.pauseMission();
        }
    }

    @Override
    public synchronized void startAllMission() {
        for (MissionInfo info : infoList) {
            if (info.getStatus() == MissionInfo.PAUSEING)
                startTask(info);
        }
    }

    @Override
    public void setMaxUploadMissionCount(int max) {
        max = max > 5 ? 5 : max < 1 ? 1 : max;
        SharedPreferencesUtils.setParam(this, "maxThreadCount", max);
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
        if (infoList == null) {
            infoList = new ArrayList<>();
        }
        int min = getMinimumPriority();
        info.setPriority(min - 1);
        infoList.add(info);
        startTask(info);
    }

    private int getUploadTaskCount() {
        int count = 0;
        for (MissionInfo info : infoList) {
            if ((info.getStatus() == MissionInfo.UPLOADING) || (info.getStatus() == MissionInfo.WAITINGNETWORDK) || (info.getStatus() == MissionInfo.WAITINGNETWORDK_WIFI))
                count++;
        }
        return count;
    }

    /**
     * 调用时间
     * 恢复暂停的任务时
     * TODO 策略考虑恢复时假如没有空闲线程 1 做等待操作 2 暂停一个优先级最低的任务，开启此任务 3 选取优先级最低的上传任务 做优先级判断 暂时第一种策略
     * 往任务池里添加任务后  这个时候任务的状态应该都是等待上传
     * 从数据库读出来缓存任务后 TODO 在这个操作中有一层判断在上级做 就是这时只会添加 UPLOADING状态的任务
     * 此方法只会判断任务池是否还有空闲，和对没网4g wifi的判断
     *
     * @param info 上传任务对象
     * @return void
     */
    private synchronized void startTask(MissionInfo info) {
        int uploadTaskCount = getUploadTaskCount();
        Integer maxThreadCount = SharedPreferencesUtils.getParam(this, "maxThreadCount", 3);
        if (uploadTaskCount < maxThreadCount) {
            //获取4g wifi策略
            //true 禁止4g上传 false允许4g上传
            Boolean forbidMobileNetworkUpload = SharedPreferencesUtils.getParam(this, "forbidMobileNetworkUpload", false);
            int netType = NetWorkState.GetNetype(this);
            if (netType == NetWorkState.NONE) {
                info.setStatus(MissionInfo.WAITINGNETWORDK);
            } else if (netType == NetWorkState.MOBILE && forbidMobileNetworkUpload) {
                info.setStatus(MissionInfo.WAITINGNETWORDK_WIFI);
            } else {
                info.setStatus(MissionInfo.UPLOADING);
                threadPool.execute(new HttpUploadTask(info, this));
            }
        } else {
            info.setStatus(MissionInfo.WAITINGUPLOAD);
        }
    }


    /**
     * 上传任务完成之后被调用，判断list里边是否还有等待上传的任务，如果有挑取优先级最高的任务进行上传，判读的时候需要保证线程安全
     * 在任务线程被调用
     *
     * @param
     * @return
     */
    public synchronized void taskComplete() {
        MissionInfo info = getUploadTask();
        if (info != null) {
            startTask(info);
        }
    }

    /**
     * 获取下一个上传对象
     *
     * @param parameter
     * @return
     */
    private MissionInfo getUploadTask() {
        if (infoList.size() == 0)
            return null;
        int index = -1;
        int maxPriority = -1;
        for (int i = 1; i < infoList.size(); i++) {
            if (infoList.get(i).getStatus() == MissionInfo.WAITINGUPLOAD) {
                if (maxPriority == -1) {
                    maxPriority = infoList.get(i).getPriority();
                    index = i;
                    continue;
                }
                if (maxPriority < infoList.get(i).getPriority()) {
                    maxPriority = infoList.get(i).getPriority();
                    index = i;
                }
            }
        }
        if (index == -1)
            return null;
        else
            return infoList.get(index);

    }

    private int getMinimumPriority() {
        if (infoList.size() == 0)
            return 1000000;
        int min = infoList.get(0).getPriority();
        for (int i = 1; i < infoList.size(); i++) {
            min = infoList.get(i).getPriority() < min ? infoList.get(i).getPriority() : min;
        }
        return min;
    }

    @Override
    public void addMissions(List<MissionInfo> infoList) {
        for (MissionInfo info : infoList) {
            addMission(info);
        }
    }

    @Override
    public List<MissionInfo> getMissions() {
        return infoList;
    }

    @Override
    public void exchangeMissionPosition(MissionInfo info1, MissionInfo info2) {
        int priority1 = info1.getPriority();
        int priority2 = info2.getPriority();
        info1.setPriority(priority2);
        info2.setPriority(priority1);
        Collections.swap(infoList, infoList.indexOf(info1), infoList.indexOf(info2));
    }

    @Override
    public void isForbidMobileNetworkUpload(boolean isForbid) {
        SharedPreferencesUtils.setParam(this, "forbidMobileNetworkUpload", false);
    }
}
