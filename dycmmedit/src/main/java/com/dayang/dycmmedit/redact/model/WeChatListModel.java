package com.dayang.dycmmedit.redact.model;

import android.content.Context;

import com.dayang.dycmmedit.http.RetrofitHelper;
import com.dayang.dycmmedit.info.ResultCommonInfo;
import com.dayang.dycmmedit.info.ResultGetUseAttachmentInfo;
import com.dayang.dycmmedit.info.ResultLoadManuscriptInfo;
import com.dayang.dycmmedit.info.ResultSaveManuscriptInfo;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 冯傲 on 2017/5/13.
 * e-mail 897840134@qq.com
 */

public class WeChatListModel {
    private Context context;

    public WeChatListModel(Context context) {
        this.context = context;
    }

    public Observable<ResultLoadManuscriptInfo> loadManuscript(Map<String, String> fields) {
        return RetrofitHelper.getInstance(context).loadManuscript(fields);
    }

    public Observable<ResultSaveManuscriptInfo> createManuscript(Map<String, String> fields1, int type) {
        return RetrofitHelper.getInstance(context).createWechatManuscript(fields1, type);
    }

    public Observable<ResultCommonInfo> delManuscript(Map<String, String> fields) {
        return RetrofitHelper.getInstance(context).delManuscript(fields);
    }

    public Observable<ResultCommonInfo> exchange(String upManuscriptId, String downManuscriptId, int isNO1) {
        return RetrofitHelper.getInstance(context).upPosition(upManuscriptId, downManuscriptId, isNO1);
    }
}
