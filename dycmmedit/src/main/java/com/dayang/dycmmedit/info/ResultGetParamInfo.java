package com.dayang.dycmmedit.info;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/11.
 * e-mail 897840134@qq.com
 */

public class ResultGetParamInfo {

    /**
     * data : [{"mobileupload":"","servicecode":"FTPUPLOADURL","streampath":"ftp://dy:123456@100.0.1.67:21"},null]
     * msg :
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
         * mobileupload :
         * servicecode : FTPUPLOADURL
         * streampath : ftp://dy:123456@100.0.1.67:21
         */

        private String mobileupload;
        private String servicecode;
        private String streampath;

        public String getMobileupload() {
            return mobileupload;
        }

        public void setMobileupload(String mobileupload) {
            this.mobileupload = mobileupload;
        }

        public String getServicecode() {
            return servicecode;
        }

        public void setServicecode(String servicecode) {
            this.servicecode = servicecode;
        }

        public String getStreampath() {
            return streampath;
        }

        public void setStreampath(String streampath) {
            this.streampath = streampath;
        }
    }
}
