package lib.prim.com.net.request.base;

import android.util.Log;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import lib.prim.com.net.PrimHttp;
import lib.prim.com.net.callback.base.Callback;
import lib.prim.com.net.converter.Converter;
import lib.prim.com.net.model.HttpHeaders;
import lib.prim.com.net.model.HttpParams;
import lib.prim.com.net.utils.HttpMethodType;
import lib.prim.com.net.utils.Utils;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/9 0009
 * 描    述：所有请求的基类，其中泛型 R 主要用于属性设置方法后，返回对应的子类型，以便于实现链式调用
 * 参考 okgo
 * 修订历史：
 * ================================================
 */
public abstract class BaseRequest<T, R extends BaseRequest> implements Serializable {

    protected String url;
    protected Object tag;
    protected int id;
    protected HttpParams params;
    protected HttpHeaders headers;
    //transient 不写入序列化中去
    protected transient okhttp3.Request mRequest;
    protected transient Callback<T> callback;
    protected transient Converter<T> converter;
    protected transient ProgressRequestBody.UploadInterceptor uploadInterceptor;

    public BaseRequest(String url) {
        this.url = url;
        PrimHttp prim = PrimHttp.getInstance();
        if (prim.getCommonParams() != null) {
            params = prim.getCommonParams();
        } else {
            params = new HttpParams();
        }
        if (prim.getCommonHeaders() != null) {
            headers = prim.getCommonHeaders();
        } else {
            headers = new HttpHeaders();
        }
    }

    @SuppressWarnings("unchecked")
    public R tag(Object tag) {
        this.tag = tag;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R url(String url) {
        this.url = url;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R id(int id) {
        this.id = id;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R converter(Converter<T> converter) {
        Utils.checkNotNull(converter, "converter == null");
        this.converter = converter;
        return (R) this;
    }

    public R setUploadInterceptor(ProgressRequestBody.UploadInterceptor interceptor) {
        Utils.checkNotNull(interceptor, "UploadInterceptor == null");
        this.uploadInterceptor = interceptor;
        return (R) this;
    }

    public Converter<T> getConverter() {
        // converter 优先级高于 callback
        if (converter == null) converter = callback;
        Utils.checkNotNull(converter, "converter == null, do you forget to call Request#converter(Converter<T>) ?");
        return converter;
    }

    /** 请求方式 */
    public abstract HttpMethodType getMethod();

    /** 获取参数 */
    public HttpParams getParams() {
        return params;
    }

    /** 添加参数 */
    @SuppressWarnings("unchecked")
    public R params(HttpParams params) {
        Utils.checkNotNull(params, "params == null");
        this.params.put(params);
        return (R) this;
    }

    /** 添加参数 */
    @SuppressWarnings("unchecked")
    public R params(String key, String value) {
        params.put(key, value);
        return (R) this;
    }

    /** 删除某个参数 */
    @SuppressWarnings("unchecked")
    public R removeParams(String key) {
        params.remove(key);
        return (R) this;
    }

    /** 删除所有参数 */
    @SuppressWarnings("unchecked")
    public R removeAllParams() {
        params.clear();
        return (R) this;
    }

    /** 获取全局的请求头 */
    public HttpHeaders getHeader() {
        return headers;
    }

    /** 添加全局的请求头 */
    public R headers(HttpHeaders headers) {
        this.headers.put(headers);
        return (R) this;
    }

    /** 添加全局公共参数 */
    public R headers(String key, String value) {
        headers.put(key, value);
        return (R) this;
    }

    public String getUrl() {
        return url;
    }

    public Object getTag() {
        return tag;
    }

    public Request getRequest() {
        return mRequest;
    }

    public void setCallback(Callback<T> callback) {
        this.callback = callback;
    }

    /** 根据不同的请求方式和参数，生成不同的RequestBody */
    protected abstract RequestBody generateRequestBody();

    /** 根据不同的请求方式，将RequestBody转换成Request对象 */
    public abstract okhttp3.Request generateRequest(RequestBody requestBody);


    /** 获取okhttp的同步call对象 */
    public okhttp3.Call getRawCall() {
        //构建请求体，返回call对象
        RequestBody requestBody = generateRequestBody();
        if (requestBody != null) {
            ProgressRequestBody<T> progressRequestBody = new ProgressRequestBody<>(requestBody, callback);
            progressRequestBody.setInterceptor(uploadInterceptor);
            mRequest = generateRequest(progressRequestBody);
        } else {
            mRequest = generateRequest(null);
        }
        return PrimHttp.getInstance().startRequest(mRequest);
    }

    /** 同步调用，阻塞方法，同步请求执行 */
    public Response execute() throws IOException {
        return getRawCall().execute();
    }

    /** 异步调用,非阻塞方法,但是回调在子线程中执行 */
    public void enqueue(final Callback<T> callback) {
        Utils.checkNotNull(callback, "callback == null");
        this.callback = callback;
        callback.onStart(this, id);
        getRawCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                failure(call, e, callback);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                response(call, response, callback);
            }
        });
    }

    /** 将请求到的错误信息 post 到主线程中去 用于界面UI的反馈 */
    private void failure(final Call call, final IOException e, final Callback<T> callback) {
        Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, id);
                callback.onFinish(id);
            }
        });
    }

    private static final String TAG = "BaseRequest";
    /** 将请求到的结果 post 到主线程中去 */
    private void response(final Call call, final Response response, final Callback<T> callback) {
        try {
            callback.convertResponse(response, id);//注意:此处在子线程中执行数据的解析与转换 不能进行UI的操作
        } catch (Throwable throwable) {
            Log.e(TAG, "response: " + "数据转换异常");
            Utils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callback.onError(call, null, id);
                    callback.onFinish(id);
                }
            });
        }
    }

}
