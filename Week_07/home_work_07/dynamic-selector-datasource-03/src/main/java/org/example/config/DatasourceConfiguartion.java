package org.example.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguartion {

    @Bean("primary")
    @Qualifier("primary")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDatasource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("secondary")
    @Qualifier("secondary")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDatasource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("thirdary")
    @Qualifier("thirdary")
    @ConfigurationProperties(prefix = "spring.datasource.thirdary")
    public DataSource thirdaryDatasource(){
        return DataSourceBuilder.create().build();
    }
}
