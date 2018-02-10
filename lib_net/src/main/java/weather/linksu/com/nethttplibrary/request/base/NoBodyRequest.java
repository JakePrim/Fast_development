package weather.linksu.com.nethttplibrary.request.base;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import weather.linksu.com.nethttplibrary.model.HttpHeaders;
import weather.linksu.com.nethttplibrary.utils.HttpMethodType;
import weather.linksu.com.nethttplibrary.utils.Utils;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/9 0009
 * 描    述：没有RequestBody GET 方式请求网络
 * 修订历史：
 * ================================================
 */
public abstract class NoBodyRequest<T, R extends NoBodyRequest> extends BaseRequest<T, R> {
    private static final long serialVersionUID = 1200621102761691196L;

    public NoBodyRequest(String url) {
        super(url);
    }

    @Override
    protected RequestBody generateRequestBody() {
        return null;
    }

    protected okhttp3.Request.Builder generateRequestBuilder(RequestBody requestBody) {
        url = Utils.createUrlFromParams(url, params.commonParams);
        Request.Builder builder = new Request.Builder();
        return Utils.appendHeaders(builder, headers);
    }

}
