package com.dayang.dycmmedit.info;

/**
 * Created by 冯傲 on 2017/5/2.
 * e-mail 897840134@qq.com
 */

public class RequestCheckDogInfo {

    /**
     * data : true
     * msg :
     * status : true
     */

    private boolean data;
    private String msg;
    private boolean status;

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

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
}
