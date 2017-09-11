package weather.linksu.com.nethttplibrary;

import java.util.Map;

import weather.linksu.com.nethttplibrary.okhttp.OkClient;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/11 0011
 * 描    述：网络请求工具类,此处用单例模式
 * 修订历史：
 * ================================================
 */
public class HttpUtil {

    private static HttpUtil httpUtil;
    private HttpClient httpClient;

    public HttpUtil() {
        httpClient = new OkClient();//默认为OkHttp
    }

    /**
     * 初始化网络请求类
     *
     * @return
     */
    public static HttpUtil getInstance() {
        if (httpUtil == null) {
            synchronized (HttpUtil.class) {
                if (httpUtil == null) {
                    httpUtil = new HttpUtil();
                }
            }
        }
        return httpUtil;
    }

    /**
     * 设置网络请求客户端
     *
     * @param httpClient
     */
    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * get 请求网络
     *
     * @param url
     */
    public void get(String url, int action, Class  cla) {
        httpClient.getSuperclassTypeParameter(cla);
        httpClient.get(url, action);
    }

    /**
     * post 请求网络
     *
     * @param url
     * @param param
     */
    public void post(String url, Map<String, String> param, int action, Class cla) {
        httpClient.getSuperclassTypeParameter(cla);
        httpClient.post(url, param, action);
    }

    /**
     * 监听回调
     *
     * @param callBack
     */
    public void setCallBack(BaseCallback callBack) {
        httpClient.setCallBack(callBack);
    }

}
