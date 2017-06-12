package com.dayang.dycmmedit.redact.view;

import com.dayang.dycmmedit.base.BaseViewInterface;
import com.dayang.dycmmedit.info.CensorRecordInfo;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.info.UserListAndTargetSystem;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/9.
 * e-mail 897840134@qq.com
 */

public interface RedactViewInterface extends BaseViewInterface {
    void upLoadFileComplete(String url, int requestCode);

    void insertImageForWeibo(String url);

    void setTextContent(String text);

    void setHasChange(boolean hasChange);

    void setWeixinImage(String weixinImage);

    void goBack();

    void setHistoryMessage( List<CensorRecordInfo> list);

    void showSubmitDialog(UserListAndTargetSystem system, ManuscriptListInfo manuscriptListInfo);

    void showTextDialog(String message);

    void showAuditDialog(UserListAndTargetSystem system, ManuscriptListInfo manuscriptListInfo);
}
