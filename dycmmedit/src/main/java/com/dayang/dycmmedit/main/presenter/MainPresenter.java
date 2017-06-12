package com.dayang.dycmmedit.main.presenter;

import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.info.RequestAuditManuscript;
import com.dayang.dycmmedit.info.RequestSubmitManuscript;
import com.dayang.dycmmedit.info.UserListAndTargetSystem;

/**
 * Created by 冯傲 on 2017/5/2.
 * e-mail 897840134@qq.com
 */

public interface MainPresenter {
    void loadData();

    void createManuscript(ManuscriptListInfo info);

    void copyManuscript(ManuscriptListInfo info, int type);

    void deleteManuscript(ManuscriptListInfo info);

    void submitManuscript(RequestSubmitManuscript system, ManuscriptListInfo info);

    void auditManuscript(ManuscriptListInfo info,RequestAuditManuscript auditManuscript);

    void lockManuscript(ManuscriptListInfo info);

    void unLockManuscript(ManuscriptListInfo info);

    void queryIsCanAuditManuscript(ManuscriptListInfo info);

    void getSubmitMessage(ManuscriptListInfo info);

    void getAuditMessage(ManuscriptListInfo info);
}
