package com.dayang.dycmmedit.info;

/**
 * Created by 冯傲 on 2017/5/19.
 * e-mail 897840134@qq.com
 */

public class RequestSubmitManuscript {

    /**
     * manuscriptIds : 5E7ABC52-2B1C-456A-8D4C-BD92D3CE8F36
     * userCode : 0003
     * targetSystemIds : c7695dc9-7d3f-4510-b81a-dec5afbfe3ed_CRE,f841ed22-6c6b-4be0-a7f7-f592f9b1dc66_CRE
     * tokenid : TGT-692-UA2XQfX7BVzLObeX1VECdfS0IZccD9tluqOowqecQxxvfmMAP2-cas
     * username : 0003-禁止删除用户用于系统间联调
     * censorAuditor :
     * censorAuditorId :
     */

    private String manuscriptIds = "";
    private String userCode = "";
    private String targetSystemIds = "";
    private String tokenid = "";
    private String username = "";
    private String censorAuditor = "";
    private String censorAuditorId = "";

    public String getManuscriptIds() {
        return manuscriptIds;
    }

    public void setManuscriptIds(String manuscriptIds) {
        this.manuscriptIds = manuscriptIds;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getTargetSystemIds() {
        return targetSystemIds;
    }

    public void setTargetSystemIds(String targetSystemIds) {
        this.targetSystemIds = targetSystemIds;
    }

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCensorAuditor() {
        return censorAuditor;
    }

    public void setCensorAuditor(String censorAuditor) {
        this.censorAuditor = censorAuditor;
    }

    public String getCensorAuditorId() {
        return censorAuditorId;
    }

    public void setCensorAuditorId(String censorAuditorId) {
        this.censorAuditorId = censorAuditorId;
    }
}
