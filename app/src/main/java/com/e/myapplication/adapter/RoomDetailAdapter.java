package com.e.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.e.myapplication.R;
import com.e.myapplication.databinding.ItemRoomBinding;
import com.e.myapplication.databinding.ItemRoomDetailParamsBinding;
import com.e.myapplication.model.RoomItem;
import com.e.myapplication.model.RoomParam;

import java.util.List;

public class RoomDetailAdapter extends RecyclerView.Adapter<RoomDetailAdapter.ViewHolder> {

  // 数据源
  private List<RoomParam> roomParams;

  // 设置数据源
  public void setRoomParamsList(List<RoomParam> roomParams) {
    this.roomParams = roomParams;
    // 当数据发生改变时更新页面
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    // 加载布局文件
    ItemRoomDetailParamsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_room_detail_params, parent, false);
    return new ViewHolder(binding.getRoot());
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ItemRoomDetailParamsBinding binding = DataBindingUtil.getBinding(holder.itemView);
    binding.executePendingBindings();
    binding.setData(roomParams.get(position));
  }

  @Override
  public int getItemCount() {
    return roomParams == null ? 0 : roomParams.size();
  }


  static class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
