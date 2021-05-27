package com.e.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;

import com.e.myapplication.R;
import com.e.myapplication.adapter.RoomListAdapter;
import com.e.myapplication.databinding.ActivityMainBinding;
import com.e.myapplication.network.Constant;
import com.e.myapplication.network.OkHttp3;
import com.e.myapplication.response.LoginResponse;
import com.e.myapplication.response.RoomListResponse;
import com.e.myapplication.response.VoiceResponse;
import com.e.myapplication.utils.SharedPreferencesUtil;
import com.e.myapplication.utils.UiOperation;
import com.e.myapplication.widget.RecordAudioButton;
import com.e.myapplication.widget.RecordVoicePopWindow;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import kr.co.namee.permissiongen.PermissionGen;


public class MainActivity extends AppCompatActivity implements MainContract.View {

  private ActivityMainBinding binding;
  private MainContract.Presenter mPresenter;
  private RecordVoicePopWindow mRecordVoicePopWindow;//提示
  private RoomListAdapter adapter;
  private int currentPage = 1;
  private int totalPage = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    initListener();
    mPresenter = new MainPresenter<MainContract.View>(this, this);
    // 设置按住说话监听
    initMic();
    // 初始化循环列表
    initRecycleView();
    //请求麦克风权限
    requestPermission();
    // 初始化presenter层
    mPresenter.init();
    // 请求房屋设备信息
    initData(String.valueOf(currentPage), "");
  }


  @Override
  protected void onResume() {
    super.onResume();
    //  设置登录名
    binding.username.setText(SharedPreferencesUtil.getString("userName"));
  }

  // 上拉刷新 下拉加载
  private void initListener() {
    binding.refresh.setOnRefreshListener(refreshLayout -> {
      currentPage = 1;
      initData(String.valueOf(currentPage), "refresh");
      binding.refresh.finishRefresh(500);
    });
    binding.refresh.setOnLoadMoreListener(refreshLayout -> {
      currentPage++;
      if (currentPage <= totalPage) {
        Log.i("====>>>>>>>>>", currentPage + "");
        initData(String.valueOf(currentPage), "addMore");
      } else {
        binding.refresh.setNoMoreData(true);
      }
      binding.refresh.finishLoadMore(500);
    });

    binding.logout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        SharedPreferencesUtil.setBoolean("is_login", false);
        SharedPreferencesUtil.setString("userName", "");
        SharedPreferencesUtil.setString("userId", "");
        SharedPreferencesUtil.setString("token", "");
        finish();
      }
    });
  }

  private void initRecycleView() {
    adapter = new RoomListAdapter();
    binding.recycleView.setAdapter(adapter);
  }


  // 请求列表数据
  private void initData(String currentPage, String type) {
    OkHttp3.getInstance().doPost(Constant.BASE_URL + "/wx/device/pageInfo", new HashMap<String, String>() {{
      put("page", currentPage);
      put("limit", "10");
    }}, new OkHttp3.NetCallBack() {
      @Override
      public void onSuccess(String data) {
        Log.i("+++====>>>>>>>>", data);
        final RoomListResponse response = new Gson().fromJson(data, RoomListResponse.class);
        if (response.getCode() == 200) {
          if (type.equals("refresh")) {
            Log.i("======>>>>>>>>", "dddddddd");
            adapter.refresh(response.getPage().getRoomItemList());
          } else if (type.equals("addMore")) {
            adapter.addData(response.getPage().getRoomItemList());
          } else {
            adapter.setRoomList(response.getPage().getRoomItemList());
          }
          totalPage = response.getPage().getTotalPage();
        } else {
          ToastUtils.show(response.getMsg());
        }
      }

      @Override
      public void onFailure(Exception e) {
        ToastUtils.show("请求失败!");
      }
    });
  }


  private void initMic() {
    binding.btnVoice.setOnVoiceButtonCallBack(new RecordAudioButton.OnVoiceButtonCallBack() {
      @Override
      public void onStartRecord() {
        mPresenter.startRecord();
        Log.i("======>>>>>>>>", "开始");
      }

      @Override
      public void onStopRecord() {
        mPresenter.stopRecord();
        Log.i("======>>>>>>>>", "暂停");
      }

      @Override
      public void onWillCancelRecord() {
        mPresenter.willCancelRecord();
        Log.i("======>>>>>>>>", "取消");
      }

      @Override
      public void onContinueRecord() {
        mPresenter.continueRecord();

      }
    });
  }


  private void requestPermission() {
    PermissionGen.with(this)
      .addRequestCode(100)
      .permissions(Manifest.permission.RECORD_AUDIO, Manifest.permission.WAKE_LOCK)
      .request();
  }

  //  当完成录制语音
  @Override
  public void onVoiceComplete(String filePath) {
    UiOperation.showLoading(MainActivity.this);
    OkHttp3.getInstance().doFile(Constant.BASE_URL + "/wx/device/parseVoice", filePath, new OkHttp3.NetCallBack() {
      @Override
      public void onSuccess(String data) {
        runOnUiThread(UiOperation::closeLoading);
        final VoiceResponse response = new Gson().fromJson(data, VoiceResponse.class);
        ToastUtils.show(response.getMsg());
        // 刷新列表
        currentPage = 1;
        initData(String.valueOf(currentPage), "refresh");
        binding.refresh.finishRefresh(500);
      }

      @Override
      public void onFailure(Exception e) {
        ToastUtils.show("接口调用失败");
      }
    });
  }

  @Override
  public void showList(List<File> list) {

  }

  @Override
  public void showNormalTipView() {
    if (mRecordVoicePopWindow == null) {
      mRecordVoicePopWindow = new RecordVoicePopWindow(this);
    }
    mRecordVoicePopWindow.showAsDropDown(binding.myRoot);
  }

  @Override
  public void showTimeOutTipView(int remainder) {
    if (mRecordVoicePopWindow != null) {
      mRecordVoicePopWindow.showTimeOutTipView(remainder);
    }
  }

  @Override
  public void showRecordingTipView() {
    if (mRecordVoicePopWindow != null) {
      mRecordVoicePopWindow.showRecordingTipView();
    }
  }

  @Override
  public void showRecordTooShortTipView() {
    if (mRecordVoicePopWindow != null) {
      mRecordVoicePopWindow.showRecordTooShortTipView();
    }
  }

  @Override
  public void showCancelTipView() {
    if (mRecordVoicePopWindow != null) {
      mRecordVoicePopWindow.showCancelTipView();
    }
  }

  @Override
  public void hideTipView() {
    if (mRecordVoicePopWindow != null) {
      mRecordVoicePopWindow.dismiss();
    }
  }

  @Override
  public void updateCurrentVolume(int db) {
    if (mRecordVoicePopWindow != null) {
      mRecordVoicePopWindow.updateCurrentVolume(db);
    }
  }

  @Override
  public void startPlayAnim(int position) {

  }

  @Override
  public void stopPlayAnim() {

  }

  @Override
  protected void onDestroy() {
    SharedPreferencesUtil.setBoolean("is_login", false);
    super.onDestroy();
  }
}