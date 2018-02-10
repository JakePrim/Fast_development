package weather.linksu.com.nethttplibrary.request;

import okhttp3.Request;
import okhttp3.RequestBody;
import weather.linksu.com.nethttplibrary.request.base.NoBodyRequest;
import weather.linksu.com.nethttplibrary.utils.HttpMethodType;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/10 0010
 * 描    述：get 请求
 * 修订历史：
 * ================================================
 */
public class GetRequest<T> extends NoBodyRequest<T, GetRequest<T>> {
    public GetRequest(String url) {
        super(url);
    }

    @Override
    public HttpMethodType getMethod() {
        return HttpMethodType.GET;
    }

    @Override
    public Request generateRequest(RequestBody requestBody) {
        Request.Builder builder = generateRequestBuilder(requestBody);
        return builder.get().url(url).tag(tag).build();
    }
}
