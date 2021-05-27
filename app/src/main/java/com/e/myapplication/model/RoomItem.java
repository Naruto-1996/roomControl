package com.e.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomItem {
  @SerializedName("id")
  private String id;
  @SerializedName("name")
  private String name;
  @SerializedName("description")
  private String description;
  @SerializedName("createTime")
  private String createTime;
  @SerializedName("status")
  private boolean status;
  @SerializedName("imageUrl")
  private String imageUrl;
  @SerializedName("isConnect")
  private boolean isConnect;
  @SerializedName("params")
  private List<RoomParam> paramList;

  public boolean isConnect() {
    return isConnect;
  }

  public void setConnect(boolean connect) {
    isConnect = connect;
  }

  public List<RoomParam> getParamList() {
    return paramList;
  }

  public void setParamList(List<RoomParam> paramList) {
    this.paramList = paramList;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
