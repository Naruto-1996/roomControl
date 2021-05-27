package com.e.myapplication.model;


import com.google.gson.annotations.SerializedName;

public class User {
  @SerializedName("userId")
  private Integer userId;
  @SerializedName("username")
  private String username;
//  @SerializedName("password")
//  private String password;
//  @SerializedName("salt")
//  private String salt;
//  @SerializedName("email")
//  private String email;
//  @SerializedName("mobile")
//  private String mobile;
//  @SerializedName("status")
//  private Integer status;
//  @SerializedName("roleIdList")
//  private Object roleIdList;
//  @SerializedName("createUserId")
//  private Integer createUserId;
//  @SerializedName("createTime")
//  private String createTime;


  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
//
//  public String getPassword() {
//    return password;
//  }
//
//  public void setPassword(String password) {
//    this.password = password;
//  }
//
//  public String getSalt() {
//    return salt;
//  }
//
//  public void setSalt(String salt) {
//    this.salt = salt;
//  }
//
//  public String getEmail() {
//    return email;
//  }
//
//  public void setEmail(String email) {
//    this.email = email;
//  }
//
//  public String getMobile() {
//    return mobile;
//  }
//
//  public void setMobile(String mobile) {
//    this.mobile = mobile;
//  }
//
//  public Integer getStatus() {
//    return status;
//  }
//
//  public void setStatus(Integer status) {
//    this.status = status;
//  }
//
//  public Object getRoleIdList() {
//    return roleIdList;
//  }
//
//  public void setRoleIdList(Object roleIdList) {
//    this.roleIdList = roleIdList;
//  }
//
//  public Integer getCreateUserId() {
//    return createUserId;
//  }
//
//  public void setCreateUserId(Integer createUserId) {
//    this.createUserId = createUserId;
//  }
//
//  public String getCreateTime() {
//    return createTime;
//  }
//
//  public void setCreateTime(String createTime) {
//    this.createTime = createTime;
//  }
}
