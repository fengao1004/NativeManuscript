package com.dayang.dycmmedit.info;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/19.
 * e-mail 897840134@qq.com
 */

public class ResultCensorStrategyExist {

    /**
     * data : {"censorRecordList":[{"msnumid":"83e33591-2a52-11e7-b123-86375c9c3355","submitnum":1,"submittime":"2017-04-26 15:32:34","censorDetailOpition":[{"censordate":"","censoropinion":"","censorstate":1,"censortype":0,"levelname":"第1级","levelnumber":1,"manuscriptid":"5E7ABC52-2B1C-456A-8D4C-BD92D3CE8F36","msnumid":"83e33591-2a52-11e7-b123-86375c9c3355","userid":"0003","username":"0003-禁止删除用户用于系统间联调"}]}],"censorsTrategyTemp":{"censormode":1,"columnid":"00001","columnname":"法制栏目","createtime":"2017-04-24 17:40:45","cstrategycode":"007","cstrategyid":"b71f1d99-815d-11e6-b1fb-8b47563d1f8f","cstrategyname":"web_007","designatedauditor":0,"hint":"","isenable":0,"manuscripttype":0}}
     * msg : 返回true表示存在，false表示不存在！
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
         * censorRecordList : [{"msnumid":"83e33591-2a52-11e7-b123-86375c9c3355","submitnum":1,"submittime":"2017-04-26 15:32:34","censorDetailOpition":[{"censordate":"","censoropinion":"","censorstate":1,"censortype":0,"levelname":"第1级","levelnumber":1,"manuscriptid":"5E7ABC52-2B1C-456A-8D4C-BD92D3CE8F36","msnumid":"83e33591-2a52-11e7-b123-86375c9c3355","userid":"0003","username":"0003-禁止删除用户用于系统间联调"}]}]
         * censorsTrategyTemp : {"censormode":1,"columnid":"00001","columnname":"法制栏目","createtime":"2017-04-24 17:40:45","cstrategycode":"007","cstrategyid":"b71f1d99-815d-11e6-b1fb-8b47563d1f8f","cstrategyname":"web_007","designatedauditor":0,"hint":"","isenable":0,"manuscripttype":0}
         */

        private CensorsTrategyTempEntity censorsTrategyTemp;
        private List<CensorRecordListEntity> censorRecordList;

        public CensorsTrategyTempEntity getCensorsTrategyTemp() {
            return censorsTrategyTemp;
        }

        public void setCensorsTrategyTemp(CensorsTrategyTempEntity censorsTrategyTemp) {
            this.censorsTrategyTemp = censorsTrategyTemp;
        }

        public List<CensorRecordListEntity> getCensorRecordList() {
            return censorRecordList;
        }

        public void setCensorRecordList(List<CensorRecordListEntity> censorRecordList) {
            this.censorRecordList = censorRecordList;
        }

        public static class CensorsTrategyTempEntity {
            /**
             * censormode : 1
             * columnid : 00001
             * columnname : 法制栏目
             * createtime : 2017-04-24 17:40:45
             * cstrategycode : 007
             * cstrategyid : b71f1d99-815d-11e6-b1fb-8b47563d1f8f
             * cstrategyname : web_007
             * designatedauditor : 0
             * hint :
             * isenable : 0
             * manuscripttype : 0
             */

            private int censormode;
            private String columnid;
            private String columnname;
            private String createtime;
            private String cstrategycode;
            private String cstrategyid;
            private String cstrategyname;
            private int designatedauditor;
            private String hint;
            private int isenable;
            private int manuscripttype;

            public int getCensormode() {
                return censormode;
            }

            public void setCensormode(int censormode) {
                this.censormode = censormode;
            }

            public String getColumnid() {
                return columnid;
            }

            public void setColumnid(String columnid) {
                this.columnid = columnid;
            }

            public String getColumnname() {
                return columnname;
            }

            public void setColumnname(String columnname) {
                this.columnname = columnname;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getCstrategycode() {
                return cstrategycode;
            }

            public void setCstrategycode(String cstrategycode) {
                this.cstrategycode = cstrategycode;
            }

            public String getCstrategyid() {
                return cstrategyid;
            }

            public void setCstrategyid(String cstrategyid) {
                this.cstrategyid = cstrategyid;
            }

            public String getCstrategyname() {
                return cstrategyname;
            }

            public void setCstrategyname(String cstrategyname) {
                this.cstrategyname = cstrategyname;
            }

            public int getDesignatedauditor() {
                return designatedauditor;
            }

            public void setDesignatedauditor(int designatedauditor) {
                this.designatedauditor = designatedauditor;
            }

            public String getHint() {
                return hint;
            }

            public void setHint(String hint) {
                this.hint = hint;
            }

            public int getIsenable() {
                return isenable;
            }

            public void setIsenable(int isenable) {
                this.isenable = isenable;
            }

            public int getManuscripttype() {
                return manuscripttype;
            }

            public void setManuscripttype(int manuscripttype) {
                this.manuscripttype = manuscripttype;
            }
        }

        public static class CensorRecordListEntity {
            /**
             * msnumid : 83e33591-2a52-11e7-b123-86375c9c3355
             * submitnum : 1
             * submittime : 2017-04-26 15:32:34
             * censorDetailOpition : [{"censordate":"","censoropinion":"","censorstate":1,"censortype":0,"levelname":"第1级","levelnumber":1,"manuscriptid":"5E7ABC52-2B1C-456A-8D4C-BD92D3CE8F36","msnumid":"83e33591-2a52-11e7-b123-86375c9c3355","userid":"0003","username":"0003-禁止删除用户用于系统间联调"}]
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
                 * manuscriptid : 5E7ABC52-2B1C-456A-8D4C-BD92D3CE8F36
                 * msnumid : 83e33591-2a52-11e7-b123-86375c9c3355
                 * userid : 0003
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
                private String userid;
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

                public String getUserid() {
                    return userid;
                }

                public void setUserid(String userid) {
                    this.userid = userid;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }
            }
        }
    }
}
