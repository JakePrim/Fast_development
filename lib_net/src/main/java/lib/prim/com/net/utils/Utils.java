package lib.prim.com.net.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import lib.prim.com.net.model.FileWrapper;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import lib.prim.com.net.PrimHttp;
import lib.prim.com.net.model.HttpHeaders;
import lib.prim.com.net.model.HttpParams;

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
        PrimHttp.getInstance().getHandler().post(runnable);
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

    /** 生成类似表单的请求体 */
    public static RequestBody generateMultipartRequestBody(HttpParams params, boolean isMultipart) {
        if (params.commonParams.isEmpty() && !isMultipart) {//上传普通的键值对
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : params.commonParams.entrySet()) {
                bodyBuilder.addEncoded(entry.getKey(), entry.getValue());
            }
            return bodyBuilder.build();
        } else {
            //表单提交，有文件
            MultipartBody.Builder multipartBodybuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            //拼接键值对
            if (!params.commonParams.isEmpty()) {
                for (Map.Entry<String, String> entry : params.commonParams.entrySet()) {
                    multipartBodybuilder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }
            //拼接文件
            for (Map.Entry<String, List<FileWrapper>> entry : params.fileParamsMap.entrySet()) {
                List<FileWrapper> fileValues = entry.getValue();
                for (FileWrapper fileWrapper : fileValues) {
                    RequestBody fileBody = RequestBody.create(fileWrapper.mediaType, fileWrapper.file);
                    multipartBodybuilder.addFormDataPart(entry.getKey(), fileWrapper.fileName, fileBody);
                }
            }
            return multipartBodybuilder.build();
        }
    }

    /** 根据文件名获取MIME类型 */
    public static MediaType guessMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        fileName = fileName.replace("#", "");   //解决文件名中含有#号异常的问题
        String contentType = fileNameMap.getContentTypeFor(fileName);
        if (contentType == null) {
            return HttpParams.MEDIA_TYPE_STREAM;
        }
        return MediaType.parse(contentType);
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

    /** 根据请求的URL 命名文件名 */
    public static String getNetFileName(String url) {
        String fileName = null;
        if (TextUtils.isEmpty(fileName)) fileName = getUrlFileName(url);
        if (TextUtils.isEmpty(fileName)) fileName = "unknowfile_" + System.currentTimeMillis();
        try {
            URLDecoder.decode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static String getUrlFileName(String url) {
        String fileName = null;
        String[] strings = url.split("/");
        for (String str : strings) {
            if (str.contains("?")) {
                int endIndex = str.indexOf("?");
                if (endIndex != -1) {
                    fileName = str.substring(0, endIndex);
                    return fileName;
                }
            }
        }
        if (strings.length > 0) {
            fileName = strings[strings.length - 1];
        }
        return fileName;
    }
}
