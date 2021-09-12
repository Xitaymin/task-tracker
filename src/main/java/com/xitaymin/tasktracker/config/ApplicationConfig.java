package com.xitaymin.tasktracker.config;

import com.xitaymin.tasktracker.dao.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class ApplicationConfig {

    @Bean
    public AtomicLong atomicLong() {
        return new AtomicLong(1);
    }

    @Bean
    public Map<Long, User> userMap() {
        return new HashMap<>();
    }
}
