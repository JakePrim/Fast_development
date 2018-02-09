package weather.linksu.com.nethttplibrary;

import android.content.Context;
import android.util.Log;

import java.util.Map;
import java.util.Objects;

import weather.linksu.com.nethttplibrary.okhttp.OkClient;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/11 0011
 * 描    述：网络请求工具类,链式调用Builder 模式
 * 可自己定制不同的网络请求
 * 修订历史：
 * ================================================
 */
public class HttpUtil {

    private static HttpClient mhttpClient;
    private HttpClient httpClient;
    private static String TAG = "HttpUtil";
    private Context context;
    private String url;
    private Map<String, String> param;
    private boolean isCache;
    private HttpMethodType methodType;
    private Class subclass;
    private int action;

    /**
     * 在Application 中初始化网络请求
     *
     * @param httpClient
     */
    public static void initHttpClient(HttpClient httpClient)  {
        mhttpClient = httpClient;
    }

    public HttpUtil() {
    }

    /**
     * 初始化context
     *
     * @param context
     * @return
     */
    public HttpUtil with(Context context) {
        this.context = context;
        return this;
    }

    /**
     * 请求的URL
     *
     * @param url
     * @return
     */
    public HttpUtil url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 请求的参数
     *
     * @param key
     * @param value
     * @return
     */
    public HttpUtil param(String key, String value) {
        this.param.put(key, value);
        return this;
    }

    /**
     * 是否缓存
     *
     * @param isCache
     * @return
     */
    public HttpUtil cache(boolean isCache) {
        this.isCache = isCache;
        return this;
    }

    /**
     * get 方式请求
     *
     * @return
     */
    public HttpUtil get() {
        this.methodType = HttpMethodType.GET;
        return this;
    }

    /**
     * post 方式请求
     *
     * @return
     */
    public HttpUtil post() {
        this.methodType = HttpMethodType.POST;
        return this;
    }

    /**
     * 设置返回的json bean 类
     *
     * @param subclass
     * @return
     */
    public HttpUtil subclass(Class subclass) {
        this.subclass = subclass;
        return this;
    }

    /**
     * 设置返回的标志位，用于多个请求时区分
     *
     * @param action
     * @return
     */
    public HttpUtil action(int action) {
        this.action = action;
        return this;
    }

    /**
     * 若要更换网络请求在此方法中设置网络请求客户端
     *
     * @param httpClient
     */
    public HttpUtil setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    /**
     * 设置完毕执行网络请求
     *
     * @param callback
     */
    public void execute(BaseCallback callback) {
        if (checkHttpClient(httpClient)) {
            if (null != subclass) {
                httpClient.getSuperclassTypeParameter(subclass);
            }
            if (methodType == HttpMethodType.GET) {
                httpClient.get(url, action, callback);
            } else if (methodType == HttpMethodType.POST) {
                httpClient.post(url, param, action, callback);
            }
            httpClient = null;
        }
    }

    /**
     * 检查网络请求客户端，是否为null
     *
     * @param httpClient
     * @return
     */
    private boolean checkHttpClient(HttpClient httpClient) {
        if (null == mhttpClient) {
            this.httpClient = new OkClient();
            Log.e(TAG, "please int Application initHttpClient ，default OkHttp");
        } else if (null == httpClient) {
            this.httpClient = mhttpClient;
        }
        return true;
    }

}
