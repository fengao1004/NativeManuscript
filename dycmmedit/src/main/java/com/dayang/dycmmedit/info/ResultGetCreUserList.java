package com.dayang.dycmmedit.info;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/18.
 * e-mail 897840134@qq.com
 */

public class ResultGetCreUserList {

    /**
     * data : [{"description":"btfrge","iconUrl":"http://100.0.10.201:8080/fe/images/user_sys.jpg","id":"458F5756-3439-4FE2-8C72-6080F80D1BCA","lastmodifier":"","mail":"","maxCapacity":0,"name":"btfrge","organizationId":"0BBCB174-64F8-4592-870D-6C8F73A03961","password":"btfrge","personalSpaceFlag":0,"status":"0","tel":"","type":0,"workNo":"btfrge"},null]
     * msg :
     * status : true
     */

    private String msg;
    private boolean status;
    private List<UserModel> data;

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

    public List<UserModel> getData() {
        return data;
    }

    public void setData(List<UserModel> data) {
        this.data = data;
    }


}
