package com.dayang.dycmmedit.info;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/18.
 * e-mail 897840134@qq.com
 */

public class ResultGetCreFolder {

    /**
     * data : {"customPageArr":[1],"list":[{"children":[],"groupId":"","id":"483900b4-b25e-4856-976b-1a2ca1a9527e","name":"随心拍20170424120849_新闻直播间"}],"page":1,"pageArr":[1],"pageArrCount":1,"pageSize":14,"totalCount":1,"totalPage":1}
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
         * customPageArr : [1]
         * list : [{"children":[],"groupId":"","id":"483900b4-b25e-4856-976b-1a2ca1a9527e","name":"随心拍20170424120849_新闻直播间"}]
         * page : 1
         * pageArr : [1]
         * pageArrCount : 1
         * pageSize : 14
         * totalCount : 1
         * totalPage : 1
         */

        private int page;
        private int pageArrCount;
        private int pageSize;
        private int totalCount;
        private int totalPage;
        private List<Integer> customPageArr;
        private List<FolderInfo> list;
        private List<Integer> pageArr;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPageArrCount() {
            return pageArrCount;
        }

        public void setPageArrCount(int pageArrCount) {
            this.pageArrCount = pageArrCount;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<Integer> getCustomPageArr() {
            return customPageArr;
        }

        public void setCustomPageArr(List<Integer> customPageArr) {
            this.customPageArr = customPageArr;
        }

        public List<FolderInfo> getList() {
            return list;
        }

        public void setList(List<FolderInfo> list) {
            this.list = list;
        }

        public List<Integer> getPageArr() {
            return pageArr;
        }

        public void setPageArr(List<Integer> pageArr) {
            this.pageArr = pageArr;
        }
    }
}
