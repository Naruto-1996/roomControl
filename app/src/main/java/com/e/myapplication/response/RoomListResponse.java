package com.e.myapplication.response;

import com.e.myapplication.model.RoomPage;
import com.google.gson.annotations.SerializedName;

public class RoomListResponse {
  @SerializedName("msg")
  private String msg;
  @SerializedName("code")
  private int code;
  @SerializedName("page")
  private RoomPage page;

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

  public RoomPage getPage() {
    return page;
  }

  public void setPage(RoomPage page) {
    this.page = page;
  }
}
