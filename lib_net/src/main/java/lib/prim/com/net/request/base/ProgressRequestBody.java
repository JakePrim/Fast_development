package lib.prim.com.net.request.base;

import java.io.IOException;

import io.reactivex.annotations.Nullable;
import lib.prim.com.net.model.Progress;
import lib.prim.com.net.utils.Utils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import lib.prim.com.net.callback.Callback;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

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
    private Callback<T> callback;
    private UploadInterceptor interceptor;

    ProgressRequestBody(RequestBody requestBody, Callback<T> callback) {
        this.requestBody = requestBody;
        this.callback = callback;
    }

    /** 重写调用实际的响应体的contentType */
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

    /** 重写进行写入 获取上传进度 */
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    /** 具体上传写入进度的实现 */
    private final class CountingSink extends ForwardingSink {

        private Progress progress;

        public CountingSink(Sink delegate) {
            super(delegate);
            progress = new Progress();
            progress.totalSize = contentLength();
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            //byteCount 写入当前的字节
            Progress.changeProgress(progress, byteCount, new Progress.Action() {
                @Override
                public void call(Progress progress) {
                    if (interceptor != null) {
                        interceptor.uploadProgress(progress);
                    } else {
                        onProgress(progress);
                    }
                }
            });
        }
    }

    private void onProgress(final Progress progress) {
        Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.uploadProgress(progress);
                }
            }
        });
    }

    public void setInterceptor(UploadInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public interface UploadInterceptor {
        void uploadProgress(Progress progress);
    }
}
