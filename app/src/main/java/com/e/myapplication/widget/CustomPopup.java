package com.e.myapplication.widget;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.myapplication.R;
import com.e.myapplication.adapter.RoomDetailAdapter;
import com.e.myapplication.model.RoomParam;
import com.lxj.xpopup.core.CenterPopupView;

import java.util.List;

public class CustomPopup extends CenterPopupView {

  // 数据源
  private List<RoomParam> roomParams;

  public void setRoomParams(List<RoomParam> roomParams) {
    this.roomParams = roomParams;
  }

  public CustomPopup(@NonNull Context context) {
    super(context);
  }

  // 返回自定义弹窗的布局
  @Override
  protected int getImplLayoutId() {
    return R.layout.custom_popup;
  }

  // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
  @Override
  protected void onCreate() {
    super.onCreate();
    RecyclerView recyclerView = findViewById(R.id.recycleView);
    RoomDetailAdapter adapter = new RoomDetailAdapter();
    recyclerView.setAdapter(adapter);
    adapter.setRoomParamsList(roomParams);
  }

  // 设置最大宽度，看需要而定，
  @Override
  protected int getMaxWidth() {
    return super.getMaxWidth();
  }

  // 设置最大高度，看需要而定
  @Override
  protected int getMaxHeight() {
    return super.getMaxHeight();
  }


}
