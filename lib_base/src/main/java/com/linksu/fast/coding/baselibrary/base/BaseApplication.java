package com.linksu.fast.coding.baselibrary.base;

import android.app.Application;
import android.content.Context;

import com.linksu.fast.coding.baselibrary.utils.PrimLogger;
import com.linksu.fast.coding.baselibrary.utils.Utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import lib.prim.com.net.Interceptor.HttpLoggingInterceptor;
import lib.prim.com.net.PrimHttpUtils;
import lib.prim.com.net.https.HttpsUtils;
import lib.prim.com.net.model.HttpHeaders;
import lib.prim.com.net.model.HttpParams;
import okhttp3.CookieJar;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/6 0006
 * 描    述：
 * 修订历史：
 * ================================================
 */
public abstract class BaseApplication extends Application {

    private static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        initUtils();
        initHttp();
    }

    /** 获取Application */
    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    /** 初始化工具类 */
    private void initUtils() {
        Utils.init(this);
        initLogger();
    }

    /** 初始化logger */
    private void initLogger() {
        //------------------log 设置 ------------------//
        new PrimLogger.Builder()
                .setLogSwitch(isLogSwitch())// 设置log总开关，默认开
                .setGlobalTag(getGlobalTag())// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLog2FileSwitch(isFileSwitch())// 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(isBorderSwitch())// 输出日志是否带边框开关，默认开
                .setLogFilter(PrimLogger.V);// log过滤器，和logcat过滤器同理，默认Verbose
    }

    /** 网络初始化 */
    private void initHttp() {
        //-------------------网络设置初始化---------------------//
        HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
        HttpParams params = new HttpParams();
        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
        PrimHttpUtils.getInstance()
                .init(this)
                .connectTimeout(60_000)
                .readTimeout(50_000)
                .addCommonHeaders(headers)
                .addCommonParams(params)
                .setSSLParams(HttpsUtils.getSslSocketFactory(new SafeTrustManager()))
                .cookieJar(CookieJar.NO_COOKIES)
                .hostnameVerifier(new SafeHostnameVerifier());//初始化网络
    }

    /** log 的总开关 交给子类去决定 */
    protected abstract boolean isLogSwitch();

    /** log 是否保存到文件交给子类去决定 */
    protected abstract boolean isFileSwitch();

    /** log 输出日志是否带边框开关交给子类去决定 */
    protected abstract boolean isBorderSwitch();

    /** log 设置log全局标签交给子类去决定 */
    protected abstract String getGlobalTag();

    /** 认证规则，具体每个业务是否需要验证，以及验证规则是什么 */
    private class SafeTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                for (X509Certificate certificate : chain) {
                    certificate.checkValidity(); //检查证书是否过期，签名是否通过等
                }
            } catch (Exception e) {
                throw new CertificateException(e);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    /**
     * 认证规则，具体每个业务是否需要验证，以及验证规则是什么
     */
    private class SafeHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            //验证主机名是否匹配
            //return hostname.equals("server.jeasonlzy.com");
            return true;
        }
    }
}
