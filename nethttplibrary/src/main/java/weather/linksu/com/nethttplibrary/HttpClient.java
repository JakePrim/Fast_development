package weather.linksu.com.nethttplibrary;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/11 0011
 * 描    述：网络请求客户端,可自己定制（需要继承此类）
 * 修订历史：
 * ================================================
 */
public interface HttpClient {
    /**
     * get请求
     *
     * @param url
     * @param action
     */
    void get(String url, int action);

    /**
     * post 请求
     *
     * @param url
     * @param param
     * @param action
     */
    void post(String url, Map<String, String> param, int action);

    /**
     * 请求回调
     *
     * @param callBack
     */
    void setCallBack(BaseCallback callBack);

    /**
     * 解析json的类
     * @param subclass
     */
    void getSuperclassTypeParameter(Class subclass);
}
