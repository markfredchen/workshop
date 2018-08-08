package com.cloudhelios.olympus.persistence.liquibase.multi.database.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author markfredchen
 * @date 2018/8/5
 */
@Configuration
@EnableConfigurationProperties({OPProperties.class, LiquibaseProperties.class, OPMapProperties.class})
public class LiquibaseConfiguration {
    private final OPProperties properties;
    private final LiquibaseProperties liquibaseProperties;
    private final OPMapProperties mapProperties;


    public LiquibaseConfiguration(OPProperties properties, LiquibaseProperties liquibaseProperties, OPMapProperties mapProperties) {
        this.properties = properties;
        this.liquibaseProperties = liquibaseProperties;
        this.mapProperties = mapProperties;
    }

    @Bean
    public List<DataSource> dataSources() {
        System.out.println(mapProperties);
        List<DataSource> dataSources = new ArrayList<>();
        for (DBProperties dbProperties : properties.getList()) {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl(dbProperties.getUrl());
            dataSource.setUsername(dbProperties.getUsername());
            dataSource.setPassword(dbProperties.getPassword());
            dataSources.add(dataSource);
        }
        return dataSources;
    }



    @Bean
    public MultiDataSourceSpringLiquibase springLiquibase() throws Exception {
        MultiDataSourceSpringLiquibase springLiquibase = new MultiDataSourceSpringLiquibase(dataSources(), liquibaseProperties.getChangeLog());
        springLiquibase.afterPropertiesSet();
        return springLiquibase;
    }

}
