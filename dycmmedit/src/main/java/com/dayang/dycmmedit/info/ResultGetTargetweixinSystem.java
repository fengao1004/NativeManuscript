package com.dayang.dycmmedit.info;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/18.
 * e-mail 897840134@qq.com
 */

public class ResultGetTargetweixinSystem {


    /**
     * data : [{"appid":"wxe786952418ad2750","appsecret":"d4624c36b6795d1d99dcf0547af5443d","hint":"fxk测试号","targetweixinid":"4ada5808-b926-45b3-914a-b478c5e95d77","targetweixinname":"fxk测试号"},{"appid":"wx4c4cf8dde2b7b521","appsecret":"d4624c36b6795d1d99dcf0547af5443d","hint":"中科大洋融合生产平台测试号","targetweixinid":"660a9100-b035-4693-9a6d-cb3a5509a694","targetweixinname":"中科大洋融合生产平台测试号"}]
     * msg :  获取微信目标系统信息成功！
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
         * appid : wxe786952418ad2750
         * appsecret : d4624c36b6795d1d99dcf0547af5443d
         * hint : fxk测试号
         * targetweixinid : 4ada5808-b926-45b3-914a-b478c5e95d77
         * targetweixinname : fxk测试号
         */

        private String appid;
        private String appsecret;
        private String hint;
        private String targetweixinid;
        private String targetweixinname;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getAppsecret() {
            return appsecret;
        }

        public void setAppsecret(String appsecret) {
            this.appsecret = appsecret;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getTargetweixinid() {
            return targetweixinid;
        }

        public void setTargetweixinid(String targetweixinid) {
            this.targetweixinid = targetweixinid;
        }

        public String getTargetweixinname() {
            return targetweixinname;
        }

        public void setTargetweixinname(String targetweixinname) {
            this.targetweixinname = targetweixinname;
        }
    }
}
