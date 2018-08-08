package com.cloudhelios.olympus.persistence.liquibase.multi.database.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author markfredchen
 * @date 2018/8/5
 */
@ConfigurationProperties(prefix = "op")
public class OPMapProperties {
    private final Map<String, DataSourceProperties> map = new HashMap<>();

    public Map<String, DataSourceProperties> getMap() {
        return this.map;
    }
}
