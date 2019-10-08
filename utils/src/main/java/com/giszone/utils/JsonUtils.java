package com.giszone.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class JsonUtils {

    public static Gson getGsonInstance() {
        return new Gson();
    }

    public static Gson getGsonInstanceExclude() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    public static Gson getGsonInstanceForUpdate() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return !f.getName().equals("uuid") && !f.getName().equals("update_time");

                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
    }

    public static JsonElement parseJson(String jsonString) throws JsonSyntaxException {
        return new JsonParser().parse(jsonString);
    }

    /**
     * 参数必须是key，value成对出现，key必须是String类型
     *
     * @param keyValues
     * @return
     */
    public static String toJsonString(Object... keyValues) {
        return toJsonObject(keyValues).toString();
    }

    /**
     * 参数必须是key，value成对出现，key必须是String类型
     *
     * @param object
     * @return
     */
    public static JsonElement objectToJsonElement(Object object) {
        return parseJson(getGsonInstance().toJson(object));
    }

    /**
     * 参数必须是key，value成对出现，key必须是String类型
     *
     * @param keyValues
     * @return
     */
    public static JsonObject toJsonObject(Object... keyValues) {
        JsonObject jsonObject = new JsonObject();
        for (int i = 0; i < keyValues.length; i += 2) {
            String key = (String) keyValues[i];
            Object value = keyValues[i + 1];
            if (value instanceof JsonElement) {
                jsonObject.add(key, (JsonElement) value);
            } else if (value instanceof Number) {
                jsonObject.addProperty(key, (Number) value);
            } else if (value instanceof String) {
                jsonObject.addProperty(key, (String) value);
            } else if (value instanceof Boolean) {
                jsonObject.addProperty(key, (Boolean) value);
            } else if (value instanceof Character) {
                jsonObject.addProperty(key, (Character) value);
            }
        }
        return jsonObject;
    }

    public static Map<String, String> toMap(String... keyValues) {
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            String key = keyValues[i];
            String value = keyValues[i + 1];
            map.put(key, value);
        }
        return map;
    }

    public static RequestBody object2RequestBody(Object o) {
        return json2RequestBody(JsonUtils.getGsonInstance().toJson(o));
    }

    public static RequestBody json2RequestBody(String json) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    }

    public static Map<String, RequestBody> toFormdataBodyMap(String... keyValues) {
        Map<String, RequestBody> res = new HashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            String key = keyValues[i];
            String value = keyValues[i + 1];
            res.put(key, RequestBody.create(MediaType.parse("multipart/form-data"), value));
        }
        return res;
    }

    public static List<MultipartBody.Part> toFormdataPartList(List<String> pathList) {
        List<MultipartBody.Part> partList = new ArrayList<>();
        for (int i = 0; i < pathList.size(); i++) {
            String path = pathList.get(i);
            File file = new File(path);
            String fileName = file.getName();
            MultipartBody.Part part = MultipartBody.Part.createFormData(
                    String.format(Locale.CHINA, "file%d", i + 1),
                    TimeUtils.getTimeStringForFile(System.currentTimeMillis()) + fileName.substring(fileName.lastIndexOf('.')),
                    RequestBody.create(MediaType.parse(guessMimeType(path)), file)
            );
            partList.add(part);
        }
        return partList;
    }

    private static String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
