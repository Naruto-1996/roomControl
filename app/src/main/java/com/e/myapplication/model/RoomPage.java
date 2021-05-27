package com.e.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomPage {
  @SerializedName("totalCount")
  private int totalCount;
  @SerializedName("pageSize")
  private int pageSize;
  @SerializedName("totalPage")
  private int totalPage;
  @SerializedName("currPage")
  private int currPage;
  @SerializedName("list")
  private List<RoomItem> roomItemList;

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public int getCurrPage() {
    return currPage;
  }

  public void setCurrPage(int currPage) {
    this.currPage = currPage;
  }

  public List<RoomItem> getRoomItemList() {
    return roomItemList;
  }

  public void setRoomItemList(List<RoomItem> roomItemList) {
    this.roomItemList = roomItemList;
  }
}
