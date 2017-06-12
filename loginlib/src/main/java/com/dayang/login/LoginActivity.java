package com.dayang.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import io.reactivex.annotations.NonNull;


/**
 * Created by 冯傲 on 2017/4/26.
 * e-mail 897840134@qq.com
 */

public class LoginActivity extends AppCompatActivity {

    private EditText edit_login_password;
    private EditText edit_login_username;
    private ProgressDialog progressDialog;
    private Button button_login;
    String TAG = "fengao";
    private Class clazz;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_login_password = (EditText) findViewById(R.id.edit_login_password);
        Drawable drawable1 = getResources().getDrawable(R.drawable.ic_login_password_default);
        drawable1.setBounds(0, 0, 60, 65);
        edit_login_password.setCompoundDrawables(drawable1, null, null, null);
        edit_login_username = (EditText) findViewById(R.id.edit_login_username);
        Drawable drawable2 = getResources().getDrawable(R.drawable.ic_login_username_default);
        drawable2.setBounds(0, 0, 60, 65);
        edit_login_username.setCompoundDrawables(drawable2, null, null, null);
        button_login = (Button) findViewById(R.id.button_login);
        clazz = (Class) getIntent().getExtras().get("aimActivityClass");
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edit_login_username.getText().toString().trim();
                String password = edit_login_password.getText().toString().trim();
                if (username.equals("")) {
                    Toast.makeText(LoginActivity.this, "用户名不能为空", 0).show();
                    return;
                }
                if (password.equals("")) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", 0).show();
                    return;
                }
                if (!username.contains("@")) {
                    Toast.makeText(LoginActivity.this, "账号格式错误", 0).show();
                    return;
                }
                login(username, password);
            }
        });
    }

    private void login(String username, String password) {
        progressDialog.show();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.userName = parseUserName(username);
        loginInfo.password = password;
        loginInfo.domainName = parseDomainName(username);
        loginInfo.productCode = "cmtools";
        RetrofitClient.getInstance(this).login(new BaseObserver<Info>() {
            @Override
            public void onNext(@NonNull Info info) {
                Log.i(TAG, "onNext: " + new Gson().toJson(info));
                SharedPreferencesUtils.setParam(LoginActivity.this, LoginManger.USERINFO, "");
                startActivity(new Intent(LoginActivity.this, clazz));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(LoginActivity.this, "登录失败", 0).show();
            }

            @Override
            public void onComplete() {
                super.onComplete();
                progressDialog.dismiss();

            }
        }, loginInfo);
    }


    public ProgressDialog getProgressDialog() {
        if (progressDialog != null) {
            return progressDialog;
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("登录中");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            return progressDialog;
        }
    }

    public String parseUserName(String username) {
        if (username.contains("@")) {
            return username.split("@")[0];
        }
        return "";
    }

    public String parseDomainName(String username) {
        if (username.contains("@")) {
            return username.split("@")[1];
        }
        return "";
    }

    public class LoginInfo {
        public String userName;
        public String password;
        public String domainName;
        public String productCode;
//        'userName': parseUserName(strUserName),
//        'password': strPassWord,
//                'domainName': parseDomainName(strUserName),
//        'productCode': 'cmtools'

    }
}
