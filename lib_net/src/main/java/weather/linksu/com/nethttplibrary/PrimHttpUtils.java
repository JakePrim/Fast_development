package weather.linksu.com.nethttplibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import weather.linksu.com.nethttplibrary.client.HttpClient;
import weather.linksu.com.nethttplibrary.client.OkClient;
import weather.linksu.com.nethttplibrary.model.HttpHeaders;
import weather.linksu.com.nethttplibrary.model.HttpParams;
import weather.linksu.com.nethttplibrary.request.GetRequest;
import weather.linksu.com.nethttplibrary.request.PostRequest;
import weather.linksu.com.nethttplibrary.utils.Utils;

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

    public static PrimHttpUtils getInstance() {
        return PrimHolder.holder;
    }

    private static class PrimHolder {
        private static PrimHttpUtils holder = new PrimHttpUtils();
    }

    public PrimHttpUtils() {
        mHandler = new Handler(Looper.getMainLooper());
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
            return httpClient.startRequest(request);
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

    public Handler getHandler() {
        return mHandler;
    }

    /** 获取全局的公共参数 */
    public HttpParams getHttpParams() {
        return mCommonParams;
    }

    /** 添加全局公共参数 */
    public PrimHttpUtils addHttpParams(HttpParams params) {
        if (mCommonParams == null) mCommonParams = new HttpParams();
        mCommonParams.put(params);
        return this;
    }

    /** 添加全局公共参数 */
    public PrimHttpUtils addHttpParams(String key, String value) {
        if (mCommonParams == null) mCommonParams = new HttpParams(key, value);
        mCommonParams.put(key, value);
        return this;
    }

    /** 获取全局的请求头 */
    public HttpHeaders getHeaderParams() {
        return mCommonHeaders;
    }

    /** 添加全局的请求头 */
    public PrimHttpUtils addHeaderParams(HttpHeaders params) {
        if (mCommonHeaders == null) mCommonHeaders = new HttpHeaders();
        mCommonHeaders.put(params);
        return this;
    }

    /** 添加全局的请求头 */
    public PrimHttpUtils addHeaderParams(String key, String value) {
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
