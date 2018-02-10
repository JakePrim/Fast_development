package weather.linksu.com.nethttplibrary.request.base;

import okhttp3.Request;
import okhttp3.RequestBody;
import weather.linksu.com.nethttplibrary.utils.HttpMethodType;
import weather.linksu.com.nethttplibrary.utils.Utils;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/10 0010
 * 描    述：有RequestBody post 方式请求数据
 * 修订历史：
 * ================================================
 */
public abstract class HasBodyRequest<T, R extends HasBodyRequest> extends BaseRequest<T, R> {

    protected boolean isMultipart = false;  //是否强制使用 multipart/form-data 表单上传 键值对上传参数
    protected boolean isSpliceUrl = false;  //是否拼接url参数

    public HasBodyRequest(String url) {
        super(url);
    }

    @SuppressWarnings("unchecked")
    public R isMultipart(boolean isMultipart) {
        this.isMultipart = isMultipart;
        return (R) this;
    }

    @Override
    protected RequestBody generateRequestBody() {
        return Utils.generateMultipartRequestBody(params, isMultipart);
    }

    protected okhttp3.Request.Builder generateRequestBuilder(RequestBody requestBody) {
        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();
        return Utils.appendHeaders(requestBuilder, headers);
    }
}
