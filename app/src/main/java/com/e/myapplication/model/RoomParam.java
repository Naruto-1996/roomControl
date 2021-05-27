package com.e.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class RoomParam {
  @SerializedName("id")
  private int id;
  @SerializedName("deviceId")
  private int deviceId;
  @SerializedName("deviceKey")
  private String deviceKey;
  @SerializedName("deviceValue")
  private String deviceValue;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(int deviceId) {
    this.deviceId = deviceId;
  }

  public String getDeviceKey() {
    return deviceKey;
  }

  public void setDeviceKey(String deviceKey) {
    this.deviceKey = deviceKey;
  }

  public String getDeviceValue() {
    return deviceValue;
  }

  public void setDeviceValue(String deviceValue) {
    this.deviceValue = deviceValue;
  }
}
