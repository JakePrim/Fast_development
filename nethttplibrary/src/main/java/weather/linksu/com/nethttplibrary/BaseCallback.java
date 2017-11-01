package weather.linksu.com.nethttplibrary;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/11 0011
 * 描    述：基础网络请求回调
 * 修订历史：
 * ================================================
 */
public interface BaseCallback {

    /**
     * 加载网络数据成功前的处理
     *
     * @param request
     */
    void onLoadRequest(Object request);

    /**
     * 请求错误的处理
     *
     * @param action
     * @param data
     * @param e
     */
    void onFailure(int action, Object data, Exception e);

    /**
     * 请求成功时
     *
     * @param action
     * @param data
     */
    void onResponse(int action, Object data);

}
