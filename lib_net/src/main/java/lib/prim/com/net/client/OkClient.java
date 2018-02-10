package lib.prim.com.net.client;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import lib.prim.com.net.PrimHttpUtils;
import lib.prim.com.net.https.HttpsUtils;
import lib.prim.com.net.request.GetRequest;
import lib.prim.com.net.Interceptor.LoggingInterceptor;
import lib.prim.com.net.request.PostRequest;

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
    public <T> PostRequest<T> post(String url) {
        return new PostRequest<>(url);
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
