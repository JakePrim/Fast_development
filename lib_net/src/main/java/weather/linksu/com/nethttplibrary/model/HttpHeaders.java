package weather.linksu.com.nethttplibrary.model;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/9 0009
 * 描    述：参考okgo 的参数封装
 * 修订历史：
 * ================================================
 */
public class HttpHeaders implements Serializable {
    private static final long serialVersionUID = 7369819159227055048L;
    public LinkedHashMap<String, String> commonParams;

    public HttpHeaders() {
        init();
    }

    public HttpHeaders(String key, String value) {
        init();
        put(key, value);
    }

    private void init() {
        commonParams = new LinkedHashMap<>();
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            commonParams.put(key, value);
        }
    }

    public void put(HttpHeaders params) {
        if (params != null) {
            if (params.commonParams != null && !params.commonParams.isEmpty()) {
                commonParams.putAll(params.commonParams);
            }
        }
    }

    public void remove(String key) {
        commonParams.remove(key);
    }

    public void clear() {
        commonParams.clear();
    }
}
