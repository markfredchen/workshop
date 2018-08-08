package com.cloudhelios.olympus.spring.core.configurations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author markfredchen
 * @date 2018/8/5
 */
@Configuration
@EnableConfigurationProperties(ListMapProperties.class)
public class PropertiesConfiguration {
    private final ListMapProperties properties;

    public PropertiesConfiguration(ListMapProperties properties) {
        this.properties = properties;
    }

    @Bean
    public String properties() {
        System.out.println(properties);
        return null;
    }
}
