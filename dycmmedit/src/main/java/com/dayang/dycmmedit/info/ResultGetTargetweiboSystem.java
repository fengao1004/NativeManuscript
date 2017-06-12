package com.dayang.dycmmedit.info;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/18.
 * e-mail 897840134@qq.com
 */

public class ResultGetTargetweiboSystem {

    /**
     * data : [{"accesstoken":"2.00WOGL_GmXmR_Ccbae8b66911mMEFC","clientid":"2117254230","clientsercret":"e4ce427090cd38dc1743e6d5d42ddb82","hint":"应用ID和应用秘钥不需要","targetweiboid":"1df0d0c6-0bcd-4021-bee3-56332240122f","targetweiboname":"大洋信息官方微博"},{"accesstoken":"2.00WOGL_GmXmR_Ccbae8b66911mMEFC","clientid":"2117254230","clientsercret":"e4ce427090cd38dc1743e6d5d42ddb82","hint":"","targetweiboid":"6694161e-6d31-421e-950a-d30b1af0d04b","targetweiboname":"新媒体测试号"}]
     * msg : 获取微博系统信息成功！
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
         * accesstoken : 2.00WOGL_GmXmR_Ccbae8b66911mMEFC
         * clientid : 2117254230
         * clientsercret : e4ce427090cd38dc1743e6d5d42ddb82
         * hint : 应用ID和应用秘钥不需要
         * targetweiboid : 1df0d0c6-0bcd-4021-bee3-56332240122f
         * targetweiboname : 大洋信息官方微博
         */

        private String accesstoken;
        private String clientid;
        private String clientsercret;
        private String hint;
        private String targetweiboid;
        private String targetweiboname;

        public String getAccesstoken() {
            return accesstoken;
        }

        public void setAccesstoken(String accesstoken) {
            this.accesstoken = accesstoken;
        }

        public String getClientid() {
            return clientid;
        }

        public void setClientid(String clientid) {
            this.clientid = clientid;
        }

        public String getClientsercret() {
            return clientsercret;
        }

        public void setClientsercret(String clientsercret) {
            this.clientsercret = clientsercret;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getTargetweiboid() {
            return targetweiboid;
        }

        public void setTargetweiboid(String targetweiboid) {
            this.targetweiboid = targetweiboid;
        }

        public String getTargetweiboname() {
            return targetweiboname;
        }

        public void setTargetweiboname(String targetweiboname) {
            this.targetweiboname = targetweiboname;
        }
    }
}
