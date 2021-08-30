package dev.abelab.timestamp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConvertUtil {

    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

    /**
     * Convert object to json string.
     *
     * @param object object
     *
     * @return string
     */
    public static String convertObjectToJson(final Object object) {
        return gson.toJson(object);
    }

    /**
     * Convert json to object
     *
     * @param <T>   type
     *
     * @param json  json
     *
     * @param clazz clazz
     *
     * @return object
     */
    public static <T> T convertJsonToObject(final String json, final Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

}
