package com.miyuan.ifat.support.util

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import com.alibaba.fastjson.parser.Feature
import com.alibaba.fastjson.serializer.SerializeConfig
import com.alibaba.fastjson.serializer.SerializerFeature
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import org.apache.commons.lang3.ObjectUtils

class JsonUtil {
    private static JsonSlurper jsonSlurper = new JsonSlurper()
    private static ObjectMapper mapper = new ObjectMapper();

    public static String prettyJson(Object obj) {
        String str = obj.toString()
        if(ObjectUtils.isEmpty(obj)){
            return ""
        }
        if(obj instanceof Map){
            JSONObject json = new JSONObject(obj)
            str = json.toString()
        }
        try {
            Object objMap = mapper.readValue(str,Object.class)
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objMap)
        }catch (Exception e){
            return obj
        }

    }

    public static def strToJson(String str){
        return jsonSlurper.parseText(str)
    }

    public static def strJsonToJson(String str){
        return jsonSlurper.parseText(jsonSlurper.parseText(str) as String)
    }

    public static JSON objToJson(Object str){
        return JSON.parseObject(str)
    }



    public static JSON  objToJsonList(Object str){
        return JSON.parseArray(str)
    }

    private static SerializeConfig mapping = new SerializeConfig();

    static {
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }

    public static String toJsonString(Object obj) {
        return JSON.toJSONString(obj, mapping, new SerializerFeature[0]);
    }

    public static String toJsonStringFilter(Object obj, SimplePropertyPreFilter filter) {
        return JSON.toJSONString(obj, filter, new SerializerFeature[0]);
    }

    public static <T> T jsonToObject(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, clazz);
    }

    public static <T> List<T> jsonToList(String jsonArrayStr, Class<T> clazz) {
        return JSON.parseArray(jsonArrayStr, clazz);
    }

    public static <T> Map<String, Object> jsonToMap(String jsonStr) {
        return (Map)jsonToObject(jsonStr, Map.class);
    }

    public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz) {
        Map<String, T> map = (Map)JSON.parseObject(jsonStr, new TypeReference<Map<String, T>>() {
        }, new Feature[0]);
        Iterator var3 = map.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, T> entry = (Map.Entry)var3.next();
            JSONObject obj = (JSONObject)entry.getValue();
            map.put(entry.getKey(), JSONObject.toJavaObject(obj, clazz));
        }

        return map;
    }


    public static Object objectParse(Object obj){
        if(ObjectUtils.isEmpty(obj)){
            return obj
        }
        try {
            Object json= JSON.parseObject(obj.toString())
            return json
        } catch (Exception e) {
            return obj
        }
    }

    public static boolean isJSON(String str) {
        boolean result = false;
        try {
            Object obj= JSON.parseObject(str.toString());
            result = true;
        } catch (Exception e) {
            result=false;
        }
        return result;
    }
}
