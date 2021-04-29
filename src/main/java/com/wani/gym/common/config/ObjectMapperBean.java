package com.wani.gym.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperBean {

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
