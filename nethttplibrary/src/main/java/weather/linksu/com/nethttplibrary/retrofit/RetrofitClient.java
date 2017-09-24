package weather.linksu.com.nethttplibrary.retrofit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import weather.linksu.com.nethttplibrary.BaseCallback;
import weather.linksu.com.nethttplibrary.GsonUtill;
import weather.linksu.com.nethttplibrary.HttpClient;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/12 0012
 * 描    述：Retrofit 网络请求
 * 修订历史：
 * ================================================
 */
public class RetrofitClient implements HttpClient {
    private static String TAG = "RetrofitClient";
    private static final int DEFAULT_TIMEOUT = 10;
    private ApiService apiService;
    private static OkHttpClient okHttpClient;
    public static String baseUrl = "https://api.douban.com";
    private static Context mContext;
    private static Retrofit retrofit;
    private Cache cache = null;
    private File httpCacheDirectory;
    private BaseCallback callback;
    private Class subclass;

    public RetrofitClient(Context context) {
        this(context, baseUrl, null);
    }

    public RetrofitClient(Context context, String url) {
        this(context, url, null);
    }

    public RetrofitClient(Context context, String url, Map<String, String> headers) {
        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }
//        if (httpCacheDirectory == null) {
//            httpCacheDirectory = new File(mContext.getCacheDir(), "tamic_cache");
//        }
//
//        try {
//            if (cache == null) {
//                cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
//            }
//        } catch (Exception e) {
//            Log.e("OKHttp", "Could not create http cache", e);
//        }
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .cookieJar(new CookieManger(context)) //
//                .cache(cache)
//                .addInterceptor(new BaseInterceptor(headers))
//                .addInterceptor(new CaheInterceptor(context))
//                .addNetworkInterceptor(new CaheInterceptor(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
        createBaseApi();
    }

    /**
     * create BaseApi  defalte ApiManager
     *
     * @return ApiManager
     */
    public RetrofitClient createBaseApi() {
        apiService = create(ApiService.class);
        return this;
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    @Override
    public void get(String url, final int action, BaseCallback callBack) {
        this.callback = callBack;
        apiService.executeGet(url)
                .compose(RxSchedulers.<ResponseBody>compose())
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        callback.onFailure(action, e.message, e);
                    }

                    @Override
                    public void onQuestComplete() {

                    }

                    @Override
                    public void onQuestResult(@NonNull ResponseBody responseBody) {
                        try {
                            Log.e(TAG, "onQuestResult: retrofit request success");
                            String result = responseBody.string();
                            Object object = GsonUtill.getObejctFromJSON(result, subclass);
                            callback.onResponse(action, object);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStartQuest() {
                        callback.onLoadRequest("");
                    }
                });
    }

    @Override
    public void post(String url, Map<String, String> param, final int action, BaseCallback callBack) {
        this.callback = callBack;
        apiService.executePost(url, param)
                .compose(RxSchedulers.<ResponseBody>compose())
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        callback.onFailure(action, e.message, e);
                    }

                    @Override
                    public void onQuestComplete() {

                    }

                    @Override
                    public void onQuestResult(@NonNull ResponseBody responseBody) {
                        String result = null;
                        try {
                            result = responseBody.string();
                            Object object = GsonUtill.getObejctFromJSON(result, subclass);
                            callback.onResponse(action, responseBody);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStartQuest() {
                        callback.onLoadRequest("");
                    }
                });
    }

    @Override
    public void getSuperclassTypeParameter(Class subclass) {
        this.subclass = subclass;
    }

}
