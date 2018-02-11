package lib.prim.com.net.client;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import lib.prim.com.net.request.GetRequest;
import lib.prim.com.net.request.PostRequest;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/11 0011
 * 描    述：网络请求客户端,可自己定制（需要继承此类）
 * E 表示用的是哪个第三方的网络请求
 * A 表示第三方网络开始请求的回调
 * B 表示第三方的请求类
 * C 表示第三方的拦截类
 * 修订历史：
 * ================================================
 */
public interface HttpClient<E, A, B, C> {

    /** get请求 */
    <T> GetRequest<T> get(String url);

    /** post 请求 */
    <T> PostRequest<T> post(String url);

    /** 获取具体的请求客户端 */
    E getClient();

    /** 开始请求后返回的回调 */
    A startRequest(B request);

    /** 添加拦截 */
    void addInterceptor(C interceptor);

    /** 链接超时设置 */
    void connectTimeout(long time);

    /** 读取超时设置 */
    void readTimeout(long time);

    /** 写入超时设置 */
    void writeTimeout(long time);

    /** 根据tag 取消请求 */
    void cancelTag(Object tag);

    /** 取消所有请求 */
    void cancelAll();

    /** 设置证书 */
    void sslSocketFactory(SSLSocketFactory sslSocketFactory, X509TrustManager trustManager);

    /** 网络请求设置 */
    void setBuilder(OkHttpClient.Builder builder);
}
