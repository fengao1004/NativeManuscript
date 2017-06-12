package com.dayang.uploadfile.upload;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import com.dayang.dyhfileuploader.DYHFileUploadInfo;
import com.dayang.dyhfileuploader.DYHFileUploadTask;
import com.dayang.dyhfileuploader.DYHFileUploader;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.util.Date;


/**
 * 此类用于实现Ftp上传
 *
 * @author renyuwei
 */
public class FtpUpload extends UploadFileThread {

    private static final String TAG = "fengao";
    private static FTPClient ftpClient = new FTPClient();
    private String ftpUrl;
    private String port;
    private String username;
    private String password;
    private String remotePath;
    private String localPath;
    private Handler handler;
    private boolean isWoring;
    private boolean isOk;
    private String taskId;
    private String fileStatusNotifyURL;
    private DYHFileUploader fileUploader = null;
    private String dir;
    private DYHFileUploadTask taskInfo;
    private DYHFileUploader.OnInfoUpdatedListener mStatusUpdatedListener = new DYHFileUploader.OnInfoUpdatedListener() {
        @Override
        public void onInfoUpdated(DYHFileUploadInfo dyhFileUploadInfo) {
            int status = dyhFileUploadInfo.status;
            switch (status) {
                case DYHFileUploadInfo.StatusError:
                    Log.i(TAG, "StatusError");
                    isOk = false;
                    break;
                case DYHFileUploadInfo.StatusFinished:
                    Log.i(TAG, "StatusFinished");
                    isWoring = false;
                    break;
                case DYHFileUploadInfo.StatusProcessing:
                    Log.i(TAG, "StatusProcessing" + dyhFileUploadInfo.uploadedBytes);
                    break;
                case DYHFileUploadInfo.StatusStopped:
                    Log.i(TAG, "StatusStopped");
                    break;
                case DYHFileUploadInfo.StatusUnkown:
                    Log.i(TAG, "StatusUnkown");
                    break;
                default:
                    break;
            }
        }
    };

    public FtpUpload(String url, String localPath, String fileStatusNotifyURL, Handler handler, String taskId) {
        super(null);
        this.fileStatusNotifyURL = fileStatusNotifyURL;
        this.handler = handler;
        this.taskId = taskId;
        this.taskId = taskId;
        this.localPath = localPath;
        solveFtpPath(url);
        fileUploader = new DYHFileUploader();
        fileUploader.init(DYHFileUploader.TypeFTP);
        fileUploader.setOnInfoUpdatedListener(mStatusUpdatedListener);
    }

    /**
     * 解析ftp路径地址
     *
     * @param ftpPath
     */
    public void solveFtpPath(String ftpPath) {
        if (ftpPath.endsWith("/")) {
            ftpPath = ftpPath.substring(0, ftpPath.length() - 1);
        }
        String pathArr[];
        if (ftpPath != null) {
            Log.i(TAG, " path :   " + ftpPath);
            pathArr = ftpPath.split(":");
            this.username = pathArr[1].substring(2);
            this.password = pathArr[2].substring(0, pathArr[2].indexOf("@"));
            this.ftpUrl = pathArr[2].substring(pathArr[2].indexOf("@") + 1);
            if (!pathArr[3].contains("/")) {
                dir = this.taskId;
                this.port = pathArr[3];
                this.remotePath = "ftp://" + ftpUrl + ":" + pathArr[3] + "/"
                        + this.taskId + "/";
            } else {
                this.port = pathArr[3].substring(0, pathArr[3].indexOf("/"));
                this.remotePath = "ftp://" + ftpUrl + ":" + port + "/"
                        + pathArr[3].substring(pathArr[3].indexOf("/") + 1)
                        + "/" + this.taskId + "/";
                dir = pathArr[3].substring(pathArr[3].indexOf("/") + 1) + "/"
                        + this.taskId;
            }
        }
    }


    public void makeDirectory() {
        try {
            ftpClient.enterLocalPassiveMode();
            int portnum = Integer.valueOf(this.port);
            ftpClient.connect(this.ftpUrl, portnum);
            boolean loginResult = ftpClient.login(username, password);
            int returnCode = ftpClient.getReplyCode();
            if (loginResult && FTPReply.isPositiveCompletion(returnCode)) {// 如果登录成功
                String directory = new String(dir.getBytes("gbk"), "ISO8859-1");
                boolean ret = ftpClient.makeDirectory(directory);
                Log.i(TAG, "ret   " + ret);
            }
            ftpClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ftp连接", "连接失败！");
        }
    }

    /**
     * 文件上传功能的实现，批量上传
     *
     * @param fileNameList
     * @return
     */
    public boolean uploadAllFiles() {
        boolean uploadStatus;
        isWoring = true;
        isOk = true;
        taskInfo = null;
        taskInfo = getTaskInfoFromUI();
        fileUploader.setTask(taskInfo);
        boolean ret = fileUploader.start(false);
        if (!ret) {
            uploadStatus = false;
        } else {
            while (isWoring) {
                SystemClock.sleep(50);
                if (!isOk) {
                    break;
                }
            }
            if (!isOk) {
                uploadStatus = false;
            } else {
                uploadStatus = true;
            }
        }
        fileStatusNotifyCallBack(fileStatusNotifyURL,
                getNotifyRequestParam(uploadStatus, localPath, taskId));
        return uploadStatus;
    }


    /**
     * 线程的run方法，用于实现文件上传和完成后的消息通知
     */
    public void run() {
        boolean upload;
        try {
            makeDirectory();
            upload = this.uploadAllFiles();
            if (upload) {
                this.handler.sendEmptyMessage(Constants.UPLOADSUCCESS);
            } else {
                this.handler.sendEmptyMessage(Constants.UPLOADFAILTURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DYHFileUploadTask getTaskInfoFromUI() {
        DYHFileUploadTask task = new DYHFileUploadTask();
        Date date = new Date();
        String time = date.getTime() + "";
        String s = time.substring(time.length() - 3);
        task.sessionID = taskId.substring(0,taskId.length() - 3) + s;
        task.FTPMode = DYHFileUploadTask.FTPModePassivePASV;
        String[] split = localPath.split("/");
        task.remoteUrl = remotePath + split[split.length - 1];
        task.localUrl = this.localPath;
        task.resume = true;
        task.user = username;
        task.password = password;
        task.uploadTrunkInfoURL = "";
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
