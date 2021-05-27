package com.e.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.e.myapplication.R;
import com.e.myapplication.databinding.ActivityLoginBinding;
import com.e.myapplication.network.Constant;
import com.e.myapplication.network.OkHttp3;
import com.e.myapplication.response.LoginResponse;
import com.e.myapplication.response.UserInfoResponse;
import com.e.myapplication.utils.SharedPreferencesUtil;
import com.e.myapplication.utils.UiOperation;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {

  private ActivityLoginBinding binding;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

    // 初始化点击事件
    initListener();
  }


  private void initListener() {
    binding.login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // 获取用户名和密码输入框的值
        String username = binding.username.getText().toString();
        String password = binding.password.getText().toString();
        // 判断为空的情况
        if (username.isEmpty()) {
          // 消息提示
          ToastUtils.show("用户名为空");
        } else if (password.isEmpty()) {
          ToastUtils.show("密码为空");
        } else {
          UiOperation.showLoading(LoginActivity.this);
          // 请求登录
          requestLogin();
        }
      }
    });
  }

  // 登录接口
  private void requestLogin() {
    OkHttp3.getInstance().doPost(Constant.BASE_URL + "/wx/mobile/login", new HashMap<String, String>() {{
      put("username", binding.username.getText().toString());
      put("password", binding.password.getText().toString());
    }}, new OkHttp3.NetCallBack() {
      @Override
      public void onSuccess(String data) {
        Log.i("=======>>>>>>>>1111111", data);
        final LoginResponse response = new Gson().fromJson(data, LoginResponse.class);
        if (response.getCode() == 200) {
          SharedPreferencesUtil.setString("token", response.getToken());
          SharedPreferencesUtil.setBoolean("is_login", true);
          requestUserInfo();
        } else {
          ToastUtils.show(response.getMsg());
          runOnUiThread(UiOperation::closeLoading);
        }
      }

      @Override
      public void onFailure(Exception e) {
        ToastUtils.show("请求失败!");
      }
    });
  }

  // 请求用户信息
  private void requestUserInfo() {
    OkHttp3.getInstance().doGet(Constant.BASE_URL + "/wx/sys/user/info", new OkHttp3.NetCallBack() {
      @Override
      public void onSuccess(String data) {
        final UserInfoResponse response = new Gson().fromJson(data, UserInfoResponse.class);
        Log.i("=======>>>>>>>>22222222", data);
        if (response.getCode() == 200) {
          SharedPreferencesUtil.setString("userId", String.valueOf(response.getUser().getUserId()));
          SharedPreferencesUtil.setString("userName", response.getUser().getUsername());
          startActivity(new Intent(LoginActivity.this, MainActivity.class));
          finish();
        } else {
          ToastUtils.show(response.getMsg());
        }
        runOnUiThread(UiOperation::closeLoading);
      }

      @Override
      public void onFailure(Exception e) {

      }
    });
  }


}