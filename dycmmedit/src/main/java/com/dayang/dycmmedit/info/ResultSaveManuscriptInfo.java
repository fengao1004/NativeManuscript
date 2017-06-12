package com.dayang.dycmmedit.info;

/**
 * Created by 冯傲 on 2017/5/9.
 * e-mail 897840134@qq.com
 */

public class ResultSaveManuscriptInfo {

    /**
     * data : {"arrayindex":0,"camerist":"","columnid":"","columnname":"","createtime":"2017-04-26 11:53:43","editor":"","estimatetime":"","fatherid":"F90582D3-6447-47D0-9DCA-DE8D7BD07EA5","fathersonmark":0,"folderid":"","foldername":"","h5content":"","header":"新建稿件_20170426115343810","hjcolumn_id":"","hjcolumn_name":"","iscomment":0,"isdeleted":1,"keywords":"","lastmodifier":"0003","lastmodifytime":"2017-04-26 11:53:43","manuscriptid":"F90582D3-6447-47D0-9DCA-DE8D7BD07EA5","manuscripttype":3,"mnum":0,"releasetype":0,"reporter":"","resourcesid":"","sources":"","sourceurl":"","status":0,"subtitle":"","summary":"","systemid":"","textcontent":"","usercode":"0003","username":"0003-禁止删除用户用于系统间联调","weixinlowimage":""}
     * msg : 新增稿件成功！
     * status : true
     */

    private ManuscriptListInfo data;
    private String msg;
    private boolean status;

    public ManuscriptListInfo getData() {
        return data;
    }

    public void setData(ManuscriptListInfo data) {
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
