package com.cloudhelios.olympus.persistence.sharding.jdbc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author markfredchen
 * @date 2018/8/8
 */
@ConfigurationProperties("sharding")
@Data
public class CustomDataSourceProperties {
    private String dbType;

    private Database common;
    private Database base;

    @Data
    public static class Database {
        private String url;
        private String dbType;
        private String username;
        private String password;
    }
}
