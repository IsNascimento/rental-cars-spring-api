package com.challenge.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    @Value("${rental-cars.props.datetime-format}")
    private String dateTimeFormat;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            JavaTimeModule module = new JavaTimeModule();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);

            module.addSerializer(new LocalDateTimeSerializer(formatter));
            builder.modules(module);
        };
    }
}
