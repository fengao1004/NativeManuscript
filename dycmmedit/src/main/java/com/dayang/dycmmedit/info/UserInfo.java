package com.dayang.dycmmedit.info;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/2.
 * e-mail 897840134@qq.com
 */

public class UserInfo {

    /**
     * data : {"columnAllListModel":[],"columnListModel":[{"description":"法制栏目","id":"00001","isValid":"1","name":"法制栏目","onlineLifeCycle":"1111","otherInfo":""},{"description":"","id":"003","isValid":"1","name":"003","onlineLifeCycle":"15","otherInfo":""}],"creDescription":"","creStatus":"0","loginModel":null,"manuscriptVersion":"V1.0.7_Build20170425","privilegeLoadModel":[{"description":"创建新稿件或将已有稿件复制为新稿件，并且可以修改、删除、提交本人创建的稿件。只能修改或删除本人未提交或被打回的稿件。该权限通常被赋予记者。\r\n","groupId":"PID_GROUP_CMEDIT","id":"PID_CMEDIT_WRITESCRIPTS","name":"撰写全媒体稿件"},{"description":"对稿件进行审核，可通过或打回。如果该用户属于多个栏目，则可以审核多个栏目的稿件。该权限通常被赋予栏目领导、责编等审核人，如果审核人同时也具有修改全部稿件的权限，则审核稿件的同时也可直接进行修改。","groupId":"PID_GROUP_CMEDIT","id":"PID_CMEDIT_AUDITSCRIPTS","name":"审核全媒体稿件"}],"userLoadModel":[{"description":" ","iconUrl":"http://100.0.10.203/creicon/user/0003.jpg","id":"0003","lastmodifier":"","mail":"","maxCapacity":0,"name":"0003-禁止删除用户用于系统间联调","organizationId":"836FE30E-85C7-465E-B13A-4EA348957CBA,612CB884-9DB6-459B-9EE2-6EC7BE5F3F06","password":"0003","personalSpaceFlag":0,"status":"0","tel":"","type":0,"workNo":"0003"}]}
     * msg :
     * status : true
     */

    private DataEntity data;
    private String msg;
    private boolean status;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
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

    public static class DataEntity {
        /**
         * columnAllListModel : []
         * columnListModel : [{"description":"法制栏目","id":"00001","isValid":"1","name":"法制栏目","onlineLifeCycle":"1111","otherInfo":""},{"description":"","id":"003","isValid":"1","name":"003","onlineLifeCycle":"15","otherInfo":""}]
         * creDescription :
         * creStatus : 0
         * loginModel : null
         * manuscriptVersion : V1.0.7_Build20170425
         * privilegeLoadModel : [{"description":"创建新稿件或将已有稿件复制为新稿件，并且可以修改、删除、提交本人创建的稿件。只能修改或删除本人未提交或被打回的稿件。该权限通常被赋予记者。\r\n","groupId":"PID_GROUP_CMEDIT","id":"PID_CMEDIT_WRITESCRIPTS","name":"撰写全媒体稿件"},{"description":"对稿件进行审核，可通过或打回。如果该用户属于多个栏目，则可以审核多个栏目的稿件。该权限通常被赋予栏目领导、责编等审核人，如果审核人同时也具有修改全部稿件的权限，则审核稿件的同时也可直接进行修改。","groupId":"PID_GROUP_CMEDIT","id":"PID_CMEDIT_AUDITSCRIPTS","name":"审核全媒体稿件"}]
         * userLoadModel : [{"description":" ","iconUrl":"http://100.0.10.203/creicon/user/0003.jpg","id":"0003","lastmodifier":"","mail":"","maxCapacity":0,"name":"0003-禁止删除用户用于系统间联调","organizationId":"836FE30E-85C7-465E-B13A-4EA348957CBA,612CB884-9DB6-459B-9EE2-6EC7BE5F3F06","password":"0003","personalSpaceFlag":0,"status":"0","tel":"","type":0,"workNo":"0003"}]
         */

        private String creDescription;
        private String creStatus;
        private Object loginModel;
        private String manuscriptVersion;
        private List<?> columnAllListModel;
        private List<ColumnListModelEntity> columnListModel;
        private List<PrivilegeLoadModelEntity> privilegeLoadModel;
        private List<UserLoadModelEntity> userLoadModel;

        public String getCreDescription() {
            return creDescription;
        }

        public void setCreDescription(String creDescription) {
            this.creDescription = creDescription;
        }

        public String getCreStatus() {
            return creStatus;
        }

        public void setCreStatus(String creStatus) {
            this.creStatus = creStatus;
        }

        public Object getLoginModel() {
            return loginModel;
        }

        public void setLoginModel(Object loginModel) {
            this.loginModel = loginModel;
        }

        public String getManuscriptVersion() {
            return manuscriptVersion;
        }

        public void setManuscriptVersion(String manuscriptVersion) {
            this.manuscriptVersion = manuscriptVersion;
        }

        public List<?> getColumnAllListModel() {
            return columnAllListModel;
        }

        public void setColumnAllListModel(List<?> columnAllListModel) {
            this.columnAllListModel = columnAllListModel;
        }

        public List<ColumnListModelEntity> getColumnListModel() {
            return columnListModel;
        }

        public void setColumnListModel(List<ColumnListModelEntity> columnListModel) {
            this.columnListModel = columnListModel;
        }

        public List<PrivilegeLoadModelEntity> getPrivilegeLoadModel() {
            return privilegeLoadModel;
        }

        public void setPrivilegeLoadModel(List<PrivilegeLoadModelEntity> privilegeLoadModel) {
            this.privilegeLoadModel = privilegeLoadModel;
        }

        public List<UserLoadModelEntity> getUserLoadModel() {
            return userLoadModel;
        }

        public void setUserLoadModel(List<UserLoadModelEntity> userLoadModel) {
            this.userLoadModel = userLoadModel;
        }

        public static class ColumnListModelEntity {
            /**
             * description : 法制栏目
             * id : 00001
             * isValid : 1
             * name : 法制栏目
             * onlineLifeCycle : 1111
             * otherInfo :
             */

            private String description;
            private String id;
            private String isValid;
            private String name;
            private String onlineLifeCycle;
            private String otherInfo;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIsValid() {
                return isValid;
            }

            public void setIsValid(String isValid) {
                this.isValid = isValid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOnlineLifeCycle() {
                return onlineLifeCycle;
            }

            public void setOnlineLifeCycle(String onlineLifeCycle) {
                this.onlineLifeCycle = onlineLifeCycle;
            }

            public String getOtherInfo() {
                return otherInfo;
            }

            public void setOtherInfo(String otherInfo) {
                this.otherInfo = otherInfo;
            }
        }

        public static class PrivilegeLoadModelEntity {
            /**
             * description : 创建新稿件或将已有稿件复制为新稿件，并且可以修改、删除、提交本人创建的稿件。只能修改或删除本人未提交或被打回的稿件。该权限通常被赋予记者。

             * groupId : PID_GROUP_CMEDIT
             * id : PID_CMEDIT_WRITESCRIPTS
             * name : 撰写全媒体稿件
             */

            private String description;
            private String groupId;
            private String id;
            private String name;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

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
        }

        public static class UserLoadModelEntity {
            /**
             * description :
             * iconUrl : http://100.0.10.203/creicon/user/0003.jpg
             * id : 0003
             * lastmodifier :
             * mail :
             * maxCapacity : 0
             * name : 0003-禁止删除用户用于系统间联调
             * organizationId : 836FE30E-85C7-465E-B13A-4EA348957CBA,612CB884-9DB6-459B-9EE2-6EC7BE5F3F06
             * password : 0003
             * personalSpaceFlag : 0
             * status : 0
             * tel :
             * type : 0
             * workNo : 0003
             */

            private String description;
            private String iconUrl;
            private String id;
            private String lastmodifier;
            private String mail;
            private int maxCapacity;
            private String name;
            private String organizationId;
            private String password;
            private int personalSpaceFlag;
            private String status;
            private String tel;
            private int type;
            private String workNo;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLastmodifier() {
                return lastmodifier;
            }

            public void setLastmodifier(String lastmodifier) {
                this.lastmodifier = lastmodifier;
            }

            public String getMail() {
                return mail;
            }

            public void setMail(String mail) {
                this.mail = mail;
            }

            public int getMaxCapacity() {
                return maxCapacity;
            }

            public void setMaxCapacity(int maxCapacity) {
                this.maxCapacity = maxCapacity;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOrganizationId() {
                return organizationId;
            }

            public void setOrganizationId(String organizationId) {
                this.organizationId = organizationId;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public int getPersonalSpaceFlag() {
                return personalSpaceFlag;
            }

            public void setPersonalSpaceFlag(int personalSpaceFlag) {
                this.personalSpaceFlag = personalSpaceFlag;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getWorkNo() {
                return workNo;
            }

            public void setWorkNo(String workNo) {
                this.workNo = workNo;
            }
        }
    }
}
