package com.cloudhelios.olympus.security.client.ip.whitelist.config;

import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.plugins.SqlExplainInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author markfredchen
 * @date 2018/8/3
 */
@Configuration
@MapperScan("com.cloudhelios.olympus.security.client.ip.whitelist.persistence.mapper")
public class DatabaseConfiguration {
    @Bean
    public SqlExplainInterceptor sqlExplainInterceptor() {
        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        sqlExplainInterceptor.setStopProceed(true);
        return sqlExplainInterceptor;
    }

    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setFormat(true);
        performanceInterceptor.setMaxTime(200);
        performanceInterceptor.setWriteInLog(false);
        return performanceInterceptor;
    }
}
