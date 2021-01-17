package cn.edu.ztbu.zmx.bbs.config;

import cn.edu.ztbu.zmx.bbs.common.CommonConstant;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @program bbs.LocalDateTimeSerializerConfig
 * @author: zhaomengxin
 * @date: 2021/1/17 15:25
 * @Description:
 */
@Configuration
public class LocalDateTimeSerializerConfig {
    @Bean
    public LocalDateTimeSerializer localDateTimeDeserializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(CommonConstant.DATE_TIME_PATTERN));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizerLocalDateTime() {
        return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeDeserializer());
    }

    @Bean
    public LocalDateSerializer localDateDeserializer() {
        return new LocalDateSerializer(DateTimeFormatter.ofPattern(CommonConstant.DATE_PATTERN));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizerLocalDate() {
        return builder -> builder.serializerByType(LocalDate.class, localDateDeserializer());
    }

    @Bean
    public LocalTimeSerializer localTimeDeserializer() {
        return new LocalTimeSerializer(DateTimeFormatter.ofPattern(CommonConstant.DATE_TIME_PATTERN));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizerLocalTime() {
        return builder -> builder.serializerByType(LocalTime.class, localTimeDeserializer());
    }
}
