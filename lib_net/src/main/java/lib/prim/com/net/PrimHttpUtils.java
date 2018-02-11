package lib.prim.com.net;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.ref.WeakReference;

import javax.net.ssl.HostnameVerifier;

import lib.prim.com.net.https.HttpsUtils;
import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import lib.prim.com.net.client.HttpClient;
import lib.prim.com.net.client.OkClient;
import lib.prim.com.net.model.HttpHeaders;
import lib.prim.com.net.model.HttpParams;
import lib.prim.com.net.request.GetRequest;
import lib.prim.com.net.request.PostRequest;
import lib.prim.com.net.utils.Utils;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/11 0011
 * 描    述：网络请求的入口类,链式调用Builder 模式
 * 可自己定制不同的网络请求
 * 修订历史：
 * ================================================
 */
public class PrimHttpUtils {

    private HttpClient httpClient;                      //网络请求客户 默认为Okhttp
    private static String TAG = "PrimHttpUtils";
    private WeakReference<Activity> weakActivity;       //用于获取在哪个Activity执行网络请求
    private boolean isCache;
    private Handler mHandler;                                   //用于在主线程执行的调度器
    private WeakReference<Application> context;                 //全局上下文 WeakReference 弱引用
    private HttpParams mCommonParams;                           //全局公共请求参数
    private HttpHeaders mCommonHeaders;                         //全局公共请求头
    public static final long DEFAULT_MILLISECONDS = 60000;      //默认的超时时间
    public static long REFRESH_TIME = 300;                      //回调刷新时间（单位ms）

    public static PrimHttpUtils getInstance() {
        return PrimHolder.holder;
    }

    private static class PrimHolder {
        private static PrimHttpUtils holder = new PrimHttpUtils();
    }

    PrimHttpUtils() {
        mHandler = new Handler(Looper.getMainLooper());
        httpClient = new OkClient();
    }

    /** 在Application 中初始化网络请求 */
    public PrimHttpUtils init(Application context) {
        this.context = new WeakReference<>(context);
        return this;
    }

    /** 获取全局上下文 */
    public Context getContext() {
        Utils.checkNotNull(context, "please call PrimHttpUtils.getInstance().init() first in application!");
        return context.get();
    }

    /** 初始化context */
    public PrimHttpUtils with(Activity context) {
        this.weakActivity = new WeakReference<>(context);
        return this;
    }

    /** 是否缓存 */
    public PrimHttpUtils cache(boolean isCache) {
        this.isCache = isCache;
        return this;
    }

    /** get 方式请求 */
    public <T> GetRequest<T> get(String url) {
        if (checkHttpClient(httpClient)) {
            return httpClient.get(url);
        }
        return null;
    }

    /** post 方式请求 */
    public <T> PostRequest<T> post(String url) {
        if (checkHttpClient(httpClient)) {
            return httpClient.post(url);
        }
        return null;
    }

    /** 在此方法中设置网络请求客户端 */
    public PrimHttpUtils setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    /** 开始请求 */
    public Call startRequest(Request request) {
        if (checkHttpClient(httpClient)) {
            return (Call) httpClient.startRequest(request);
        }
        return null;
    }

    /** 检查网络请求客户端，是否为null */
    private boolean checkHttpClient(HttpClient httpClient) {
        if (null == httpClient) {
            this.httpClient = new OkClient();
            Log.e(TAG, "please int Application init ，default OkHttp");
        }
        return true;
    }

    /** 获取OkHttpClient */
    public OkHttpClient getOkHttpClient() {
        if (httpClient.getClient() instanceof OkHttpClient) {
            return (OkHttpClient) httpClient.getClient();
        }
        return null;
    }

    /** 链接超时 */
    public PrimHttpUtils connectTimeout(long time) {
        if (checkHttpClient(httpClient)) {
            httpClient.connectTimeout(time);
        }
        return this;
    }

    /** 读取超时 */
    public PrimHttpUtils readTimeout(long time) {
        if (checkHttpClient(httpClient)) {
            httpClient.readTimeout(time);
        }
        return this;
    }

    /** 写入超时 */
    public PrimHttpUtils writeTimeout(long time) {
        if (checkHttpClient(httpClient)) {
            httpClient.writeTimeout(time);
        }
        return this;
    }

    /** 设置拦截 */
    public PrimHttpUtils addInterceptor(Interceptor interceptor) {
        if (checkHttpClient(httpClient)) {
            httpClient.addInterceptor(interceptor);
        }
        return this;
    }

    /** 设置证书 */
    public PrimHttpUtils setSSLParams(HttpsUtils.SSLParams sslParams) {
        if (checkHttpClient(httpClient)) {
            httpClient.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        }
        return this;
    }

    /** 配置https的域名匹配规则 */
    public PrimHttpUtils hostnameVerifier(HostnameVerifier hostnameVerifier) {
        if (checkHttpClient(httpClient)) {
            httpClient.hostnameVerifier(hostnameVerifier);
        }
        return this;
    }

    /** 自动管理cookie（或者叫session的保持） */
    public PrimHttpUtils cookieJar(CookieJar cookieJar) {
        //1. 使用sp保持cookie，如果cookie不过期，则一直有效
        //2. 使用数据库保持cookie，如果cookie不过期，则一直有效
        //3. 使用内存保持cookie，app退出后，cookie消失
        if (checkHttpClient(httpClient)) {
            httpClient.cookieJar(cookieJar);
        }
        return this;
    }

    /** 设置okhttpClient */
    public PrimHttpUtils setOkhttpClient(OkHttpClient.Builder builder) {
        if (checkHttpClient(httpClient)) {
            httpClient.setBuilder(builder);
        }
        return this;
    }

    /** 获取handler */
    public Handler getHandler() {
        return mHandler;
    }

    /** 获取全局的公共参数 */
    public HttpParams getCommonParams() {
        return mCommonParams;
    }

    /** 添加全局公共参数 */
    public PrimHttpUtils addCommonParams(HttpParams params) {
        if (mCommonParams == null) mCommonParams = new HttpParams();
        mCommonParams.put(params);
        return this;
    }

    /** 添加全局公共参数 */
    public PrimHttpUtils addCommonParams(String key, String value) {
        if (mCommonParams == null) mCommonParams = new HttpParams(key, value);
        mCommonParams.put(key, value);
        return this;
    }

    /** 获取全局的请求头 */
    public HttpHeaders getCommonHeaders() {
        return mCommonHeaders;
    }

    /** 添加全局的请求头 */
    public PrimHttpUtils addCommonHeaders(HttpHeaders params) {
        if (mCommonHeaders == null) mCommonHeaders = new HttpHeaders();
        mCommonHeaders.put(params);
        return this;
    }

    /** 添加全局的请求头 */
    public PrimHttpUtils addCommonHeaders(String key, String value) {
        if (mCommonHeaders == null) mCommonHeaders = new HttpHeaders(key, value);
        mCommonHeaders.put(key, value);
        return this;
    }

    /** 根据tag 取消请求 */
    public void cancelTag(Object tag) {
        if (checkHttpClient(httpClient)) {
            httpClient.cancelTag(tag);
        }
    }

    /** 取消所有请求 */
    public void cancelAll() {
        if (checkHttpClient(httpClient)) {
            httpClient.cancelAll();
        }
    }

}
