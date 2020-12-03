package org.example.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class DynamicDatasourceConfiguration {

    @Bean
    @ConditionalOnBean(DataSource.class)
    public DynamicDatasource dynamicDatasource(Map<String,DataSource> sources){
        DynamicDatasource dynamicDatasource = new DynamicDatasource();
        Map<Object, Object> datasources = sources.entrySet().stream().collect(Collectors.toMap(x -> (Object)x.getKey(), x -> (Object) x.getValue()));
        dynamicDatasource.setTargetDataSources(datasources);
        dynamicDatasource.setDefaultTargetDataSource(sources.get("primary"));
        dynamicDatasource.afterPropertiesSet();
        return dynamicDatasource;
    }
}
