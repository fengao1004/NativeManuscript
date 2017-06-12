package com.dayang.dycmmedit.info;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/18.
 * e-mail 897840134@qq.com
 */

public class ResultGetWebTargetSystem {


    /**
     * data : [{"hint":"","serviceaddress":"","systemid":"","systemname":"","targetsystemcode":"","targetsystemid":"c7695dc9-7d3f-4510-b81a-dec5afbfe3ed_CRE","targetsystemname":"sdfsfs"},{"hint":"","serviceaddress":"","systemid":"","systemname":"","targetsystemcode":"","targetsystemid":"f841ed22-6c6b-4be0-a7f7-f592f9b1dc66_CRE","targetsystemname":"CMS测试"}]
     * msg : 获取网页目标系统信息成功！
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
         * hint :
         * serviceaddress :
         * systemid :
         * systemname :
         * targetsystemcode :
         * targetsystemid : c7695dc9-7d3f-4510-b81a-dec5afbfe3ed_CRE
         * targetsystemname : sdfsfs
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
