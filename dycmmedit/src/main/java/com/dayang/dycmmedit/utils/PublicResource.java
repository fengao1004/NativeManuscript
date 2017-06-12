package com.dayang.dycmmedit.utils;

import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.http.RetrofitHelper;
import com.dayang.dycmmedit.info.FolderInfo;
import com.dayang.dycmmedit.info.UserInfo;
import com.dayang.dycmmedit.info.UserModel;
import com.elvishew.xlog.XLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 冯傲 on 2017/5/11.
 * e-mail 897840134@qq.com
 */

public class PublicResource {
    String storageURL;
    String fileStatusNotifyURL;
    String token;
    String password;
    List<String> authorityList;
    List<String> userNames;
    List<Integer> drawableList;

    public List<Integer> getDrawableList() {
        if (drawableList == null) {
            drawableList = new ArrayList<>();
            drawableList.add(R.drawable.d_aini);
            drawableList.add(R.drawable.d_aoteman);
            drawableList.add(R.drawable.d_baibai);
            drawableList.add(R.drawable.d_baobao);
            drawableList.add(R.drawable.d_beishang);
            drawableList.add(R.drawable.d_bingbujiandan);
            drawableList.add(R.drawable.d_bishi);
            drawableList.add(R.drawable.d_bizui);
            drawableList.add(R.drawable.d_chanzui);
            drawableList.add(R.drawable.d_chijing);
            drawableList.add(R.drawable.d_dahaqi);
            drawableList.add(R.drawable.d_dalian);
            drawableList.add(R.drawable.d_ding);
            drawableList.add(R.drawable.d_doge);
            drawableList.add(R.drawable.d_erha);
            drawableList.add(R.drawable.d_feijie);
            drawableList.add(R.drawable.d_feizao);
            drawableList.add(R.drawable.d_ganmao);
            drawableList.add(R.drawable.d_guzhang);
            drawableList.add(R.drawable.d_haha);
            drawableList.add(R.drawable.d_haixiu);
            drawableList.add(R.drawable.d_han);
            drawableList.add(R.drawable.d_hehe);
            drawableList.add(R.drawable.d_heiheihei);
            drawableList.add(R.drawable.d_heixian);
            drawableList.add(R.drawable.d_heng);
            drawableList.add(R.drawable.d_huaixiao);
            drawableList.add(R.drawable.d_huaxin);
            drawableList.add(R.drawable.d_jiyan);
            drawableList.add(R.drawable.d_keai);
            drawableList.add(R.drawable.d_kelian);
            drawableList.add(R.drawable.d_ku);
            drawableList.add(R.drawable.d_kulou);
            drawableList.add(R.drawable.d_kun);
            drawableList.add(R.drawable.d_landelini);
            drawableList.add(R.drawable.d_lang);
            drawableList.add(R.drawable.d_lei);
            drawableList.add(R.drawable.d_miao);
            drawableList.add(R.drawable.d_nanhaier);
            drawableList.add(R.drawable.d_nu);
            drawableList.add(R.drawable.d_numa);
            drawableList.add(R.drawable.d_nvhaier);
            drawableList.add(R.drawable.d_qian);
            drawableList.add(R.drawable.d_qinqin);
            drawableList.add(R.drawable.d_shayan);
            drawableList.add(R.drawable.d_shengbing);
            drawableList.add(R.drawable.d_shenshou);
            drawableList.add(R.drawable.d_shiwang);
            drawableList.add(R.drawable.d_shuai);
            drawableList.add(R.drawable.d_shuijiao);
            drawableList.add(R.drawable.d_sikao);
            drawableList.add(R.drawable.d_taikaixin);
            drawableList.add(R.drawable.d_tanshou);
            drawableList.add(R.drawable.d_tian);
            drawableList.add(R.drawable.d_touxiao);
            drawableList.add(R.drawable.d_tu);
            drawableList.add(R.drawable.d_tuzi);
            drawableList.add(R.drawable.d_wabishi);
            drawableList.add(R.drawable.d_weiqu);
            drawableList.add(R.drawable.d_wu);
            drawableList.add(R.drawable.d_xiaoku);
            drawableList.add(R.drawable.d_xingxingyan);
            drawableList.add(R.drawable.d_xiongmao);
            drawableList.add(R.drawable.d_xixi);
            drawableList.add(R.drawable.d_xu);
            drawableList.add(R.drawable.d_yinxian);
            drawableList.add(R.drawable.d_yiwen);
            drawableList.add(R.drawable.d_youhengheng);
            drawableList.add(R.drawable.d_yun);
            drawableList.add(R.drawable.d_yunbei);
            drawableList.add(R.drawable.d_zhuakuang);
            drawableList.add(R.drawable.d_zhutou);
            drawableList.add(R.drawable.d_zuiyou);
            drawableList.add(R.drawable.d_zuohengheng);
            drawableList.add(R.drawable.h_buyao);
            drawableList.add(R.drawable.h_good);
            drawableList.add(R.drawable.h_haha);
            drawableList.add(R.drawable.h_jiayou);
            drawableList.add(R.drawable.h_lai);
            drawableList.add(R.drawable.h_ok);
            drawableList.add(R.drawable.h_quantou);
            drawableList.add(R.drawable.h_ruo);
            drawableList.add(R.drawable.h_woshou);
            drawableList.add(R.drawable.h_ye);
            drawableList.add(R.drawable.h_zan);
            drawableList.add(R.drawable.h_zuoyi);
        }
        return drawableList;
    }

    public Map<Integer, String> getDrawableMap() {
        if (drawableMap == null) {
            drawableMap = new HashMap<>();
            drawableMap.put(R.drawable.d_aini, "[爱你]");
            drawableMap.put(R.drawable.d_aoteman, "[奥特曼]");
            drawableMap.put(R.drawable.d_baibai, "[拜拜]");
            drawableMap.put(R.drawable.d_baobao, "[抱抱]");
            drawableMap.put(R.drawable.d_beishang, "[悲伤]");
            drawableMap.put(R.drawable.d_bingbujiandan, "[并不简单]");
            drawableMap.put(R.drawable.d_bishi, "[鄙视]");
            drawableMap.put(R.drawable.d_bizui, "[闭嘴]");
            drawableMap.put(R.drawable.d_chanzui, "[馋嘴]");
            drawableMap.put(R.drawable.d_chijing, "[吃惊]");
            drawableMap.put(R.drawable.d_dahaqi, "[打哈欠]");
            drawableMap.put(R.drawable.d_dalian, "[打脸]");
            drawableMap.put(R.drawable.d_ding, "[叮]");
            drawableMap.put(R.drawable.d_doge, "[doge]");
            drawableMap.put(R.drawable.d_erha, "[二哈]");
            drawableMap.put(R.drawable.d_feijie, "[费解]");
            drawableMap.put(R.drawable.d_feizao, "[肥皂]");
            drawableMap.put(R.drawable.d_ganmao, "[感冒]");
            drawableMap.put(R.drawable.d_guzhang, "[鼓掌]");
            drawableMap.put(R.drawable.d_haha, "[哈哈]");
            drawableMap.put(R.drawable.d_haixiu, "[害羞]");
            drawableMap.put(R.drawable.d_han, "[汗]");
            drawableMap.put(R.drawable.d_hehe, "[呵呵]");
            drawableMap.put(R.drawable.d_heiheihei, "[嘿嘿嘿]");
            drawableMap.put(R.drawable.d_heixian, "[黑线]");
            drawableMap.put(R.drawable.d_heng, "[哼]");
            drawableMap.put(R.drawable.d_huaixiao, "[坏笑]");
            drawableMap.put(R.drawable.d_huaxin, "[花心]");
            drawableMap.put(R.drawable.d_jiyan, "[挤眼]");
            drawableMap.put(R.drawable.d_keai, "[可爱]");
            drawableMap.put(R.drawable.d_kelian, "[可怜]");
            drawableMap.put(R.drawable.d_ku, "[酷]");
            drawableMap.put(R.drawable.d_kulou, "[骷髅]");
            drawableMap.put(R.drawable.d_kun, "[困]");
            drawableMap.put(R.drawable.d_landelini, "[懒得理你]");
            drawableMap.put(R.drawable.d_lang, "[浪]");
            drawableMap.put(R.drawable.d_lei, "[累]");
            drawableMap.put(R.drawable.d_miao, "[喵]");
            drawableMap.put(R.drawable.d_nanhaier, "[男孩儿]");
            drawableMap.put(R.drawable.d_nu, "[怒]");
            drawableMap.put(R.drawable.d_numa, "[怒骂]");
            drawableMap.put(R.drawable.d_nvhaier, "[女孩儿]");
            drawableMap.put(R.drawable.d_qian, "[钱]");
            drawableMap.put(R.drawable.d_qinqin, "[亲亲]");
            drawableMap.put(R.drawable.d_shayan, "[傻眼]");
            drawableMap.put(R.drawable.d_shengbing, "[生病]");
            drawableMap.put(R.drawable.d_shenshou, "[神兽]");
            drawableMap.put(R.drawable.d_shiwang, "[失望]");
            drawableMap.put(R.drawable.d_shuai, "[衰]");
            drawableMap.put(R.drawable.d_shuijiao, "[睡觉]");
            drawableMap.put(R.drawable.d_sikao, "[思考]");
            drawableMap.put(R.drawable.d_taikaixin, "[太开心]");
            drawableMap.put(R.drawable.d_tanshou, "[摊手]");
            drawableMap.put(R.drawable.d_tian, "[舔]");
            drawableMap.put(R.drawable.d_touxiao, "[偷笑]");
            drawableMap.put(R.drawable.d_tu, "[吐]");
            drawableMap.put(R.drawable.d_tuzi, "[兔子]");
            drawableMap.put(R.drawable.d_wabishi, "[挖鼻屎]");
            drawableMap.put(R.drawable.d_weiqu, "[委屈]");
            drawableMap.put(R.drawable.d_wu, "[捂]");
            drawableMap.put(R.drawable.d_xiaoku, "[笑哭]");
            drawableMap.put(R.drawable.d_xingxingyan, "[星星眼]");
            drawableMap.put(R.drawable.d_xiongmao, "[熊猫]");
            drawableMap.put(R.drawable.d_xixi, "[嘻嘻]");
            drawableMap.put(R.drawable.d_xu, "[嘘]");
            drawableMap.put(R.drawable.d_yinxian, "[阴险]");
            drawableMap.put(R.drawable.d_yiwen, "[疑问]");
            drawableMap.put(R.drawable.d_youhengheng, "[右哼哼]");
            drawableMap.put(R.drawable.d_yun, "[晕]");
            drawableMap.put(R.drawable.d_yunbei, "[允悲]");
            drawableMap.put(R.drawable.d_zhuakuang, "[抓狂]");
            drawableMap.put(R.drawable.d_zhutou, "[猪头]");
            drawableMap.put(R.drawable.d_zuiyou, "[最右]");
            drawableMap.put(R.drawable.d_zuohengheng, "[左哼哼]");
            drawableMap.put(R.drawable.h_buyao, "[NO]");
            drawableMap.put(R.drawable.h_good, "[good]");
            drawableMap.put(R.drawable.h_haha, "[哈哈]");
            drawableMap.put(R.drawable.h_jiayou, "[加油]");
            drawableMap.put(R.drawable.h_lai, "[来]");
            drawableMap.put(R.drawable.h_ok, "[ok]");
            drawableMap.put(R.drawable.h_quantou, "[拳头]");
            drawableMap.put(R.drawable.h_ruo, "[弱]");
            drawableMap.put(R.drawable.h_woshou, "[握手]");
            drawableMap.put(R.drawable.h_ye, "[耶]");
            drawableMap.put(R.drawable.h_zan, "[赞]");
            drawableMap.put(R.drawable.h_zuoyi, "[作揖]");
        }
        return drawableMap;
    }

    Map<Integer, String> drawableMap;

    public void setAllFolder(boolean allFolder) {
        isAllFolder = allFolder;
    }

    public void setFolderInfos(List<FolderInfo> folderInfos) {
        this.folderInfos = folderInfos;
    }

    public boolean isAllFolder() {
        return isAllFolder;
    }

    public List<FolderInfo> getFolderInfos() {
        return folderInfos;
    }

    boolean isAllFolder;
    List<FolderInfo> folderInfos = new ArrayList<>();
    List<String> userCodes;
    List<UserModel> userList;

    public void setUserList(List<UserModel> userList) {
        this.userList = userList;
    }

    public List<UserModel> getUserList() {
        return userList;
    }

    public void setUserCodes(List<String> userCodes) {
        this.userCodes = userCodes;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public List<String> getUserCodes() {
        return userCodes;
    }

    public String getToken() {
        return token;
    }

    public List<String> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<UserInfo.DataEntity.PrivilegeLoadModelEntity> list) {
        authorityList = authorityList == null ? new ArrayList<String>() : authorityList;
        authorityList.clear();
        for (int i = 0; i < list.size(); i++) {
            XLog.i("setAuthorityList: " + list.get(i).getId());
            authorityList.add(list.get(i).getId());
        }
    }

    public String getPassword() {
        return password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    List<UserInfo.DataEntity.ColumnListModelEntity> columnNameList;

    public void setColumnNameList(List<UserInfo.DataEntity.ColumnListModelEntity> columnNameList) {
        this.columnNameList = columnNameList;
    }

    public List<UserInfo.DataEntity.ColumnListModelEntity> getColumnNameList() {
        return columnNameList;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserCode() {
        return userCode;
    }

    String userCode;
    String userName;
    String streamPath;
    static PublicResource publicResource;

    private PublicResource() {
    }

    public static PublicResource getInstance() {
        if (publicResource == null) {
            publicResource = new PublicResource();
        }
        return publicResource;
    }

    public String getStorageURL() {
        return storageURL;
    }

    public void setStreamPath(String streamPath) {
        this.streamPath = streamPath;
    }

    public String getFileStatusNotifyURL() {
        return fileStatusNotifyURL;
    }

    public String getStreamPath() {
        return streamPath;
    }

    public void setStorageURL(String storageURL) {
        this.storageURL = storageURL;
    }

    public void setFileStatusNotifyURL(String fileStatusNotifyURL) {
        this.fileStatusNotifyURL = fileStatusNotifyURL;
    }
}

