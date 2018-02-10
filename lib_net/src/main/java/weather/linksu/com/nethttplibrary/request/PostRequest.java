package weather.linksu.com.nethttplibrary.request;

import okhttp3.Request;
import okhttp3.RequestBody;
import weather.linksu.com.nethttplibrary.request.base.HasBodyRequest;
import weather.linksu.com.nethttplibrary.utils.HttpMethodType;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/10 0010
 * 描    述：Post请求的实现类
 * 修订历史：
 * ================================================
 */
public class PostRequest<T> extends HasBodyRequest<T, PostRequest<T>> {
    public PostRequest(String url) {
        super(url);
    }

    @Override
    public HttpMethodType getMethod() {
        return HttpMethodType.POST;
    }

    @Override
    public Request generateRequest(RequestBody requestBody) {
        Request.Builder builder = generateRequestBuilder(requestBody);
        return builder.post(requestBody).url(url).tag(tag).build();
    }
}
