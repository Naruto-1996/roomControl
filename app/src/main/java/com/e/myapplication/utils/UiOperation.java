package com.e.myapplication.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.e.myapplication.R;

/**
 * UI相关的通用操作
 */
public class UiOperation {

  private static final String TAG = "UiOperation";

  private static ProgressDialog loading;

  // TODO 先不考虑不在Ui线程调用showLoading

  /**
   * loading动画相关_showLoading需要在Ui线程中调用
   * 建议在inflater.inflate或setContextView的后一行调用
   */
  public static void showLoading(Context context) {
    if (loading != null) {
      // 防止用户没等loading结束强行离开页面，进入别的页面时loading容易出错
      loading.dismiss();
      loading = null;
    }
    loading = new ProgressDialog(context, R.style.loading_dialog_center);
    loading.setCancelable(false);
    try {
      ((Activity) context).runOnUiThread(() -> loading.show());
    } catch (WindowManager.BadTokenException e) {
      // Unable to add window -- token android.os.BinderProxy@91c87de is not valid; is your activity running?
      Log.w(TAG, Log.getStackTraceString(e));
      loading = null;
    }
  }

  /**
   * loading动画相关_在「Ui线程中」调用closeLoading
   */
  public static void closeLoading() {
    if (loading == null) {
      return;
    }
    // 解决友盟上的bug
    // java.lang.IllegalArgumentException: View=DecorView@27e245a[WebViewActivity] not attached to window manager
    try {
      loading.dismiss();
    } catch (Exception e) {
      Log.i(TAG, "closeLoading: " + e.toString());
    } finally {
      loading = null;
    }
  }
}
