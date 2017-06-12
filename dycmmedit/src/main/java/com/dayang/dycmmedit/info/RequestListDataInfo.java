package com.dayang.dycmmedit.info;

/**
 * Created by 冯傲 on 2017/5/2.
 * e-mail 897840134@qq.com
 */

public class RequestListDataInfo {


    public RequestListDataInfo(String resourceType, int page, String states, String manuscriptType, String dateranges, String title, String usercode, String privilegeIds, String columnListIds, String allColumnIds) {
        this.resourceType = resourceType;
        this.page = page;
        this.states = states;
        this.manuscriptType = manuscriptType;
        this.dateranges = dateranges;
        this.title = title;
        this.usercode = usercode;
        this.privilegeIds = privilegeIds;
        this.columnListIds = columnListIds;
        this.allColumnIds = allColumnIds;
    }

    /**
     * resourceType : 1
     * page : 1
     * states :
     * manuscriptType :
     * dateranges :
     * title :
     * usercode : 0003
     * privilegeIds : PID_CMEDIT_WRITESCRIPTS,PID_CMEDIT_DELALLSCRIPTS,PID_CMEDIT_EXPORTWORD,PID_CMEDIT_EDITALLSCRIPTS,PID_CMEDIT_CONFIGURE,PID_CMEDIT_AUDITSCRIPTS
     * columnListIds : 00001,007,00006,001122,00006,003
     * allColumnIds :
     */


    private String resourceType;
    private int page;
    private String states;
    private String manuscriptType;
    private String dateranges;
    private String title;
    private String usercode;
    private String privilegeIds;
    private String columnListIds;
    private String allColumnIds;

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getManuscriptType() {
        return manuscriptType;
    }

    public void setManuscriptType(String manuscriptType) {
        this.manuscriptType = manuscriptType;
    }

    public String getDateranges() {
        return dateranges;
    }

    public void setDateranges(String dateranges) {
        this.dateranges = dateranges;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getPrivilegeIds() {
        return privilegeIds;
    }

    public void setPrivilegeIds(String privilegeIds) {
        this.privilegeIds = privilegeIds;
    }

    public String getColumnListIds() {
        return columnListIds;
    }

    public void setColumnListIds(String columnListIds) {
        this.columnListIds = columnListIds;
    }

    public String getAllColumnIds() {
        return allColumnIds;
    }

    public void setAllColumnIds(String allColumnIds) {
        this.allColumnIds = allColumnIds;
    }
}
