package lib.prim.com.net.client;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
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
public class OkClient implements HttpClient<OkHttpClient, Call, Request, Interceptor> {
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    private static String TAG = "OkClient";

    public OkClient() {
        //默认的配置
        okHttpClient = new OkHttpClient();
        builder = okHttpClient.newBuilder();
        builder.addInterceptor(new LoggingInterceptor());//日志拦截器
        builder.connectTimeout(PrimHttpUtils.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.readTimeout(PrimHttpUtils.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(PrimHttpUtils.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.connectionPool(new ConnectionPool(8, 15, TimeUnit.MILLISECONDS));

        //方法一: 默认信任所有证书，不安全有风险
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
        //HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        //配置https的域名匹配规则，不需要就不要加入，使用不当会导致https握手失败
        builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);
    }

    @Override
    public void addInterceptor(Interceptor interceptor) {
        builder.addInterceptor(interceptor);
    }

    @Override
    public void connectTimeout(long time) {
        builder.connectTimeout(time, TimeUnit.MILLISECONDS);
    }

    @Override
    public void readTimeout(long time) {
        builder.readTimeout(time, TimeUnit.MILLISECONDS);
    }

    @Override
    public void writeTimeout(long time) {
        builder.writeTimeout(time, TimeUnit.MILLISECONDS);
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
    public OkHttpClient getClient() {
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

    @Override
    public void sslSocketFactory(SSLSocketFactory sslSocketFactory, X509TrustManager trustManager) {
        builder.sslSocketFactory(sslSocketFactory, trustManager);
    }

    @Override
    public void setBuilder(OkHttpClient.Builder builder) {
        okHttpClient = builder.build();
    }
}
