package com.cloudhelios.olympus.spring.core.configurations;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author markfredchen
 * @date 2018/8/5
 */
@ConfigurationProperties("olympus")
public class ListMapProperties {
    private final List<OlympusProperteis> list = new ArrayList<>();
    private final Map<String, OlympusProperteis> map = new HashMap<>();
    private final List<DataSourceProperties> dataSources = new ArrayList<>();
    private final Map<String, DataSourceProperties> dataSourceMap = new HashMap<>();

    public List<OlympusProperteis> getList() {
        return this.list;
    }

    public Map<String, OlympusProperteis> getMap() {
        return this.map;
    }

    public List<DataSourceProperties> getDataSources() {
        return this.dataSources;
    }

    public Map<String, DataSourceProperties> getDataSourceMap() {
        return this.dataSourceMap;
    }
}
