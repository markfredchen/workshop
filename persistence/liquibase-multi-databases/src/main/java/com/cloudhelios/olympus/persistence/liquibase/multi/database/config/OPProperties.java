package com.cloudhelios.olympus.persistence.liquibase.multi.database.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author markfredchen
 * @date 2018/8/5
 */
@ConfigurationProperties(prefix = "olympus")
public class OPProperties {
    private final List<DBProperties> list = new ArrayList<>();

    public List<DBProperties> getList() {
        return list;
    }
}
