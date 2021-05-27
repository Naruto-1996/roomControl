package com.e.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.e.myapplication.R;
import com.e.myapplication.databinding.ItemRoomBinding;
import com.e.myapplication.model.RoomItem;
import com.e.myapplication.widget.CustomPopup;
import com.lxj.xpopup.XPopup;

import java.util.List;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.ViewHolder> {

  // 数据源
  private List<RoomItem> roomList;

  // 设置数据源
  public void setRoomList(List<RoomItem> roomList) {
    this.roomList = roomList;
    // 当数据发生改变时更新页面
    notifyDataSetChanged();
  }

  // 刷新
  public void refresh(List<RoomItem> refreshList) {
    if (roomList.size() > 0) {
      roomList.clear();
    }
    this.roomList = refreshList;
    notifyDataSetChanged();
  }

  // 加载
  public void addData(List<RoomItem> room) {
    roomList.addAll(room);
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    // 加载布局文件
    ItemRoomBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_room, parent, false);
    return new ViewHolder(binding.getRoot());
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    // 当布局文件被绑定时可以处理一些事情 比如点击事件、页面跳转等
    ItemRoomBinding binding = DataBindingUtil.getBinding(holder.itemView);
    binding.executePendingBindings();
    binding.setData(roomList.get(position));
    binding.linear.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        CustomPopup popup = new CustomPopup(holder.itemView.getContext());
        popup.setRoomParams(roomList.get(position).getParamList());
        new XPopup.Builder(holder.itemView.getContext())
          .asCustom(popup)
          .show();
      }
    });
  }

  @Override
  public int getItemCount() {
    return roomList == null ? 0 : roomList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
