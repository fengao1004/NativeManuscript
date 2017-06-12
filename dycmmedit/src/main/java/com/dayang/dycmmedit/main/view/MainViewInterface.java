package com.dayang.dycmmedit.main.view;

import com.dayang.dycmmedit.base.BaseViewInterface;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.info.UserInfo;
import com.dayang.dycmmedit.info.UserListAndTargetSystem;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/2.
 * e-mail 897840134@qq.com
 */

public interface MainViewInterface extends BaseViewInterface {
    void refreshList(List<ManuscriptListInfo> list);

    void setMaxPage(int page);

    void showAuditDialog(ManuscriptListInfo manuscriptListInfo, boolean isCanAuditManuscript);

    void refresh();

    int getPage();

    String getResourceType();

    String getStates();

    String getDateranges();

    String getSearchTitle();

    String getManuscriptType();

    void showLoading();

    void makeToast(String text);

    void removeLoading();

    void enterRedact(ManuscriptListInfo manuscriptListInfo);

    void enterRedact(boolean isLock, ManuscriptListInfo manuscriptListInfo);

    void setIsLoadData(boolean isLoadData);

    void setUserInfo(UserInfo userInfo);

    void showTextDialog(String title, String message);

    void showTextDialog(String message);

    void showSubmitDialog(UserListAndTargetSystem system, ManuscriptListInfo manuscriptListInfo);

    void showAuditDialog(UserListAndTargetSystem system, ManuscriptListInfo manuscriptListInfo);

}
