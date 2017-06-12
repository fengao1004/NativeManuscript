package com.dayang.uploadfile.upload;

import android.os.Handler;
import android.util.Log;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;

/**
 * 此类用于实现http上传
 *
 * @author renyuwei
 */
public class HttpUpload extends UploadFileThread {
    private String httpUrl;
    private String localPath;
    private Handler handler;
    private String taskId;
    private String fileStatusNotifyURL;

    public HttpUpload(String url, String localPath, String fileStatusNotifyURL, Handler handler, String taskId) {
        super(null);
        this.httpUrl = url;
        this.localPath = localPath;
        this.handler = handler;
        this.taskId = taskId;
        this.fileStatusNotifyURL = fileStatusNotifyURL;
    }

    String TAG = "fengao";

    public boolean uploadSingleFile() {
        boolean uploadFlag = false;
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost httppost = new HttpPost(this.httpUrl);
            File uploadFile = new File(this.localPath);
            FileBody bin = new FileBody(uploadFile);
            StringBody taskId = new StringBody(this.taskId);
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("filename", bin);// file为请求后台的File upload;属性
            reqEntity.addPart("taskId", taskId);
            httppost.setEntity(reqEntity);
            HttpResponse response = httpclient.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                System.out.println("服务器正常响应.....");
                Log.i(TAG, "uploadSingleFile: " + "结果只为true");
                uploadFlag = true;
            }
        } catch (Exception e) {
            Log.e("debug", e.toString());//
        } finally {
            try {
                httpclient.getConnectionManager().shutdown();
            } catch (Exception ignore) {
                Log.e("debug", ignore.toString());
            }
        }
//        this.fileStatusNotifyCallBack(fileStatusNotifyURL, getNotifyRequestParam(uploadFlag, this.localPath, this.taskId));
        return uploadFlag;
    }

    /**
     * 用于上传和上传后的消息通知
     */
    @Override
    public void run() {
        boolean upload;
        Log.i(TAG, "run: " + "http");
        try {
            Log.i(TAG, "run: " + "单个上传");
            upload = this.uploadSingleFile();
            if (upload) {
                Log.i(TAG, "run: 5");
                this.handler.sendEmptyMessage(Constants.UPLOADSUCCESS);
            } else {
                this.handler.sendEmptyMessage(Constants.UPLOADFAILTURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
