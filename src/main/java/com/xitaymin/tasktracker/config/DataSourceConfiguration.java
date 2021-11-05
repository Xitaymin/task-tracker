package com.xitaymin.tasktracker.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class DataSourceConfiguration {

    @Bean
    public DataSource dataSource() throws IOException {
        Properties properties = new Properties();
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream("db/datasource.properties")) {
            properties.load(stream);
        }
        HikariConfig config = new HikariConfig(properties);
        return new HikariDataSource(config);
    }
}
