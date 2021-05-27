package com.e.myapplication.network;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.e.myapplication.ui.LoginActivity;
import com.e.myapplication.utils.SharedPreferencesUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * okhttp连接池单例
 **/
public class OkHttp3 {

  private static final String TAG = "HttpUtils";
  private static volatile OkHttp3 instance;
  public Handler handler = new Handler();
  private final OkHttpClient client;

  private Interceptor getAppInterceptor() {
    Interceptor interceptor = new Interceptor() {
      @Override
      public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.e("++++++", "拦截前");
        Response response = chain.proceed(request);
        Log.e("++++++", "拦截后");
        //请求之后
        return response;
      }
    };
    return interceptor;
  }

  private OkHttp3() {
//    File file = new File(Environment.getExternalStorageDirectory(), "cacheApp");
    client = new OkHttpClient().newBuilder()
      .readTimeout(3000, TimeUnit.SECONDS)
      .connectTimeout(3000, TimeUnit.SECONDS)
      .addInterceptor(getAppInterceptor())
      //.sslSocketFactory(createSSLSocketFactory())
      .hostnameVerifier(new TrustAllHostnameVerifier())
//      .cache(new Cache(file, 10 * 1024))
      .addInterceptor(new UserAgentInterceptor(getUserAgent())) // 请求头拦截器
      .build();
  }

  //单例OkHttp
  public static OkHttp3 getInstance() {
    if (instance == null) {
      synchronized (OkHttp3.class) {
        if (null == instance) {
          instance = new OkHttp3();
        }
      }
    }
    return instance;
  }

  public void doGet(String url, final NetCallBack netCallBack) {
    final Request request = new Request.Builder()
      .get()
      .url(url)
      .build();
    //创建一个call对象
    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, final IOException e) {
        handler.post(new Runnable() {
          @Override
          public void run() {
            netCallBack.onFailure(e);
          }
        });
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        handler.post(new Runnable() {
          @Override
          public void run() {
            netCallBack.onSuccess(result);
          }
        });
      }
    });
  }

  public void doPost(String url, Map<String, String> params, final NetCallBack netCallBack) {
    FormBody.Builder body = new FormBody.Builder();
    for (String key : params.keySet()) {
      body.add(key, params.get(key));
    }
    Request request = new Request.Builder()
      .url(url)
      .post(body.build())
      .build();
    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        netCallBack.onFailure(e);
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        handler.post(new Runnable() {
          @Override
          public void run() {
            netCallBack.onSuccess(result);
          }
        });
      }
    });

  }


  public void doFile(String url, String filePath, final NetCallBack netCallBack) {

    File file = new File(filePath);
    RequestBody requestBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
    MultipartBody multipartBody = new MultipartBody.Builder()
      .setType(MultipartBody.FORM)
//      .addFormDataPart("param1", "value1")
//      .addFormDataPart("param2", "value2")
//      .addFormDataPart("param3", "value3")
      .addFormDataPart("file", file.getName(), requestBody)
      .build();
    Request request = new Request.Builder()
      .url(url)
      .post(multipartBody)
      .build();
    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        netCallBack.onFailure(e);
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        handler.post(new Runnable() {
          @Override
          public void run() {
            netCallBack.onSuccess(result);
          }
        });
      }
    });

  }

  public interface NetCallBack {
    void onSuccess(String data);

    void onFailure(Exception e);
  }


  // UserAgent 过滤器 只有通过这个方式才能改变http的user-agent
  public static class UserAgentInterceptor implements Interceptor {
    private final String mUserAgent;

    public UserAgentInterceptor(String userAgent) {
      mUserAgent = userAgent;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
      Request request = chain.request()
        .newBuilder()
        //.header("User-Agent", mUserAgent)
        .header("token", SharedPreferencesUtil.getString("token"))
        .build();
      Log.i("=====>>>>>>>>>>>>", SharedPreferencesUtil.getString("token"));
      return chain.proceed(request);
    }
  }

  /**
   * 返回正确的UserAgent
   *
   * @return
   */
  public static String getUserAgent() {
    String userAgent = "";
    StringBuffer sb = new StringBuffer();
    userAgent = System.getProperty("http.agent");//Dalvik/2.1.0 (Linux; U; Android 6.0.1; vivo X9L Build/MMB29M)

    for (int i = 0, length = userAgent.length(); i < length; i++) {
      char c = userAgent.charAt(i);
      if (c <= '\u001f' || c >= '\u007f') {
        sb.append(String.format("\\u%04x", (int) c));
      } else {
        sb.append(c);
      }
    }

    Log.i("User-Agent", "User-Agent: " + sb.toString());
    return sb.toString();
  }


  /**
   * 默认信任所有的证书
   * TODO 最好加上证书认证，主流App都有自己的证书
   *
   * @return
   */
  @SuppressLint("TrulyRandom")
  private static SSLSocketFactory createSSLSocketFactory() {
    SSLSocketFactory sSLSocketFactory = null;
    try {
      SSLContext sc = SSLContext.getInstance("TLS");
      sc.init(null, new TrustManager[]{new TrustAllManager()},
        new SecureRandom());
      sSLSocketFactory = sc.getSocketFactory();
    } catch (Exception e) {
    }
    return sSLSocketFactory;
  }

  private static class TrustAllManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
      throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
      throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
      return new X509Certificate[0];
    }
  }

  private static class TrustAllHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
      return true;
    }
  }

}
