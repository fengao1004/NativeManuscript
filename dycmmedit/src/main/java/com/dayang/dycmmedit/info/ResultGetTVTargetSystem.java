package com.dayang.dycmmedit.info;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/18.
 * e-mail 897840134@qq.com
 */

public class ResultGetTVTargetSystem {

    /**
     * data : [{"hint":"发送到文稿接口地址","serviceaddress":"http://192.168.10.126:8080/DYNewsService/services/PPNENewsMaterialImportService?wsdl","systemid":"7b15554d-0259-4602-9c62-22d04461c3b6","systemname":"","targetsystemcode":"DISTRIBUTIONURL","targetsystemid":"3028805c-072b-41d3-87bf-12db3f38617d","targetsystemname":"生成文稿"},{"hint":"发送到文稿（选题）接口地址","serviceaddress":"http://192.168.20.167:1234/PPNEMaterialImportService?wsdl","systemid":"7b15554d-0259-4602-9c62-22d04461c3b6","systemname":"","targetsystemcode":"NEWSTASK","targetsystemid":"0c2e2345-9279-41fd-999e-b67747e2c8be","targetsystemname":"生成选题"}]
     * msg : 获取电视目标系统信息成功！
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
         * hint : 发送到文稿接口地址
         * serviceaddress : http://192.168.10.126:8080/DYNewsService/services/PPNENewsMaterialImportService?wsdl
         * systemid : 7b15554d-0259-4602-9c62-22d04461c3b6
         * systemname :
         * targetsystemcode : DISTRIBUTIONURL
         * targetsystemid : 3028805c-072b-41d3-87bf-12db3f38617d
         * targetsystemname : 生成文稿
         */

        private String hint;
        private String serviceaddress;
        private String systemid;
        private String systemname;
        private String targetsystemcode;
        private String targetsystemid;
        private String targetsystemname;

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getServiceaddress() {
            return serviceaddress;
        }

        public void setServiceaddress(String serviceaddress) {
            this.serviceaddress = serviceaddress;
        }

        public String getSystemid() {
            return systemid;
        }

        public void setSystemid(String systemid) {
            this.systemid = systemid;
        }

        public String getSystemname() {
            return systemname;
        }

        public void setSystemname(String systemname) {
            this.systemname = systemname;
        }

        public String getTargetsystemcode() {
            return targetsystemcode;
        }

        public void setTargetsystemcode(String targetsystemcode) {
            this.targetsystemcode = targetsystemcode;
        }

        public String getTargetsystemid() {
            return targetsystemid;
        }

        public void setTargetsystemid(String targetsystemid) {
            this.targetsystemid = targetsystemid;
        }

        public String getTargetsystemname() {
            return targetsystemname;
        }

        public void setTargetsystemname(String targetsystemname) {
            this.targetsystemname = targetsystemname;
        }
    }
}
