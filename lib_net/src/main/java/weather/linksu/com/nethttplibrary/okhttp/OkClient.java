package weather.linksu.com.nethttplibrary.okhttp;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import weather.linksu.com.nethttplibrary.BaseCallback;
import weather.linksu.com.nethttplibrary.GsonUtill;
import weather.linksu.com.nethttplibrary.HttpClient;
import weather.linksu.com.nethttplibrary.HttpMethodType;
import weather.linksu.com.nethttplibrary.Interceptor.LoggingInerceptor;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/11 0011
 * 描    述：OkHttp 去请求网络
 * 修订历史：
 * ================================================
 */
public class OkClient implements HttpClient {
    private static OkHttpClient okHttpClient;
    private BaseCallback callBack;
    private Class type;
    private Handler mHandler;
    private static String TAG = "OkClient";
    public static String baseUrl = "https://api.douban.com";
    private Call call;

    public OkClient() {
        this(baseUrl, null);
    }

    public OkClient(String baseUrl) {
        this(baseUrl, null);
    }

    public OkClient(String baseUrl, Map<String, String> headers) {
        this.baseUrl = baseUrl;
        okHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = okHttpClient.newBuilder();
        builder.addInterceptor(new LoggingInerceptor());//日志拦截器
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        builder.connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS));
        mHandler = new Handler();
    }

    @Override
    public void get(String url, int action, BaseCallback callBack) {
        this.callBack = callBack;
        Request request = buildRequest(url, HttpMethodType.GET, null);
        doRequest(request, action);
    }

    @Override
    public void post(String url, Map<String, String> param, int action, BaseCallback callBack) {
        this.callBack = callBack;
        Request request = buildRequest(url, HttpMethodType.POST, param);
        doRequest(request, action);
    }

    @Override
    public void getSuperclassTypeParameter(Class subclass) {
        type = subclass;
    }

    /**
     * 开始去请求
     *
     * @param request
     */
    public void doRequest(final Request request, final int action) {
        callBack.onLoadRequest(request);
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackFailure(action, "网络错误,请稍后重试", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("doRequest", "okClient request success ");
                    try {
                        if (null != type){
                            Object object = GsonUtill.getObejctFromJSON(result, type);
                            callbackSuccess(action, object);
                        }else {
                            callbackSuccess(action, result);
                        }
                    } catch (Exception e) {
                        callbackFailure(action, "Json 解析异常", e);
                    }
                } else {
                    callbackFailure(action, response.message(), null);
                }
            }
        });
    }

    /**
     * 需要主要Handler中进行界面更新操作
     *
     * @param action
     * @param object
     */
    private void callbackSuccess(final int action, final Object object) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null)
                    callBack.onResponse(action, object);
                else
                    Log.e(TAG, "run: callBack is null ,please set callBack");
            }
        });
    }

    private void callbackFailure(final int action, final String msg, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null)
                    callBack.onFailure(action, msg, e);
                else
                    Log.e(TAG, "run: callBack is null ,please set callBack");
            }
        });
    }


    /**
     * 返回 Request
     *
     * @param url
     * @param methodType
     * @param param
     * @return
     */
    private Request buildRequest(String url, HttpMethodType methodType, Map<String, String> param) {
        Request.Builder builder = new Request.Builder().url(baseUrl + url);
        if (methodType == HttpMethodType.POST) {
            RequestBody body = builderFormData(param);
            builder.post(body);
        } else if (methodType == HttpMethodType.GET) {
            builder.get();
        }
        return builder.build();
    }

    /**
     * post 需要一个body
     *
     * @param param
     * @return
     */
    private RequestBody builderFormData(Map<String, String> param) {
        FormBody.Builder builder = new FormBody.Builder();
        if (param != null) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }
}
