package com.dayang.dycmmedit.info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 冯傲 on 2017/5/18.
 * e-mail 897840134@qq.com
 */

public class UserListAndTargetSystem {
    public ArrayList<String> targetSystemNames;
    public ArrayList<String> targetSystemIds;
    private ArrayList<String> auditorNames;
    private List<UserModel> auditors;

    public ArrayList<String> getAuditorNames() {
        return auditorNames;
    }

    public List<UserModel> getAuditors() {
        return auditors;
    }

    public boolean showChooseAuditor;

    public UserListAndTargetSystem() {

    }

    public void setTargetSystemNames(List<UserModel> auditors) {
        this.auditorNames = new ArrayList<>();
        this.auditors = auditors;
        auditorNames.add("请指定审核人");
        for (UserModel user : auditors) {
            auditorNames.add(user.getName());
        }
    }
}
