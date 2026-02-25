package com.manpibrook.backend_api.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        // Chỉ khởi tạo ObjectMapper thuần túy
        return new ObjectMapper();
    }
}