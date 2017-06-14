package com.dayang.dycmmedit.http;

import android.content.Context;
import android.text.TextUtils;

import com.dayang.dycmmedit.http.service.ApiService;
import com.dayang.dycmmedit.info.RequestCheckDogInfo;
import com.dayang.dycmmedit.info.RequestListDataInfo;
import com.dayang.dycmmedit.info.RequestLoginNewH5Info;
import com.dayang.dycmmedit.info.ResultCensorStrategyExist;
import com.dayang.dycmmedit.info.ResultCommonInfo;
import com.dayang.dycmmedit.info.ResultGetCreFolder;
import com.dayang.dycmmedit.info.ResultGetCreUserList;
import com.dayang.dycmmedit.info.ResultGetParamInfo;
import com.dayang.dycmmedit.info.ResultGetUseAttachmentInfo;
import com.dayang.dycmmedit.info.ResultLoadManuscriptInfo;
import com.dayang.dycmmedit.info.ResultManuscriptListInfo;
import com.dayang.dycmmedit.info.ResultSaveManuscriptInfo;
import com.dayang.dycmmedit.info.UserInfo;
import com.dayang.dycmmedit.main.presenter.MainPresenterImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Subscriber;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

/**
 * Created by 冯傲 on 2017/5/2.
 * e-mail 897840134@qq.com
 */

public class RetrofitHelper {
    private static final int DEFAULT_TIMEOUT = 5;

    private ApiService apiService;

    private OkHttpClient okHttpClient;
    public static String baseUrl = "http://192.168.10.85:8081/DYCMMEdit/";
    //    public static String baseUrl = "http://100.0.1.67:8080/DYCMMEdit/";
    private static Context mContext;


    private static class SingletonHolder {
        private static RetrofitHelper INSTANCE = new RetrofitHelper(
                mContext);
    }

    public static RetrofitHelper getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }

    private RetrofitHelper(Context context) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS);
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .addInterceptor(new BaseInterceptor(mContext))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        apiService = retrofit.create(ApiService.class);
    }


    public Observable<RequestCheckDogInfo> checkDog() {
        return apiService.checkDog();
    }

    public Observable<UserInfo> loginNewH5(String userId, String tokenId) {
        return apiService.loginNewH5(userId, tokenId);
    }

    public Observable<ResultManuscriptListInfo> getManuscriptByPage(Map<String, String> fields, String url) {
        return apiService.getManuscriptByPage(fields, url);
    }

    public Observable<ResultSaveManuscriptInfo> saveManuscript(Map<String, String> fields, Map<String, Integer> fields2) {
        return apiService.saveManuscript(fields, fields2);
    }

    public Observable<ResultSaveManuscriptInfo> saveManuscript(Map<String, String> fields) {
        return apiService.saveManuscript(fields);
    }

    public Observable<ResultSaveManuscriptInfo> copyManuscript(Map<String, String> fields) {
        return apiService.copyManuscript(fields);
    }

    public Observable<ResultCommonInfo> delManuscript(Map<String, String> fields) {
        return apiService.delManuscript(fields);
    }

    public Observable<ResultCommonInfo> submitManuscript(Map<String, String> fields) {
        return apiService.submitManuscript(fields);
    }

    public Observable<ResultCommonInfo> auditManuscript(Map<String, String> field1,Map<String,Integer> field2) {
        return apiService.auditManuscript(field1,field2);
    }

    public Observable<ResultGetParamInfo> getParamInfo() {
        return apiService.getParamInfo();
    }

    public Observable<ResultGetUseAttachmentInfo> getUseAttachment(Map<String, String> fields) {
        return apiService.getUseAttachment(fields);
    }

    public Observable<ResultCommonInfo> delPicByManuscript(Map<String, String> fields) {
        return apiService.delPicByManuscript(fields);
    }

    public Observable<ResultLoadManuscriptInfo> loadManuscript(Map<String, String> fields) {
        return apiService.loadManuscript(fields);
    }

    public Observable<ResultSaveManuscriptInfo> createWechatManuscript(Map<String, String> fields1, int type) {
        return apiService.createWechatManuscript(fields1, type);
    }

    public Observable<ResultCommonInfo> upPosition(String upManuscriptId, String downManuscriptId, int isNO1) {
        return apiService.upPosition(upManuscriptId, downManuscriptId, isNO1);
    }

    public Observable<ResultCommonInfo> lockManuscript(Map<String, String> fields,Map<String, Integer> fields2) {
        return apiService.lockManuscript(fields,fields2);
    }

    public Observable<ResultCommonInfo> unlockManuscript(Map<String, String> fields) {
        return apiService.unlockManuscript(fields);
    }

    public Observable<ResultCommonInfo> isCanAuditManuscript(Map<String, String> fields) {
        return apiService.isCanAuditManuscript(fields);
    }

    public Observable<ResultGetCreUserList> getCreUserList(Map<String, String> fields) {
        return apiService.getCreUserList(fields);
    }

    public Observable<ResultGetCreFolder> getCreFolder(Map<String, String> fields) {
        return apiService.getCreFolder(fields);
    }

    public Observable<ResultCommonInfo> auditAddCensorAuditor(Map<String, String> fields) {
        return apiService.auditAddCensorAuditor(fields);
    }

    public Observable<ResultCommonInfo> IsOutOfMemoryOfTVManuscript(Map<String, String> fields) {
        return apiService.auditAddCensorAuditor(fields);
    }

    public Observable<JsonObject> getTVTargetSystem(Map<String, String> fields) {
        return apiService.getTVTargetSystem(fields);
    }

    public Observable<JsonObject> getTargetweiboSystem(Map<String, String> fields) {
        return apiService.getTargetweiboSystem(fields);
    }

    public Observable<JsonObject> getTargetweixinSystem(Map<String, String> fields) {
        return apiService.getTargetweixinSystem(fields);
    }

    public Observable<JsonObject> getWebTargetSystem(Map<String, String> fields) {
        return apiService.getWebTargetSystem(fields);
    }

    public Observable<JsonObject> getTargetSystemByManuscriptId(Map<String, String> fields) {
        return apiService.getTargetSystemByManuscriptId(fields);
    }

    public Observable<ResultCensorStrategyExist> censorStrategyExist(Map<String, String> fields) {
        return apiService.censorStrategyExist(fields);
    }
}
