package com.dayang.dycmmedit.info;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/18.
 * e-mail 897840134@qq.com
 */

public class FolderInfo {
    /**
     * children : []
     * groupId :
     * id : 483900b4-b25e-4856-976b-1a2ca1a9527e
     * name : 随心拍20170424120849_新闻直播间
     */

    private String groupId="";
    private String id="";
    private String name="";
    private List<?> children;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }
}
