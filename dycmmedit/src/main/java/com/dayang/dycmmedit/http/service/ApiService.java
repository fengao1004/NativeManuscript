package com.dayang.dycmmedit.http.service;

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
import com.dayang.login.Info;
import com.dayang.login.LoginActivity;
import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by 冯傲 on 2017/5/2.
 * e-mail 897840134@qq.com
 */

public interface ApiService {

    @POST("UserInfoController/checkDog.do")
    Observable<RequestCheckDogInfo> checkDog();

    @FormUrlEncoded
    @POST("UserInfoController/loginNewH5.do")
//    Observable<UserInfo> loginNewH5(@Body JsonObject json);
    Observable<UserInfo> loginNewH5(@Field("userId") String userId, @Field("tokenId") String tokenId);


    @FormUrlEncoded
    @POST("ManuscriptController/{url}")
    Observable<ResultManuscriptListInfo> getManuscriptByPage(@FieldMap Map<String, String> fields, @Path("url") String url);

    //    @POST("api/projService")
//    Observable<JsonObject> login(@Body MainPresenterImpl.LoginInfo json);
    @FormUrlEncoded
    @POST("ManuscriptController/saveManuscript.do")
    Observable<ResultSaveManuscriptInfo> saveManuscript(@FieldMap Map<String, String> fields1, @FieldMap Map<String, Integer> fields2);

    @FormUrlEncoded
    @POST("ManuscriptController/saveManuscript.do")
    Observable<ResultSaveManuscriptInfo> saveManuscript(@FieldMap Map<String, String> fields1);

    @FormUrlEncoded
    @POST("ManuscriptController/saveManuscript.do")
    Observable<ResultSaveManuscriptInfo> createWechatManuscript(@FieldMap Map<String, String> fields1, @Field("fathersonmark") int type);

    //复制稿件
    @FormUrlEncoded
    @POST("ManuscriptController/copyManuscript.do")
    Observable<ResultSaveManuscriptInfo> copyManuscript(@FieldMap Map<String, String> fields);

    //删除稿件
    @FormUrlEncoded
    @POST("ManuscriptController/delManuscript.do")
    Observable<ResultCommonInfo> delManuscript(@FieldMap Map<String, String> fields);

    //提交稿件
    @FormUrlEncoded
    @POST("ManuscriptController/submitManuscript.do")
    Observable<ResultCommonInfo> submitManuscript(@FieldMap Map<String, String> fields);

    //审核稿件
    @FormUrlEncoded
    @POST("ManuscriptController/auditManuscript.do")
    Observable<ResultCommonInfo> auditManuscript(@FieldMap Map<String, String> field1, @FieldMap Map<String, Integer> field2);

    //获取上传地址
    @POST("System/SystemparameterController/getParamInfo.do")
    Observable<ResultGetParamInfo> getParamInfo();

    //获取微博图片
    @FormUrlEncoded
    @POST("UseAttachmentController/getUseAttachment.do")
    Observable<ResultGetUseAttachmentInfo> getUseAttachment(@FieldMap Map<String, String> fields);

    //删除微博图片
    @FormUrlEncoded
    @POST("UseAttachmentController/delPicByManuscript.do")
    Observable<ResultCommonInfo> delPicByManuscript(@FieldMap Map<String, String> fields);

    //获取稿件详情（微信稿件调用）
    @FormUrlEncoded
    @POST("ManuscriptController/loadManuscript.do")
    Observable<ResultLoadManuscriptInfo> loadManuscript(@FieldMap Map<String, String> fields);

    //交换稿件顺序（微信稿件调用）
    @FormUrlEncoded
    @POST("ManuscriptController/upPosition.do")
    Observable<ResultCommonInfo> upPosition(@Field("upManuscriptId") String upManuscriptId, @Field("downManuscriptId") String downManuscriptId, @Field("isNO1") int isNO1);

    //锁定稿件
    @FormUrlEncoded
    @POST("ManuscriptController/lockManuscript.do")
    Observable<ResultCommonInfo> lockManuscript(@FieldMap Map<String, String> fields);

    //解锁稿件
    @FormUrlEncoded
    @POST("ManuscriptController/unlockManuscript.do")
    Observable<ResultCommonInfo> unlockManuscript(@FieldMap Map<String, String> fields);

    //是否可以审核
    @FormUrlEncoded
    @POST("ManuscriptController/isCanAuditManuscript.do")
    Observable<ResultCommonInfo> isCanAuditManuscript(@FieldMap Map<String, String> fields);

    //获取cre用户信息(审核人)
    @FormUrlEncoded
    @POST("ManuscriptController/getCreUserList.do")
    Observable<ResultGetCreUserList> getCreUserList(@FieldMap Map<String, String> fields);

    //获取电视分发系统
    @FormUrlEncoded
    @POST("ManuscriptController/getTVTargetSystem.do")
    Observable<JsonObject> getTVTargetSystem(@FieldMap Map<String, String> fields);

    //获取微博分发系统
    @FormUrlEncoded
    @POST("ManuscriptController/getTargetweiboSystem.do")
    Observable<JsonObject> getTargetweiboSystem(@FieldMap Map<String, String> fields);

    //获取微信分发系统
    @FormUrlEncoded
    @POST("ManuscriptController/getTargetweixinSystem.do")
    Observable<JsonObject> getTargetweixinSystem(@FieldMap Map<String, String> fields);

    //获取网页分发系统
    @FormUrlEncoded
    @POST("ManuscriptController/getWebTargetSystem.do")
    Observable<JsonObject> getWebTargetSystem(@FieldMap Map<String, String> fields);

    //获取审核策略
    @FormUrlEncoded
    @POST("ManuscriptController/censorStrategyExist.do")
    Observable<ResultCensorStrategyExist> censorStrategyExist(@FieldMap Map<String, String> fields);


    //获取选题信息
    @FormUrlEncoded
    @POST("ManuscriptController/getCreFolder.do")
    Observable<ResultGetCreFolder> getCreFolder(@FieldMap Map<String, String> fields);

    //保存下级审核人
    @FormUrlEncoded
    @POST("ManuscriptController/auditAddCensorAuditor.do")
    Observable<ResultCommonInfo> auditAddCensorAuditor(@FieldMap Map<String, String> fields);


    //审核时，获取分发目标
    @FormUrlEncoded
    @POST("ManuscriptController/getTargetSystemByManuscriptId.do")
    Observable<JsonObject> getTargetSystemByManuscriptId(@FieldMap Map<String, String> fields);

    //判断是否有存储空间
    @FormUrlEncoded
    @POST("ManuscriptController/IsOutOfMemoryOfTVManuscript.do")
    Observable<ResultCommonInfo> IsOutOfMemoryOfTVManuscript(@FieldMap Map<String, String> fields);
}
