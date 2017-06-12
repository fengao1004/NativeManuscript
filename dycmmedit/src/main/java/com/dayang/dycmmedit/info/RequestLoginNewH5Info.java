package com.dayang.dycmmedit.info;

/**
 * Created by 冯傲 on 2017/5/2.
 * e-mail 897840134@qq.com
 */

public class RequestLoginNewH5Info {

    public RequestLoginNewH5Info(String userId, String tokenId) {
        this.userId = userId;
        this.tokenId = tokenId;
    }

    /**
     * userId : 0003
     * tokenId : TGT-692-UA2XQfX7BVzLObeX1VECdfS0IZccD9tluqOowqecQxxvfmMAP2-cas
     */

    private String userId;
    private String tokenId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
