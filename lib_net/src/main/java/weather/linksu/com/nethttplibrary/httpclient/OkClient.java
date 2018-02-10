package weather.linksu.com.nethttplibrary.httpclient;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import weather.linksu.com.nethttplibrary.PrimHttpUtils;
import weather.linksu.com.nethttplibrary.callback.HttpCallback;
import weather.linksu.com.nethttplibrary.https.HttpsUtils;
import weather.linksu.com.nethttplibrary.request.GetRequest;
import weather.linksu.com.nethttplibrary.utils.GsonUtill;
import weather.linksu.com.nethttplibrary.utils.HttpMethodType;
import weather.linksu.com.nethttplibrary.Interceptor.LoggingInterceptor;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/11 0011
 * 描    述：OkHttp 去请求网络
 * 修订历史：
 * ================================================
 */
public class OkClient implements HttpClient {
    private OkHttpClient okHttpClient;
    private static String TAG = "OkClient";

    public OkClient() {
        //默认的配置
        okHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = okHttpClient.newBuilder();
        builder.addInterceptor(new LoggingInterceptor());//日志拦截器
        builder.connectTimeout(PrimHttpUtils.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.readTimeout(PrimHttpUtils.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(PrimHttpUtils.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.connectionPool(new ConnectionPool(8, 15, TimeUnit.MILLISECONDS));

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);

    }


    @Override
    public <T> GetRequest<T> get(String url) {
        return new GetRequest<>(url);
    }

    @Override
    public void post(String url) {

    }

    @Override
    public Object getClient() {
        return okHttpClient;
    }

    @Override
    public Call startRequest(Request request) {
        return okHttpClient.newCall(request);
    }

    @Override
    public void cancelTag(Object tag) {
        if (okHttpClient == null || tag == null) return;
        for (Call call : okHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }

        for (Call call : okHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    @Override
    public void cancelAll() {
        for (Call call : okHttpClient.dispatcher().queuedCalls()) {
            call.cancel();
        }

        for (Call call : okHttpClient.dispatcher().runningCalls()) {
            call.cancel();
        }
    }
}
