package com.e.myapplication.utils;

import android.content.Context;

import com.e.myapplication.base.BaseApplication;

public class SharedPreferencesUtil {
  // 只需提供SharedPreferences的文件名，安卓会自动加上.xml后缀

  public static void setString(String key, String value) {
    BaseApplication.getContext().getSharedPreferences("room", Context.MODE_PRIVATE)
      .edit().putString(key, value).apply();
  }

  public static String getString(String key) {
    return BaseApplication.getContext().getSharedPreferences("room", Context.MODE_PRIVATE).getString(key, "");
  }

  public static String getString(String key, String defaultValue) {
    return BaseApplication.getContext().getSharedPreferences("room", Context.MODE_PRIVATE).getString(key, defaultValue);
  }

  public static boolean isLogin() {
    return getBoolean("is_login");
  }

  public static void setBoolean(String key, boolean value) {
    BaseApplication.getContext().getSharedPreferences("room", Context.MODE_PRIVATE)
      .edit().putBoolean(key, value).apply();
  }

  public static boolean getBoolean(String key) {
    return BaseApplication.getContext().getSharedPreferences("room", Context.MODE_PRIVATE)
      .getBoolean(key, false);
  }
}
