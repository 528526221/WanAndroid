package com.xulc.wanandroid.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Date：2017/8/28
 * Desc：Gson工具类
 * Created by xuliangchun.
 */

public class GsonUtil {
    private static final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .create();
    /**
     * 将Json数据解析成相应的映射对象
     * @param jsonData
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        T result = null;
        try {
            result = gson.fromJson(jsonData, type);

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将Json数组解析成相应的映射对象列表
     * @param jsonData
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> parseJsonArrayWithGson(String jsonData, Class<T[]> type) {
        T[] arr = null;
        try {
            arr = gson.fromJson(jsonData, type);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (arr==null){
            return new ArrayList<>();
        }else {
            return new ArrayList<>(Arrays.asList(arr));
        }
    }

    /**
     * 将对象转string
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String beanToJson(T t){
        return gson.toJson(t);
    }


}
