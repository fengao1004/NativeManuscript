package com.dayang.login;

import java.util.List;

/**
 * Created by 冯傲 on 2017/4/26.
 * e-mail 897840134@qq.com
 */

public class Info {

    /**
     * status : true
     * data : [{"backendservice":"http://lbappsrv-2079712928.cn-north-1.elb.amazonaws.com.cn/portal/api/applist","casservice":"http://tms.dayang.com;http://tms.dayang.com","description":"大洋测试环境","domainname":"dayang.com.cn","extendattribute":"","ispublic":1,"productcode":"cmtools","projectid":"","projectname":"大洋测试","projserviceguid":"76ff7acf-85df-4ed4-99df-c816155e1b92","status":0,"tenantid":"Dayang","noticeServiceUrl":"http://lbappsrv-2079712928.cn-north-1.elb.amazonaws.com.cn/announcement","messageServiceUrl":"http://123.127.220.187:8088/messageservice","getuiServiceUrl":"http://123.127.220.187:8088/messagecenter","gpsServiceUrl":""},{"backendservice":"http://192.168.10.116:8080/app/api/applist","casservice":"http://123.127.220.187:8088;http://123.127.220.187:8088","description":"大洋测试环境，CAS备用地址：http://100.0.10.201:8080","domainname":"dayang.com.cn","extendattribute":"","ispublic":0,"productcode":"cmtools","projectid":"","projectname":"大洋测试","projserviceguid":"d2e646f3-72ae-4299-b3d5-5aa2a418cf77","status":0,"tenantid":"Dayang","gpsServiceUrl":"http://192.168.10.116:8080/app","getuiServiceUrl":"http://100.0.1.201:8088/messagecenter","messageServiceUrl":"http://100.0.1.201:8088/messageservice","noticeServiceUrl":"http://192.168.10.116:8080/app"}]
     * description : 成功
     */

    private boolean status;
    private String description;
    private List<DataEntity> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * backendservice : http://lbappsrv-2079712928.cn-north-1.elb.amazonaws.com.cn/portal/api/applist
         * casservice : http://tms.dayang.com;http://tms.dayang.com
         * description : 大洋测试环境
         * domainname : dayang.com.cn
         * extendattribute :
         * ispublic : 1
         * productcode : cmtools
         * projectid :
         * projectname : 大洋测试
         * projserviceguid : 76ff7acf-85df-4ed4-99df-c816155e1b92
         * status : 0
         * tenantid : Dayang
         * noticeServiceUrl : http://lbappsrv-2079712928.cn-north-1.elb.amazonaws.com.cn/announcement
         * messageServiceUrl : http://123.127.220.187:8088/messageservice
         * getuiServiceUrl : http://123.127.220.187:8088/messagecenter
         * gpsServiceUrl :
         */

        private String backendservice;
        private String casservice;
        private String description;
        private String domainname;
        private String extendattribute;
        private int ispublic;
        private String productcode;
        private String projectid;
        private String projectname;
        private String projserviceguid;
        private int status;
        private String tenantid;
        private String noticeServiceUrl;
        private String messageServiceUrl;
        private String getuiServiceUrl;
        private String gpsServiceUrl;

        public String getBackendservice() {
            return backendservice;
        }

        public void setBackendservice(String backendservice) {
            this.backendservice = backendservice;
        }

        public String getCasservice() {
            return casservice;
        }

        public void setCasservice(String casservice) {
            this.casservice = casservice;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDomainname() {
            return domainname;
        }

        public void setDomainname(String domainname) {
            this.domainname = domainname;
        }

        public String getExtendattribute() {
            return extendattribute;
        }

        public void setExtendattribute(String extendattribute) {
            this.extendattribute = extendattribute;
        }

        public int getIspublic() {
            return ispublic;
        }

        public void setIspublic(int ispublic) {
            this.ispublic = ispublic;
        }

        public String getProductcode() {
            return productcode;
        }

        public void setProductcode(String productcode) {
            this.productcode = productcode;
        }

        public String getProjectid() {
            return projectid;
        }

        public void setProjectid(String projectid) {
            this.projectid = projectid;
        }

        public String getProjectname() {
            return projectname;
        }

        public void setProjectname(String projectname) {
            this.projectname = projectname;
        }

        public String getProjserviceguid() {
            return projserviceguid;
        }

        public void setProjserviceguid(String projserviceguid) {
            this.projserviceguid = projserviceguid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTenantid() {
            return tenantid;
        }

        public void setTenantid(String tenantid) {
            this.tenantid = tenantid;
        }

        public String getNoticeServiceUrl() {
            return noticeServiceUrl;
        }

        public void setNoticeServiceUrl(String noticeServiceUrl) {
            this.noticeServiceUrl = noticeServiceUrl;
        }

        public String getMessageServiceUrl() {
            return messageServiceUrl;
        }

        public void setMessageServiceUrl(String messageServiceUrl) {
            this.messageServiceUrl = messageServiceUrl;
        }

        public String getGetuiServiceUrl() {
            return getuiServiceUrl;
        }

        public void setGetuiServiceUrl(String getuiServiceUrl) {
            this.getuiServiceUrl = getuiServiceUrl;
        }

        public String getGpsServiceUrl() {
            return gpsServiceUrl;
        }

        public void setGpsServiceUrl(String gpsServiceUrl) {
            this.gpsServiceUrl = gpsServiceUrl;
        }
    }
}
