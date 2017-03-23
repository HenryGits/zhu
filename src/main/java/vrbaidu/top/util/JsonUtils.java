package vrbaidu.top.util;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Json工具类
 *
 */
public class JsonUtils {

    private static Gson gson;

    private JsonUtils() {
    }

    static {
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gson = gb.create();
    }

    public static final String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static final <T> T fromJson(final String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static final <T> T fromJson(final String json, Type t) {
        return gson.fromJson(json, t);
    }

    public static final Map<String, Object> fromJson(final String json) {
        return fromJson(json, Map.class);
    }

    public static final Object fromJson(final Map<String, String> map) {
        return JSONObject.toJSON(map);
    }

}
