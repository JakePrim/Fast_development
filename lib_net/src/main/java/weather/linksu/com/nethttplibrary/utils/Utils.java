package weather.linksu.com.nethttplibrary.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import weather.linksu.com.nethttplibrary.PrimHttpUtils;
import weather.linksu.com.nethttplibrary.model.HttpHeaders;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/9 0009
 * 描    述：工具类
 * 修订历史：
 * ================================================
 */
public class Utils {
    /** 检查是否为空 */
    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    /** 在主线程中运行 */
    public static void runOnUiThread(Runnable runnable) {
        PrimHttpUtils.getInstance().getHandler().post(runnable);
    }

    /** 拼接URL get */
    public static String createUrlFromParams(String url, Map<String, String> params) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            if (url.indexOf('&') > 0 || url.indexOf('?') > 0) sb.append("&");
            else sb.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                String encode = URLEncoder.encode(value, "utf-8");
                sb.append(key).append("=").append(encode);
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    /** 通用的拼接请求头 */
    public static Request.Builder appendHeaders(Request.Builder builder, HttpHeaders headers) {
        if (headers.commonParams.isEmpty()) return builder;
        Headers.Builder headerBuilder = new Headers.Builder();
        try {
            for (Map.Entry<String, String> entry : headers.commonParams.entrySet()) {
                //对头信息进行 utf-8 编码,防止头信息传中文,这里暂时不编码,可能出现未知问题,如有需要自行编码
//                String headerValue = URLEncoder.encode(entry.getValue(), "UTF-8");
                headerBuilder.add(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.headers(headerBuilder.build());
        return builder;
    }
}
