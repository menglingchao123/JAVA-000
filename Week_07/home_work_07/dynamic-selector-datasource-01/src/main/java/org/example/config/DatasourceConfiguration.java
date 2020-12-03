package org.example.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {

    /**
     * 配置主数据源
     * @return
     */
    @Bean("primary")
    @Qualifier("primary")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDatasource(){
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置从数据源
     * @return
     */
    @Bean("secondary")
    @Qualifier("secondary")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDatasource(){
        return DataSourceBuilder.create().build();
    }
}
