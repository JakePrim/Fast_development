package lib.prim.com.net.request.base;

import java.io.IOException;

import io.reactivex.annotations.Nullable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import lib.prim.com.net.callback.HttpCallback;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/10 0010
 * 描    述：包装的请求体，处理进度，可以处理任何的 RequestBody，参考 okgo
 * 修订历史：
 * ================================================
 */
public class ProgressRequestBody<T> extends RequestBody {

    private RequestBody requestBody;         //实际的待包装请求体
    private HttpCallback<T> callback;
    private UploadInterceptor interceptor;

    ProgressRequestBody(RequestBody requestBody, HttpCallback<T> callback) {
        this.requestBody = requestBody;
        this.callback = callback;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    /** 重写调用实际的响应体的contentLength */
    @Override
    public long contentLength() {
        try {
            return requestBody.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
//        CountingSink countingSink = new CountingSink(sink);
//        BufferedSink bufferedSink = Okio.buffer(countingSink);
//        requestBody.writeTo(bufferedSink);
//        bufferedSink.flush();
    }

    public interface UploadInterceptor {
//        void uploadProgress(Progress progress);
    }
}
