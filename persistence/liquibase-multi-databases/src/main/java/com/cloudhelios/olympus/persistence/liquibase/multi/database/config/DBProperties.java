package com.cloudhelios.olympus.persistence.liquibase.multi.database.config;

import lombok.Data;

/**
 * @author markfredchen
 * @date 2018/8/5
 */
@Data
public class DBProperties {
    private String url;
    private String username;
    private String password;
    private String type;
}
