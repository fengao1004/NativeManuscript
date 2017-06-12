package com.dayang.dycmmedit.info;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/24.
 * e-mail 897840134@qq.com
 */

public class CensorRecordInfo {
    /**
     * msnumid : fd585a2d-295e-11e7-b61f-d3e89be0be57
     * submitnum : 1
     * submittime : 2017-04-25 10:29:21
     * censorDetailOpition : [{"censordate":"","censoropinion":"","censorstate":1,"censortype":0,"levelname":"第1级","levelnumber":1,"manuscriptid":"A7304649-37B5-4DC5-A4BF-BC7BA6173052","msnumid":"fd585a2d-295e-11e7-b61f-d3e89be0be57","username":"0003-禁止删除用户用于系统间联调"}]
     */

    private String msnumid;
    private int submitnum;
    private String submittime;
    private List<CensorDetailOpitionEntity> censorDetailOpition;

    public String getMsnumid() {
        return msnumid;
    }

    public void setMsnumid(String msnumid) {
        this.msnumid = msnumid;
    }

    public int getSubmitnum() {
        return submitnum;
    }

    public void setSubmitnum(int submitnum) {
        this.submitnum = submitnum;
    }

    public String getSubmittime() {
        return submittime;
    }

    public void setSubmittime(String submittime) {
        this.submittime = submittime;
    }

    public List<CensorDetailOpitionEntity> getCensorDetailOpition() {
        return censorDetailOpition;
    }

    public void setCensorDetailOpition(List<CensorDetailOpitionEntity> censorDetailOpition) {
        this.censorDetailOpition = censorDetailOpition;
    }

    public static class CensorDetailOpitionEntity {
        /**
         * censordate :
         * censoropinion :
         * censorstate : 1
         * censortype : 0
         * levelname : 第1级
         * levelnumber : 1
         * manuscriptid : A7304649-37B5-4DC5-A4BF-BC7BA6173052
         * msnumid : fd585a2d-295e-11e7-b61f-d3e89be0be57
         * username : 0003-禁止删除用户用于系统间联调
         */

        private String censordate;
        private String censoropinion;
        private int censorstate;
        private int censortype;
        private String levelname;
        private int levelnumber;
        private String manuscriptid;
        private String msnumid;
        private String username;

        public String getCensordate() {
            return censordate;
        }

        public void setCensordate(String censordate) {
            this.censordate = censordate;
        }

        public String getCensoropinion() {
            return censoropinion;
        }

        public void setCensoropinion(String censoropinion) {
            this.censoropinion = censoropinion;
        }

        public int getCensorstate() {
            return censorstate;
        }

        public void setCensorstate(int censorstate) {
            this.censorstate = censorstate;
        }

        public int getCensortype() {
            return censortype;
        }

        public void setCensortype(int censortype) {
            this.censortype = censortype;
        }

        public String getLevelname() {
            return levelname;
        }

        public void setLevelname(String levelname) {
            this.levelname = levelname;
        }

        public int getLevelnumber() {
            return levelnumber;
        }

        public void setLevelnumber(int levelnumber) {
            this.levelnumber = levelnumber;
        }

        public String getManuscriptid() {
            return manuscriptid;
        }

        public void setManuscriptid(String manuscriptid) {
            this.manuscriptid = manuscriptid;
        }

        public String getMsnumid() {
            return msnumid;
        }

        public void setMsnumid(String msnumid) {
            this.msnumid = msnumid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
