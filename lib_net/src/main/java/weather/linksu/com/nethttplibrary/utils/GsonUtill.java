package weather.linksu.com.nethttplibrary.utils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

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
        return create().toJson(obj);
    }

    public static <T> T getObejctFromJSON(String jsonStr, Class<T> cls) {
        T t = create().fromJson(jsonStr, cls);
        return t;
    }

    public static <T> T getTypeFromJSON(String jsonStr, Type type) {
        T t = create().fromJson(jsonStr, type);
        return t;
    }

    public static List getListObjectFromJSON(String jsonStr, TypeToken typeToken) {
        List list = create().fromJson(jsonStr, typeToken.getType());
        return list;
    }

    private static Gson create() {
        return GsonUtill.GsonHolder.gson;
    }

    private static class GsonHolder {
        private static Gson gson = new Gson();
    }


    public static <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return create().fromJson(reader, typeOfT);
    }

    public static <T> List<Map<String, Object>> getListMapFromJSON(
            String jsonStr, Class<T> cls) {
        return new Gson().fromJson(jsonStr,
                new TypeToken<List<Map<String, Object>>>() {
                }.getType());
    }
}
