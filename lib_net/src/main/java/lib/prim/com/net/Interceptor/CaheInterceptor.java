package lib.prim.com.net.Interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/12 0012
 * 描    述：加入缓存机制 和网络工具类
 * 修订历史：
 * ================================================
 */
public class CaheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        if (NetworkUtil.isNetworkAvailable(context)) {
//            Response response = chain.proceed(request);
//            // read from cache for 60 s
//            int maxAge = 60;
//            String cacheControl = request.cacheControl().toString();
//            Log.e("Tamic", "60s load cahe" + cacheControl);
//            return response.newBuilder()
//                    .removeHeader("Pragma")
//                    .removeHeader("Cache-Control")
//                    .header("Cache-Control", "public, max-age=" + maxAge)
//                    .build();
//        } else {
//            Log.e("Tamic", " no network load cahe");
//            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
//            Response response = chain.proceed(request);
//            //set cahe times is 1 days
//            int maxStale = 60 * 60 * 24 * 1;
//            return response.newBuilder()
//                    .removeHeader("Pragma")
//                    .removeHeader("Cache-Control")
//                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                    .build();
//        }
        return null;
    }
}
