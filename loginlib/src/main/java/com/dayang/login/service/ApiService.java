package com.dayang.login.service;


import com.dayang.login.Info;
import com.dayang.login.LoginActivity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 冯傲 on 2017/4/26.
 * e-mail 897840134@qq.com
 */

public interface ApiService {
    String BaseUrl = "http://appservice.dayang.com/portal/";
    @POST("api/projService")
    Observable<Info> login(@Body LoginActivity.LoginInfo json);
}
