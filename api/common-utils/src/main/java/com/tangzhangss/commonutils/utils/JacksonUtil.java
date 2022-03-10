package com.tangzhangss.commonutils.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Jackson工具类
 */
public class JacksonUtil {
    private final static ObjectMapper objectMapper = JsonMapper
            .builder()
            .enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS)
            .enable(JsonReadFeature.ALLOW_JAVA_COMMENTS) //允许解析注释
            .enable(JsonReadFeature.ALLOW_SINGLE_QUOTES) //允许单引号引号引起的key
            .enable(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES) //允许非双引号引起的key
            .build();



    private JacksonUtil() {

    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    /**
     * javaBean、列表数组转换为json字符串
     */
    public static String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * javaBean、列表数组转换为json字符串,忽略空值
     */
    public static String toJsonIgnoreNull(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    /**
     * json 转JavaBean
     */

    public static <T> T toBean(String jsonString, Class<T> clazz) throws JsonProcessingException {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper.readValue(jsonString, clazz);
    }

    /**
     * json字符串转换为map
     */
    public static Map<String, Object> toMap(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, Map.class);
    }

    /**
     *转JavaBean
     */
    public static T toBean(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }


    /**
     * 获取泛型的Collection Type
     * @param collectionClass 泛型的Collection
     * @param elementClasses 元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
