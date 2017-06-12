package com.dayang.dycmmedit.info;

import com.elvishew.xlog.XLog;

import org.apache.commons.net.util.Base64;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 冯傲 on 2017/5/2.
 * e-mail 897840134@qq.com
 */

public class ManuscriptListInfo implements Serializable {
    public static final int MANUSCRIPT_TYPE_CMS = 0;
    public static final int MANUSCRIPT_TYPE_WECHAT = 1;
    public static final int MANUSCRIPT_TYPE_WEIBO = 2;
    public static final int MANUSCRIPT_TYPE_TV = 3;


    public static final int MANUSCRIPT_STATUS_WAITING_SUBMIT = 0;
    public static final int MANUSCRIPT_STATUS_WAITING_PENDING = 1;
    public static final int MANUSCRIPT_STATUS_PASS = 2;
    public static final int MANUSCRIPT_STATUS_FAIL = 3;
    public ArrayList<CreateDialogItemInfo> list;
    public ArrayList<CreateDialogItemInfo> wechatChildList = new ArrayList<>();

    public ManuscriptListInfo(int manuscriptType) {
        init(manuscriptType);
    }

    public ManuscriptListInfo() {

    }

    public String[] reporters = new String[]{"暂不指定", "张三", "李四"};//记者
    public String[] editors = new String[]{"暂不指定", "张三", "李四"};//编辑
    public String[] montagers = new String[]{"暂不指定", "张三", "李四"};//剪辑
    public String[] cameramans = new String[]{"暂不指定", "张三", "李四"};//摄像师
    public boolean isReleaseThumbnail;

    public ArrayList<String> columns;//栏目
    public ArrayList<String> columnsID;

    public int arrayindex;
    public String camerist = "";
    public String nickname = "";//昵称
    public String columnid = "";
    public String columnname = "";
    public String createtime = "";
    public String editor = "";//作者
    public String estimatetime = "";
    public String fatherid = "";
    public String introduction = "";//导语
    public int fathersonmark;
    public String folderid = ""; //选题
    public String montager = "";//剪辑员
    public String foldername = "";//选题id
    public String h5content = "";
    public String header = "";
    public String hjcolumn_id = "";//新媒体
    public String hjcolumn_name = "";//新媒体
    public int iscomment;
    public int isdeleted;
    public String keywords = "";//关键字
    public String lastmodifier = "";
    public String lastmodifytime = "";
    public String manuscriptid = "";
    public int manuscripttype;
    public int mnum;
    public int releasetype;
    public String reporter = "";
    public String resourcesid = "";
    public String sources = "";
    public String sourceurl = "";//原文链接
    public int status;
    public String subtitle = "";
    public String summary = "";
    public String systemid = "";
    public String textcontent = "";
    public String usercode = "";
    public String username = "";
    public String weixinlowimage = "";

    public void init(int manuscriptType) {
        this.manuscripttype = manuscriptType;
        list = new ArrayList<>();
        list.clear();
        wechatChildList.add(new CreateDialogItemInfo("昵称", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//reporter
        wechatChildList.add(new CreateDialogItemInfo("缩略图发布到正文", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_CHECKBOX));// "1" 发布  "0"不发布
        wechatChildList.add(new CreateDialogItemInfo("摘要", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//summary
        switch (manuscriptType) {
            case MANUSCRIPT_TYPE_CMS:
                list.add(new CreateDialogItemInfo("标题", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//subtitle
                list.add(new CreateDialogItemInfo("副标题", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//subtitle
                list.add(new CreateDialogItemInfo("选题", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_PULL_LIST));//folderid foldername
                list.add(new CreateDialogItemInfo("所属栏目", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_PULL_LIST));
                list.add(new CreateDialogItemInfo("新媒体栏目", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_PULL_LIST));//hjcolumn_name hjcolumn_id
                list.add(new CreateDialogItemInfo("来源", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//sources
                list.add(new CreateDialogItemInfo("作者", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//editor
                list.add(new CreateDialogItemInfo("关键字", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//keywords
                list.add(new CreateDialogItemInfo("原文链接", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//sourceurl
                list.add(new CreateDialogItemInfo("摘要", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//summary
                list.add(new CreateDialogItemInfo("开启评论列表", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_CHECKBOX));//iscomment "1"开启 "0"关闭 int
                break;
            case MANUSCRIPT_TYPE_WECHAT:
                list.add(new CreateDialogItemInfo("标题", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//subtitle
                list.add(new CreateDialogItemInfo("选题", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_PULL_LIST));
                list.add(new CreateDialogItemInfo("所属栏目", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_PULL_LIST));
                list.add(new CreateDialogItemInfo("昵称", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//reporter
                list.add(new CreateDialogItemInfo("缩略图发布到正文", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_CHECKBOX));// "1" 发布  "0"不发布
                list.add(new CreateDialogItemInfo("摘要", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//summary
                break;
            case MANUSCRIPT_TYPE_WEIBO:
                list.add(new CreateDialogItemInfo("标题", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//subtitle
                list.add(new CreateDialogItemInfo("选题", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_PULL_LIST));//folderid foldername
                list.add(new CreateDialogItemInfo("所属栏目", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_PULL_LIST));//columnname columnid
                break;
            case MANUSCRIPT_TYPE_TV:
                list.add(new CreateDialogItemInfo("标题", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//subtitle
                list.add(new CreateDialogItemInfo("选题", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_PULL_LIST));//folderid foldername
                list.add(new CreateDialogItemInfo("所属栏目", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_PULL_LIST));//columnname columnid
                list.add(new CreateDialogItemInfo("记者", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//reporter
                list.add(new CreateDialogItemInfo("摄像员", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//camerist
                list.add(new CreateDialogItemInfo("剪辑员", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//editor
                list.add(new CreateDialogItemInfo("来源", CreateDialogItemInfo.CREATE_DIALOG_ITEM_TYPE_INPUT));//sources
                break;
        }
    }

    public void copy(ManuscriptListInfo info) {
        columns = info.columns;
        columnsID = info.columnsID;
        arrayindex = info.arrayindex;
        camerist = info.camerist;
        nickname = info.nickname;
        columnid = info.columnid;
        columnname = info.columnname;
        createtime = info.createtime;
        editor = info.editor;
        estimatetime = info.estimatetime;
        fatherid = info.fatherid;
        introduction = info.introduction;
        fathersonmark = info.fathersonmark;
        folderid = info.folderid;
        montager = info.montager;
        foldername = info.foldername;
        h5content = info.h5content;
        header = info.header;
        hjcolumn_id = info.hjcolumn_id;
        hjcolumn_name = info.hjcolumn_name;
        iscomment = info.iscomment;
        isdeleted = info.isdeleted;
        keywords = info.keywords;
        lastmodifier = info.lastmodifier;
        lastmodifytime = info.lastmodifytime;
        manuscriptid = info.manuscriptid;
        manuscripttype = info.manuscripttype;
        mnum = info.mnum;
        releasetype = info.releasetype;
        reporter = info.reporter;
        resourcesid = info.resourcesid;
        sources = info.sources;
        sourceurl = info.sourceurl;
        status = info.status;
        subtitle = info.subtitle;
        summary = info.summary;
        systemid = info.systemid;
        textcontent = info.textcontent;
        usercode = info.usercode;
        username = info.username;
        weixinlowimage = info.weixinlowimage;
    }

    public Map getMap() {
        Map<String, String> map = new HashMap<>();
        map.put("manuscripttype", this.manuscripttype + "");
        map.put("manuscriptid", this.manuscriptid);
        map.put("header", this.header);
        map.put("usercode", this.usercode);
        map.put("username", this.username);
        map.put("columnname", this.columnname);
        folderid = this.folderid.equals("") ? "" : folderid;
        foldername = this.folderid.equals("") ? "" : foldername;
        map.put("folderid", this.folderid);
        map.put("foldername", this.foldername);
        map.put("hjcolumn_id", this.hjcolumn_id);
        map.put("hjcolumn_name", this.hjcolumn_name);
        map.put("camerist", this.camerist);
        map.put("editor", this.editor);
        map.put("sources", this.sources);
        map.put("montager", this.montager);

        map.put("summary", this.summary);
        map.put("reporter", this.reporter);
        map.put("sourceurl", this.sourceurl);
        map.put("subtitle", this.subtitle);
        map.put("keywords", this.keywords);
        map.put("nickname", this.nickname);

//
//        map.put("createtime", this.createtime);
//        map.put("lastmodifytime", this.lastmodifytime);
//        map.put("estimatetime", this.estimatetime);

        map.put("fatherid", this.fatherid);
        map.put("introduction", this.introduction);
        map.put("lastmodifier", this.lastmodifier);
        map.put("resourcesid", this.resourcesid);
        map.put("systemid", this.systemid);
//        map.put("weixinlowimage", this.weixinlowimage);
        if (weixinlowimage.equals("")) {
            map.put("weixinlowimage", this.weixinlowimage);
        }
        String columnid = "";
        for (int i = 0; i < this.columns.size(); i++) {
            if (i != 0 && this.columnname.equals(this.columns.get(i))) {
                columnid = this.columnsID.get(i - 1);
                break;
            }
        }
        byte[] bytes = Base64.encodeBase64(this.h5content.getBytes());
        String h5Content = new String(bytes);
        map.put("columnid", columnid);
        map.put("textcontent", this.textcontent);
        map.put("h5content", h5Content);
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("iscomment", this.iscomment);
        map2.put("arrayindex", this.arrayindex);
        map2.put("fathersonmark", this.fathersonmark);
        map2.put("isdeleted", this.isdeleted);
        map2.put("mnum", this.mnum);
        map2.put("releasetype", this.releasetype);
        map2.put("status", this.status);
        Map<String, Map> mapMap = new HashMap<>();
        mapMap.put("stringMap", map);
        mapMap.put("int", map2);
        return mapMap;
    }
}
