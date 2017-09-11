package weather.linksu.com.nethttplibrary.okhttp;

import okhttp3.Response;
import weather.linksu.com.nethttplibrary.BaseCallback;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/11 0011
 * 描    述：基于基础的回调，OkHttp的回调
 * 修订历史：
 * ================================================
 */
public interface OkCallBack extends BaseCallback {

    /**
     * json 解析异常
     * @param response
     * @param e
     */
    void OnJsonParseError(Response response, Exception e);
}
