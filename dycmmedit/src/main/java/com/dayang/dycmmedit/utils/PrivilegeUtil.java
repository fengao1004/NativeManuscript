package com.dayang.dycmmedit.utils;

import com.dayang.dycmmedit.info.ManuscriptListInfo;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/18.
 * e-mail 897840134@qq.com
 */

public class PrivilegeUtil {
    public static final int PRIVILEGE_SUBMIT = 12;
    public static final int PRIVILEGE_AUDIT = 13;
    public static final int PRIVILEGE_DELETE = 14;
    public static final int PRIVILEGE_COPY = 15;
    public static final int PRIVILEGE_WRITE = 16;

    static String PID_CMEDIT_WRITESCRIPTS = "PID_CMEDIT_WRITESCRIPTS";
    static String PID_CMEDIT_DELALLSCRIPTS = "PID_CMEDIT_DELALLSCRIPTS";
    static String PID_CMEDIT_EDITALLSCRIPTS = "PID_CMEDIT_EDITALLSCRIPTS";
    static String PID_CMEDIT_CONFIGURE = "PID_CMEDIT_CONFIGURE";
    static String PID_CMEDIT_AUDITSCRIPTS = "PID_CMEDIT_AUDITSCRIPTS";

    public static boolean hasPrivilege(int requestCode, ManuscriptListInfo info) {
        List<String> authorityList = PublicResource.getInstance().getAuthorityList();
        String userCode = PublicResource.getInstance().getUserCode();
        switch (requestCode) {
            case PRIVILEGE_SUBMIT:
                if (authorityList.contains(PID_CMEDIT_CONFIGURE)) {
                    return true;
                } else if (authorityList.contains(PID_CMEDIT_WRITESCRIPTS)) {
                    if (userCode.equals(info.usercode) && (info.status == ManuscriptListInfo.MANUSCRIPT_STATUS_FAIL || info.status == ManuscriptListInfo.MANUSCRIPT_STATUS_WAITING_SUBMIT)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case PRIVILEGE_AUDIT:
                if (authorityList.contains(PID_CMEDIT_CONFIGURE)) {
                    return true;
                } else if (authorityList.contains(PID_CMEDIT_AUDITSCRIPTS)) {
                    return true;
                } else {
                    return false;
                }
            case PRIVILEGE_DELETE:
                if (authorityList.contains(PID_CMEDIT_CONFIGURE)) {
                    return true;
                } else if (authorityList.contains(PID_CMEDIT_DELALLSCRIPTS)) {
                    return true;
                } else if (authorityList.contains(PID_CMEDIT_WRITESCRIPTS)) {
                    if (userCode.equals(info.usercode) && (info.status == ManuscriptListInfo.MANUSCRIPT_STATUS_FAIL || info.status == ManuscriptListInfo.MANUSCRIPT_STATUS_WAITING_SUBMIT)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case PRIVILEGE_COPY:
                if (authorityList.contains(PID_CMEDIT_CONFIGURE)) {
                    return true;
                } else if (authorityList.contains(PID_CMEDIT_WRITESCRIPTS)) {
                    return true;
                } else {
                    return false;
                }
            case PRIVILEGE_WRITE:
                if (authorityList.contains(PID_CMEDIT_CONFIGURE)) {
                    return true;
                } else if (authorityList.contains(PID_CMEDIT_EDITALLSCRIPTS)) {
                    return true;
                } else if (authorityList.contains(PID_CMEDIT_WRITESCRIPTS)) {
                    if (userCode.equals(info.usercode) && (info.status == ManuscriptListInfo.MANUSCRIPT_STATUS_FAIL || info.status == ManuscriptListInfo.MANUSCRIPT_STATUS_WAITING_SUBMIT)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
        }


        return false;
    }
}
