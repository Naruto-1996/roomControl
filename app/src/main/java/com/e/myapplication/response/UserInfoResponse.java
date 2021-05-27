package com.e.myapplication.response;

import com.e.myapplication.model.User;
import com.google.gson.annotations.SerializedName;

public class UserInfoResponse {
  @SerializedName("msg")
  private String msg;
  @SerializedName("code")
  private int code;
  @SerializedName("user")
  private User user;


  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
