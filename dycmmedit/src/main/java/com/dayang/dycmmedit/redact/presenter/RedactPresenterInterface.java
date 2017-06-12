package com.dayang.dycmmedit.redact.presenter;

import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.info.RequestAuditManuscript;
import com.dayang.dycmmedit.info.RequestSubmitManuscript;

/**
 * Created by 冯傲 on 2017/5/9.
 * e-mail 897840134@qq.com
 */

public interface RedactPresenterInterface {
    void save(ManuscriptListInfo createManuscriptInfo, boolean isBack);

    void submit(ManuscriptListInfo createManuscriptInfo);

    void uploadFile(String path, String taskId, int requestCode);

    void getWeiboImage(ManuscriptListInfo createManuscriptInfo);

    void delWeiboImage(ManuscriptListInfo createManuscriptInfo);

    void getHistoryData(ManuscriptListInfo manuscriptListInfo);

    void getSubmitMessage(ManuscriptListInfo info);

    void submitManuscript( RequestSubmitManuscript submitManuscript,ManuscriptListInfo info);

    void getAuditMessage(ManuscriptListInfo info);

    void auditManuscript(ManuscriptListInfo info,RequestAuditManuscript auditManuscript);

}
