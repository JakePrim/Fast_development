package weather.linksu.com.nethttplibrary;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/11 0011
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GsonUtill {
    public static String setObjectToJSON(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static <T> T getObejctFromJSON(String jsonStr, Class<T> cls) {
        Gson gson = new Gson();
        T t = gson.fromJson(jsonStr, cls);
        return t;
    }

    public static <T> T getTypeFromJSON(String jsonStr, Type type) {
        Gson gson = new Gson();
        T t = gson.fromJson(jsonStr, type);
        return t;
    }

    public static List getListObjectFromJSON(String jsonStr, TypeToken typeToken) {
        Gson gson = new Gson();
        List list = gson.fromJson(jsonStr, typeToken.getType());
        return list;
    }

    public static <T> List<Map<String, Object>> getListMapFromJSON(
            String jsonStr, Class<T> cls) {
        return new Gson().fromJson(jsonStr,
                new TypeToken<List<Map<String, Object>>>() {
                }.getType());
    }
}
