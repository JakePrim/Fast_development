package weather.linksu.com.nethttplibrary.model;

import okhttp3.Call;
import okhttp3.Headers;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/10 0010
 * 描    述：响应体的包装类
 * 修订历史：
 * ================================================
 */
public final class Response<T> {
    private T body;
    private Throwable throwable;
    //    private boolean isFromCache;
    private okhttp3.Call rawCall;
    private okhttp3.Response rawResponse;

    public Response() {
    }

    public static <T> Response<T> success(T body, Call call, okhttp3.Response rawResponse) {
        Response<T> response = new Response<>();
        response.setBody(body);
        response.setRawCall(call);
        response.setRawResponse(rawResponse);
        return response;
    }

    public static <T> Response<T> error(T body, Call call, okhttp3.Response rawResponse, Throwable throwable) {
        Response<T> response = new Response<>();
        response.setBody(body);
        response.setRawCall(call);
        response.setRawResponse(rawResponse);
        response.setThrowable(throwable);
        return response;
    }

    public int code() {
        if (rawResponse == null) return -1;
        return rawResponse.code();
    }

    public String message() {
        if (rawResponse == null) return null;
        return rawResponse.message();
    }

    public Headers headers() {
        if (rawResponse == null) return null;
        return rawResponse.headers();
    }

    public boolean isSuccessful() {
        return throwable == null;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Call getRawCall() {
        return rawCall;
    }

    public void setRawCall(Call rawCall) {
        this.rawCall = rawCall;
    }

    public okhttp3.Response getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(okhttp3.Response rawResponse) {
        this.rawResponse = rawResponse;
    }
}
