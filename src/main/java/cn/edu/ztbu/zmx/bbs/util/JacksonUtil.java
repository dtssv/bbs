package cn.edu.ztbu.zmx.bbs.util;

import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @version 1.0
 * @program bbs.JacksonUtil
 * @author zhaomengxin
 * @date 2020/10/17 15:25
 * @Description
 * @since 1.0
 */
@Slf4j
public class JacksonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,Boolean.FALSE);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,Boolean.FALSE);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        OBJECT_MAPPER.configure(SerializationFeature.INDENT_OUTPUT,Boolean.FALSE);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,Boolean.FALSE);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS,Boolean.FALSE);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS,Boolean.FALSE);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING,Boolean.FALSE);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX,Boolean.FALSE);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_ENUM_KEYS_USING_INDEX,Boolean.FALSE);
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(CommonConstant.DATE_TIME_PATTERN)));
        module.addSerializer(LocalDate.class,new LocalDateSerializer(DateTimeFormatter.ofPattern(CommonConstant.DATE_PATTERN)));
        module.addSerializer(LocalTime.class,new LocalTimeSerializer(DateTimeFormatter.ofPattern(CommonConstant.TIME_PATTERN)));
        module.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(CommonConstant.DATE_TIME_PATTERN)));
        module.addDeserializer(LocalDate.class,new LocalDateDeserializer(DateTimeFormatter.ofPattern(CommonConstant.DATE_PATTERN)));
        module.addDeserializer(LocalTime.class,new LocalTimeDeserializer(DateTimeFormatter.ofPattern(CommonConstant.TIME_PATTERN)));
        OBJECT_MAPPER.registerModule(module);
    }

    public static String toJsonString(Object object){
        if(object == null){
            return "";
        }
        if(object instanceof String){
            return object.toString();
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            log.error("json ");
            return null;
        }
    }

    public static <T> T toJavaBean(String jsonStr,Class<T> clazz){
        if(Strings.isNullOrEmpty(jsonStr) || clazz == null){
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(jsonStr,clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T toJavaBean(String jsonStr, TypeReference<T> reference){
        if(Strings.isNullOrEmpty(jsonStr) || reference == null){
            return null;
        }
        try {
            return (T) OBJECT_MAPPER.readValue(jsonStr,reference);
        } catch (Exception e) {
            return null;
        }
    }
}
