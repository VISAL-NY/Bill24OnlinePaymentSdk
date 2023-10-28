package com.bill24.onlinepaymentsdk.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SharePreferenceCustom {

    public static <T> String convertListObjectToJson(List<T> objectList){
        Gson gson=new Gson();
        Type type = new TypeToken<List<T>>() {}.getType();
        return gson.toJson(objectList,type);
    }

    public static <T> List<T> convertFromJsonToListObject(String json,Class<T> typeClass){
        Gson gson=new Gson();
        Type type = TypeToken.getParameterized(List.class, typeClass).getType();
        return gson.fromJson(json, type);
    }

    public static <T> String convertObjectToJson(T object){
        Gson gson=new Gson();
        return gson.toJson(object);
    }

    public static <T> T converJsonToObject(String json,Class<T> object){
        Gson gson=new Gson();
        return gson.fromJson(json,object);
    }
}
