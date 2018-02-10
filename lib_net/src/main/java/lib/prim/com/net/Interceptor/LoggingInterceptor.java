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
 * 描    述：日志拦截器
 * 通过chain.request()可以拿到请求对象;
 * 通过chain.proceed(request)可以拿到响应结果.
 * 修订历史：
 * ================================================
 */
public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        long t1 = System.nanoTime();
        Request request = chain.request();

        System.out.println(String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();

        //打印网络请求url 和 请求时间  请求头  返回结果
        System.out.println(String.format("LoggingInterceptor Received response for %s in %.1fms%n%s result %s",
                request.url(), (t2 - t1) / 1e6d, response.headers(),response.body().string()));
        return response;
    }
}
