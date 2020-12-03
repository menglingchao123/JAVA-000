package org.example.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class DynamicDatasourceConfiguration {

    @Bean
    @ConditionalOnBean(DataSource.class)
    public DynamicDatasource dynamicDatasource(List<HikariDataSource> sources){
        DynamicDatasource dynamicDatasource = new DynamicDatasource();
        //设置数据源集合
        dynamicDatasource.setTargetDataSources(sources.stream().collect(Collectors.toMap(HikariDataSource::getPoolName, item -> item)));
        //设置默认数据源
        dynamicDatasource.setDefaultTargetDataSource(sources.stream().filter(item-> item.getPoolName().equals("primary")).findFirst().get());
        dynamicDatasource.afterPropertiesSet();
        return dynamicDatasource;
    }

}
