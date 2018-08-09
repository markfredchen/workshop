package com.cloudhelios.olympus.persistence.sharding.jdbc.service;

import com.cloudhelios.olympus.persistence.sharding.jdbc.config.DataSourceConfiguration;
import com.cloudhelios.olympus.persistence.sharding.jdbc.domain.Tenant;
import liquibase.exception.LiquibaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author markfredchen
 * @date 2018/8/9
 */
@Service
public class TenantService {
    @Autowired
    @Qualifier("commonDataSource")
    private DataSource commonDataSource;

    @Autowired
    private DataSourceConfiguration configuration;

    @Autowired
    @Qualifier("shardingDataSource")
    private DataSource dataSource;


    public void createTenant(Tenant tenant) throws SQLException, LiquibaseException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(commonDataSource);
        jdbcTemplate.execute(String.format("INSERT INTO tenant VALUES(%d, '%s')", tenant.getId(), tenant.getTenantCode()));
        jdbcTemplate.execute(String.format("CREATE SCHEMA `ds_%d` DEFAULT CHARACTER SET utf8;", tenant.getId()));
        configuration.renewShardingDataSource(commonDataSource);
    }

}
