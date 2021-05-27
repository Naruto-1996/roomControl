package com.e.myapplication.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.e.myapplication.R;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;


// 项目启动时会先走这里 所以这个类经常会用来做一下初始化工作 比如初始化数据库对象 一些全局的常量之类的
public class BaseApplication extends Application {


  @SuppressLint("StaticFieldLeak")
  private static Context context;

  public static Context getContext() {
    return context;
  }


  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();

    // 在 Application 中初始化 toast 消息提示
    ToastUtils.init(this);
  }

  //static 代码段可以防止内存泄露 //这一部分是设置全局的下拉刷新样式
  static {
    //设置全局的Header构建器
    SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
      @Override
      public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
        layout.setPrimaryColorsId(R.color.transparent, android.R.color.black);//全局设置主题颜色
        return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
      }
    });
    //设置全局的Footer构建器
    SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
      @Override
      public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
        //指定为经典Footer，默认是 BallPulseFooter
        return new ClassicsFooter(context).setDrawableSize(20);
      }
    });
  }


}
