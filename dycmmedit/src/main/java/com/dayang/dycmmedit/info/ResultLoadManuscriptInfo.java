package com.dayang.dycmmedit.info;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/13.
 * e-mail 897840134@qq.com
 */

public class ResultLoadManuscriptInfo {

    /**
     * data : {"manuscriptList":[{"arrayindex":0,"camerist":"0","columnid":"007","columnname":"007","createtime":"2017-04-25 10:28:54","editor":"","estimatetime":"","fatherid":"A7304649-37B5-4DC5-A4BF-BC7BA6173052","fathersonmark":0,"folderid":"","foldername":"","h5content":"<p>xhhxhx<\/p>","header":"新建稿件_20170425102854512","hjcolumn_id":"","hjcolumn_name":"","iscomment":0,"isdeleted":1,"keywords":"","lastmodifier":"0003","lastmodifytime":"2017-04-25 10:29:21","manuscriptid":"A7304649-37B5-4DC5-A4BF-BC7BA6173052","manuscripttype":1,"mnum":0,"releasetype":0,"reporter":"","resourcesid":"","sources":"","sourceurl":"","status":1,"subtitle":"","summary":"","systemid":"","textcontent":"xhhxhx","usercode":"0003","username":"0003-禁止删除用户用于系统间联调","weixinlowimage":"http://100.0.1.67:8080/images/phone/A7304649-37B5-4DC5-A4BF-BC7BA6173052_weixinLowImage/9252150_174125118561_2.jpg"}],"manuscript":{"arrayindex":0,"camerist":"0","columnid":"007","columnname":"007","createtime":"2017-04-25 10:28:54","editor":"","estimatetime":"","fatherid":"A7304649-37B5-4DC5-A4BF-BC7BA6173052","fathersonmark":0,"folderid":"","foldername":"","h5content":"<p>xhhxhx<\/p>","header":"新建稿件_20170425102854512","hjcolumn_id":"","hjcolumn_name":"","iscomment":0,"isdeleted":1,"keywords":"","lastmodifier":"0003","lastmodifytime":"2017-04-25 10:29:21","manuscriptid":"A7304649-37B5-4DC5-A4BF-BC7BA6173052","manuscripttype":1,"mnum":0,"releasetype":0,"reporter":"","resourcesid":"","sources":"","sourceurl":"","status":1,"subtitle":"","summary":"","systemid":"","textcontent":"xhhxhx","usercode":"0003","username":"0003-禁止删除用户用于系统间联调","weixinlowimage":"phone/A7304649-37B5-4DC5-A4BF-BC7BA6173052_weixinLowImage/9252150_174125118561_2.jpg"},"censorRecordList":[{"msnumid":"fd585a2d-295e-11e7-b61f-d3e89be0be57","submitnum":1,"submittime":"2017-04-25 10:29:21","censorDetailOpition":[{"censordate":"","censoropinion":"","censorstate":1,"censortype":0,"levelname":"第1级","levelnumber":1,"manuscriptid":"A7304649-37B5-4DC5-A4BF-BC7BA6173052","msnumid":"fd585a2d-295e-11e7-b61f-d3e89be0be57","username":"0003-禁止删除用户用于系统间联调"}]}]}
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
         * manuscriptList : [{"arrayindex":0,"camerist":"0","columnid":"007","columnname":"007","createtime":"2017-04-25 10:28:54","editor":"","estimatetime":"","fatherid":"A7304649-37B5-4DC5-A4BF-BC7BA6173052","fathersonmark":0,"folderid":"","foldername":"","h5content":"<p>xhhxhx<\/p>","header":"新建稿件_20170425102854512","hjcolumn_id":"","hjcolumn_name":"","iscomment":0,"isdeleted":1,"keywords":"","lastmodifier":"0003","lastmodifytime":"2017-04-25 10:29:21","manuscriptid":"A7304649-37B5-4DC5-A4BF-BC7BA6173052","manuscripttype":1,"mnum":0,"releasetype":0,"reporter":"","resourcesid":"","sources":"","sourceurl":"","status":1,"subtitle":"","summary":"","systemid":"","textcontent":"xhhxhx","usercode":"0003","username":"0003-禁止删除用户用于系统间联调","weixinlowimage":"http://100.0.1.67:8080/images/phone/A7304649-37B5-4DC5-A4BF-BC7BA6173052_weixinLowImage/9252150_174125118561_2.jpg"}]
         * manuscript : {"arrayindex":0,"camerist":"0","columnid":"007","columnname":"007","createtime":"2017-04-25 10:28:54","editor":"","estimatetime":"","fatherid":"A7304649-37B5-4DC5-A4BF-BC7BA6173052","fathersonmark":0,"folderid":"","foldername":"","h5content":"<p>xhhxhx<\/p>","header":"新建稿件_20170425102854512","hjcolumn_id":"","hjcolumn_name":"","iscomment":0,"isdeleted":1,"keywords":"","lastmodifier":"0003","lastmodifytime":"2017-04-25 10:29:21","manuscriptid":"A7304649-37B5-4DC5-A4BF-BC7BA6173052","manuscripttype":1,"mnum":0,"releasetype":0,"reporter":"","resourcesid":"","sources":"","sourceurl":"","status":1,"subtitle":"","summary":"","systemid":"","textcontent":"xhhxhx","usercode":"0003","username":"0003-禁止删除用户用于系统间联调","weixinlowimage":"phone/A7304649-37B5-4DC5-A4BF-BC7BA6173052_weixinLowImage/9252150_174125118561_2.jpg"}
         * censorRecordList : [{"msnumid":"fd585a2d-295e-11e7-b61f-d3e89be0be57","submitnum":1,"submittime":"2017-04-25 10:29:21","censorDetailOpition":[{"censordate":"","censoropinion":"","censorstate":1,"censortype":0,"levelname":"第1级","levelnumber":1,"manuscriptid":"A7304649-37B5-4DC5-A4BF-BC7BA6173052","msnumid":"fd585a2d-295e-11e7-b61f-d3e89be0be57","username":"0003-禁止删除用户用于系统间联调"}]}]
         */

        private ManuscriptListInfo manuscript;
        private List<ManuscriptListInfo> manuscriptList;
        private List<CensorRecordInfo> censorRecordList;

        public ManuscriptListInfo getManuscript() {
            return manuscript;
        }

        public void setManuscript(ManuscriptListInfo manuscript) {
            this.manuscript = manuscript;
        }

        public List<ManuscriptListInfo> getManuscriptList() {
            return manuscriptList;
        }

        public void setManuscriptList(List<ManuscriptListInfo> manuscriptList) {
            this.manuscriptList = manuscriptList;
        }

        public List<CensorRecordInfo> getCensorRecordList() {
            return censorRecordList;
        }

        public void setCensorRecordList(List<CensorRecordInfo> censorRecordList) {
            this.censorRecordList = censorRecordList;
        }

        public static class ManuscriptEntity {
        }

        public static class ManuscriptListEntity {
            /**
             * arrayindex : 0
             * camerist : 0
             * columnid : 007
             * columnname : 007
             * createtime : 2017-04-25 10:28:54
             * editor :
             * estimatetime :
             * fatherid : A7304649-37B5-4DC5-A4BF-BC7BA6173052
             * fathersonmark : 0
             * folderid :
             * foldername :
             * h5content : <p>xhhxhx</p>
             * header : 新建稿件_20170425102854512
             * hjcolumn_id :
             * hjcolumn_name :
             * iscomment : 0
             * isdeleted : 1
             * keywords :
             * lastmodifier : 0003
             * lastmodifytime : 2017-04-25 10:29:21
             * manuscriptid : A7304649-37B5-4DC5-A4BF-BC7BA6173052
             * manuscripttype : 1
             * mnum : 0
             * releasetype : 0
             * reporter :
             * resourcesid :
             * sources :
             * sourceurl :
             * status : 1
             * subtitle :
             * summary :
             * systemid :
             * textcontent : xhhxhx
             * usercode : 0003
             * username : 0003-禁止删除用户用于系统间联调
             * weixinlowimage : http://100.0.1.67:8080/images/phone/A7304649-37B5-4DC5-A4BF-BC7BA6173052_weixinLowImage/9252150_174125118561_2.jpg
             */

            private int arrayindex;
            private String camerist;
            private String columnid;
            private String columnname;
            private String createtime;
            private String editor;
            private String estimatetime;
            private String fatherid;
            private int fathersonmark;
            private String folderid;
            private String foldername;
            private String h5content;
            private String header;
            private String hjcolumn_id;
            private String hjcolumn_name;
            private int iscomment;
            private int isdeleted;
            private String keywords;
            private String lastmodifier;
            private String lastmodifytime;
            private String manuscriptid;
            private int manuscripttype;
            private int mnum;
            private int releasetype;
            private String reporter;
            private String resourcesid;
            private String sources;
            private String sourceurl;
            private int status;
            private String subtitle;
            private String summary;
            private String systemid;
            private String textcontent;
            private String usercode;
            private String username;
            private String weixinlowimage;

            public int getArrayindex() {
                return arrayindex;
            }

            public void setArrayindex(int arrayindex) {
                this.arrayindex = arrayindex;
            }

            public String getCamerist() {
                return camerist;
            }

            public void setCamerist(String camerist) {
                this.camerist = camerist;
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

            public String getEditor() {
                return editor;
            }

            public void setEditor(String editor) {
                this.editor = editor;
            }

            public String getEstimatetime() {
                return estimatetime;
            }

            public void setEstimatetime(String estimatetime) {
                this.estimatetime = estimatetime;
            }

            public String getFatherid() {
                return fatherid;
            }

            public void setFatherid(String fatherid) {
                this.fatherid = fatherid;
            }

            public int getFathersonmark() {
                return fathersonmark;
            }

            public void setFathersonmark(int fathersonmark) {
                this.fathersonmark = fathersonmark;
            }

            public String getFolderid() {
                return folderid;
            }

            public void setFolderid(String folderid) {
                this.folderid = folderid;
            }

            public String getFoldername() {
                return foldername;
            }

            public void setFoldername(String foldername) {
                this.foldername = foldername;
            }

            public String getH5content() {
                return h5content;
            }

            public void setH5content(String h5content) {
                this.h5content = h5content;
            }

            public String getHeader() {
                return header;
            }

            public void setHeader(String header) {
                this.header = header;
            }

            public String getHjcolumn_id() {
                return hjcolumn_id;
            }

            public void setHjcolumn_id(String hjcolumn_id) {
                this.hjcolumn_id = hjcolumn_id;
            }

            public String getHjcolumn_name() {
                return hjcolumn_name;
            }

            public void setHjcolumn_name(String hjcolumn_name) {
                this.hjcolumn_name = hjcolumn_name;
            }

            public int getIscomment() {
                return iscomment;
            }

            public void setIscomment(int iscomment) {
                this.iscomment = iscomment;
            }

            public int getIsdeleted() {
                return isdeleted;
            }

            public void setIsdeleted(int isdeleted) {
                this.isdeleted = isdeleted;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }

            public String getLastmodifier() {
                return lastmodifier;
            }

            public void setLastmodifier(String lastmodifier) {
                this.lastmodifier = lastmodifier;
            }

            public String getLastmodifytime() {
                return lastmodifytime;
            }

            public void setLastmodifytime(String lastmodifytime) {
                this.lastmodifytime = lastmodifytime;
            }

            public String getManuscriptid() {
                return manuscriptid;
            }

            public void setManuscriptid(String manuscriptid) {
                this.manuscriptid = manuscriptid;
            }

            public int getManuscripttype() {
                return manuscripttype;
            }

            public void setManuscripttype(int manuscripttype) {
                this.manuscripttype = manuscripttype;
            }

            public int getMnum() {
                return mnum;
            }

            public void setMnum(int mnum) {
                this.mnum = mnum;
            }

            public int getReleasetype() {
                return releasetype;
            }

            public void setReleasetype(int releasetype) {
                this.releasetype = releasetype;
            }

            public String getReporter() {
                return reporter;
            }

            public void setReporter(String reporter) {
                this.reporter = reporter;
            }

            public String getResourcesid() {
                return resourcesid;
            }

            public void setResourcesid(String resourcesid) {
                this.resourcesid = resourcesid;
            }

            public String getSources() {
                return sources;
            }

            public void setSources(String sources) {
                this.sources = sources;
            }

            public String getSourceurl() {
                return sourceurl;
            }

            public void setSourceurl(String sourceurl) {
                this.sourceurl = sourceurl;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getSystemid() {
                return systemid;
            }

            public void setSystemid(String systemid) {
                this.systemid = systemid;
            }

            public String getTextcontent() {
                return textcontent;
            }

            public void setTextcontent(String textcontent) {
                this.textcontent = textcontent;
            }

            public String getUsercode() {
                return usercode;
            }

            public void setUsercode(String usercode) {
                this.usercode = usercode;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getWeixinlowimage() {
                return weixinlowimage;
            }

            public void setWeixinlowimage(String weixinlowimage) {
                this.weixinlowimage = weixinlowimage;
            }
        }
    }
}
