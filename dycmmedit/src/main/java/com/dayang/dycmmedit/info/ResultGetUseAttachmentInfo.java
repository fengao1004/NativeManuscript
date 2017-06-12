package com.dayang.dycmmedit.info;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/12.
 * e-mail 897840134@qq.com
 */

public class ResultGetUseAttachmentInfo {

    /**
     * data : [{"attachresultdetaiid":"","attachsourceid":"C3808C50-A397-46AF-BBE1-C6F7CDB0B1AF","manuscriptid":"E4483174-BD1A-4F43-99A3-4A4563ED42F9","quotetype":0,"requestid":"","requestname":"","totallength":"","useattachid":"DEB7C6D0-5F4F-4E1C-8B88-2752D15733BF","useattachname":"2.jpg","useattachsize":"8372","useattachtype":4,"usesharepath":"http://100.0.1.67:8080/images/weixinIcon\\20170426\\2.jpg"}]
     * msg :
     * status : true
     */

    private String msg;
    private boolean status;
    private List<DataEntity> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * attachresultdetaiid :
         * attachsourceid : C3808C50-A397-46AF-BBE1-C6F7CDB0B1AF
         * manuscriptid : E4483174-BD1A-4F43-99A3-4A4563ED42F9
         * quotetype : 0
         * requestid :
         * requestname :
         * totallength :
         * useattachid : DEB7C6D0-5F4F-4E1C-8B88-2752D15733BF
         * useattachname : 2.jpg
         * useattachsize : 8372
         * useattachtype : 4
         * usesharepath : http://100.0.1.67:8080/images/weixinIcon\20170426\2.jpg
         */

        private String attachresultdetaiid;
        private String attachsourceid;
        private String manuscriptid;
        private int quotetype;
        private String requestid;
        private String requestname;
        private String totallength;
        private String useattachid;
        private String useattachname;
        private String useattachsize;
        private int useattachtype;
        private String usesharepath;

        public String getAttachresultdetaiid() {
            return attachresultdetaiid;
        }

        public void setAttachresultdetaiid(String attachresultdetaiid) {
            this.attachresultdetaiid = attachresultdetaiid;
        }

        public String getAttachsourceid() {
            return attachsourceid;
        }

        public void setAttachsourceid(String attachsourceid) {
            this.attachsourceid = attachsourceid;
        }

        public String getManuscriptid() {
            return manuscriptid;
        }

        public void setManuscriptid(String manuscriptid) {
            this.manuscriptid = manuscriptid;
        }

        public int getQuotetype() {
            return quotetype;
        }

        public void setQuotetype(int quotetype) {
            this.quotetype = quotetype;
        }

        public String getRequestid() {
            return requestid;
        }

        public void setRequestid(String requestid) {
            this.requestid = requestid;
        }

        public String getRequestname() {
            return requestname;
        }

        public void setRequestname(String requestname) {
            this.requestname = requestname;
        }

        public String getTotallength() {
            return totallength;
        }

        public void setTotallength(String totallength) {
            this.totallength = totallength;
        }

        public String getUseattachid() {
            return useattachid;
        }

        public void setUseattachid(String useattachid) {
            this.useattachid = useattachid;
        }

        public String getUseattachname() {
            return useattachname;
        }

        public void setUseattachname(String useattachname) {
            this.useattachname = useattachname;
        }

        public String getUseattachsize() {
            return useattachsize;
        }

        public void setUseattachsize(String useattachsize) {
            this.useattachsize = useattachsize;
        }

        public int getUseattachtype() {
            return useattachtype;
        }

        public void setUseattachtype(int useattachtype) {
            this.useattachtype = useattachtype;
        }

        public String getUsesharepath() {
            return usesharepath;
        }

        public void setUsesharepath(String usesharepath) {
            this.usesharepath = usesharepath;
        }
    }
}
