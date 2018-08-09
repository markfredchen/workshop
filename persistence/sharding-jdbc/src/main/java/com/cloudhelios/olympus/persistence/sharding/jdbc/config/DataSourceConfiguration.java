package com.cloudhelios.olympus.persistence.sharding.jdbc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.cloudhelios.olympus.persistence.sharding.jdbc.domain.Tenant;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingjdbc.core.api.config.strategy.NoneShardingStrategyConfiguration;
import io.shardingjdbc.core.api.config.strategy.ShardingStrategyConfiguration;
import io.shardingjdbc.core.jdbc.core.datasource.ShardingDataSource;
import io.shardingjdbc.core.keygen.DefaultKeyGenerator;
import io.shardingjdbc.core.routing.strategy.ShardingStrategy;
import io.shardingjdbc.core.rule.ShardingRule;
import io.shardingjdbc.core.rule.TableRule;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author markfredchen
 * @date 2018/8/8
 */
@Configuration
@EnableConfigurationProperties(CustomDataSourceProperties.class)
public class DataSourceConfiguration {
    private final CustomDataSourceProperties properties;
    private final ApplicationContext context;

    public DataSourceConfiguration(CustomDataSourceProperties properties, ApplicationContext context) {
        this.properties = properties;
        this.context = context;
    }


    private void runLiquibase(DataSource dataSource, String changeLogPath, String tenantId) throws LiquibaseException {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setChangeLog(changeLogPath);
        springLiquibase.setDataSource(dataSource);
        springLiquibase.setShouldRun(true);
        springLiquibase.setResourceLoader(new DefaultResourceLoader());
        if (!StringUtils.isEmpty(tenantId)) {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("tenantId", tenantId);
            springLiquibase.setChangeLogParameters(params);
        }
        springLiquibase.afterPropertiesSet();
    }

    @Bean(name = "shardingDataSource")
    @Qualifier("shardingDataSource")
    @Primary
    public DataSource shardingDataSource() throws SQLException, LiquibaseException {
        DataSource commonDataSource = commonDataSource();
        runLiquibase(commonDataSource, "classpath:db/common/master.xml", null);
        List<Tenant> tenantList = getTenants(commonDataSource);

        ShardingRuleConfiguration configuration = shardingRuleConfiguration(tenantList);
        Map<String, DataSource> shardingDataSources = getDataSourceMapByTenants(tenantList);
        shardingDataSources.put("common", commonDataSource);
        return ShardingDataSourceFactory.createDataSource(shardingDataSources, configuration, new ConcurrentHashMap<>(), new Properties());
    }

    @Bean(name = "commonDataSource")
    @Qualifier("commonDataSource")
    public DataSource dataSource() {
        return commonDataSource();
    }


    private DataSource commonDataSource() {
        DruidDataSource commonDataSource = new DruidDataSource();
        commonDataSource.setUrl(properties.getCommon().getUrl());
        commonDataSource.setDbType(properties.getDbType());
        commonDataSource.setUsername(properties.getCommon().getUsername());
        commonDataSource.setPassword(properties.getCommon().getPassword());
        commonDataSource.setValidationQuery("SELECT 1");
        commonDataSource.setTestWhileIdle(true);
        return commonDataSource;
    }

    public void renewShardingDataSource(DataSource commonDataSource) throws LiquibaseException, SQLException {
        List<Tenant> tenantList = getTenants(commonDataSource);
        Map<String, DataSource> dataSourceMap = getDataSourceMapByTenants(tenantList);
        ShardingRuleConfiguration configuration = shardingRuleConfiguration(tenantList);

        List<TableRule> tableRule = configuration.getTableRuleConfigs().stream().map(t -> t.build(dataSourceMap)).collect(Collectors.toList());
        ShardingRule shardingRule = new ShardingRule(dataSourceMap,
                "common",
                tableRule,
                new ArrayList<>(),
                configuration.getDefaultDatabaseShardingStrategyConfig().build(),
                configuration.getDefaultTableShardingStrategyConfig().build(), new DefaultKeyGenerator());
        context.getBean("shardingDataSource", ShardingDataSource.class)
                .renew(shardingRule, new Properties());
    }

    private Map<String, DataSource> getDataSourceMapByTenants(List<Tenant> tenantList) throws LiquibaseException {
        Map<String, DataSource> shardingDataSources = new ConcurrentHashMap<>();
        for (Tenant tenant : tenantList) {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl(String.format(properties.getBase().getUrl(), tenant.getId()));
            dataSource.setDbType(properties.getDbType());
            dataSource.setUsername(properties.getBase().getUsername());
            dataSource.setPassword(properties.getBase().getPassword());
            dataSource.setValidationQuery("SELECT 1");
            dataSource.setTestWhileIdle(true);
            runLiquibase(dataSource, "classpath:db/sharding/master.xml", tenant.getId().toString());
            shardingDataSources.put("ds_" + tenant.getId(), dataSource);
        }
        return shardingDataSources;
    }

    private List<Tenant> getTenants(DataSource commonDataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(commonDataSource);
        return jdbcTemplate.query("SELECT * FROM tenant", (resultSet, i) -> {
            Tenant tenant = new Tenant();
            tenant.setId(resultSet.getLong("id"));
            tenant.setTenantCode(resultSet.getString("tenant_code"));
            return tenant;
        });
    }

    private ShardingRuleConfiguration shardingRuleConfiguration(List<Tenant> tenantList) {
        String tenantString = tenantList.stream().map(Tenant::getId).collect(Collectors.toList()).toString();
        tenantString = tenantString.replace("[", "['");
        tenantString = tenantString.replaceAll(", ", "', '");
        tenantString = tenantString.replace("]", "']");
        System.out.println(tenantString);
        ShardingRuleConfiguration config = new ShardingRuleConfiguration();
        config.setDefaultDataSourceName("common");
        TableRuleConfiguration userTableRuleConfig = new TableRuleConfiguration();
        userTableRuleConfig.setLogicTable("op_user");
        userTableRuleConfig.setActualDataNodes("ds_${" + tenantString + "}.op_user");
        userTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("tenant_id", "ds_${tenant_id}"));
        userTableRuleConfig.setTableShardingStrategyConfig(new NoneShardingStrategyConfiguration());
        config.getTableRuleConfigs().add(userTableRuleConfig);
        config.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("tenant_id", "ds_${tenant_id}"));
        return config;
    }



}
