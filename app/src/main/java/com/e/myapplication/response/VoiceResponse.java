package com.e.myapplication.response;

import com.google.gson.annotations.SerializedName;

public class VoiceResponse {
  @SerializedName("msg")
  private String msg;
  @SerializedName("code")
  private int code;

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
}
