package lib.prim.com.net.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import lib.prim.com.net.utils.Utils;
import okhttp3.MediaType;
import okhttp3.internal.Util;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/9 0009
 * 描    述：参考okgo 的参数封装
 * 修订历史：
 * ================================================
 */
public class HttpParams implements Serializable {
    private static final long serialVersionUID = 7369819159227055048L;

    public static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    public LinkedHashMap<String, String> commonParams;

    /** 文件的键值对参数 */
    public LinkedHashMap<String, List<FileWrapper>> fileParamsMap;

    public HttpParams() {
        init();
    }

    public HttpParams(String key, String value) {
        init();
        put(key, value);
    }

    public HttpParams(String key, File file) {
        init();
        put(key, file);
    }

    private void init() {
        commonParams = new LinkedHashMap<>();
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            commonParams.put(key, value);
        }
    }

    public void put(HttpParams params) {
        if (params != null) {
            if (params.commonParams != null && !params.commonParams.isEmpty()) {
                commonParams.putAll(params.commonParams);
            }
            if (params.fileParamsMap != null && !params.fileParamsMap.isEmpty()) {
                fileParamsMap.putAll(params.fileParamsMap);
            }
        }
    }

    public void put(String key, File file) {
        if (key != null && file != null) {
            List<FileWrapper> fileWrappers = fileParamsMap.get(key);
            if (fileWrappers == null) {
                fileWrappers = new ArrayList<>();
                fileParamsMap.put(key, fileWrappers);
            }
            fileWrappers.add(new FileWrapper(file, file.getName(), Utils.guessMimeType(file.getName())));
        }
    }

    public void put(String key, FileWrapper fileWrapper) {
        if (key != null && fileWrapper != null) {
            put(key, fileWrapper.file);
        }
    }

    public void putFiles(String key, List<File> files) {
        if (key != null && !files.isEmpty()) {
            for (File file : files) {
                put(key, file);
            }
        }
    }

    public void putFileWrapper(String key, List<FileWrapper> fileWrappers) {
        if (key != null && fileWrappers != null && !fileWrappers.isEmpty()) {
            for (FileWrapper fileWrapper :
                    fileWrappers) {
                put(key, fileWrapper);
            }
        }
    }


    public void remove(String key) {
        commonParams.remove(key);
        fileParamsMap.remove(key);
    }

    public void clear() {
        commonParams.clear();
        fileParamsMap.clear();
    }
}
