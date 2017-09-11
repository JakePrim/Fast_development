package weather.linksu.com.nethttplibrary.okhttp;

import android.os.Handler;
import android.util.Log;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import weather.linksu.com.nethttplibrary.BaseCallback;
import weather.linksu.com.nethttplibrary.GsonUtill;
import weather.linksu.com.nethttplibrary.HttpClient;
import weather.linksu.com.nethttplibrary.HttpMethodType;

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
    private OkHttpClient okHttpClient;
    private OkCallBack callBack;
    private Class type;
    private Handler mHandler;
    private static String TAG = "OkClient";

    public OkClient() {
        okHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = okHttpClient.newBuilder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        mHandler = new Handler();
    }


    @Override
    public void get(String url, int action) {
        Request request = buildRequest(url, HttpMethodType.GET, null);
        doRequest(request, action);
    }

    @Override
    public void post(String url, Map<String, String> param, int action) {
        Request request = buildRequest(url, HttpMethodType.POST, param);
        doRequest(request, action);
    }

    @Override
    public void setCallBack(BaseCallback callBack) {
        this.callBack = (OkCallBack) callBack;
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
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                callBack.onFailure(action, "网络错误,请稍后重试", e);
                callbackFailure(action, "网络错误,请稍后重试", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("doRequest", "onResponse: " + result);
                    try {
                        Object object = GsonUtill.getObejctFromJSON(result, type);
//                        callBack.onResponse(action, object);
                        callbackSuccess(action, object);
                    } catch (Exception e) {
                        callBack.OnJsonParseError(response, e);
                    }
                } else {
//                    callBack.onFailure(action, response.message(), null);
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
        Request.Builder builder = new Request.Builder().url(url);
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
