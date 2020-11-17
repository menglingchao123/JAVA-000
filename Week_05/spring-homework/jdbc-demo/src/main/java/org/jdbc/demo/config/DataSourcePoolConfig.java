package org.jdbc.demo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 配置hikari数据库连接池
 * @author lingchaomeng
 * @Date 2020/11/17
 */
@Configuration
public class DataSourcePoolConfig {

    @Resource
    private JdbcConnectionProperties jdbcConnectionProperties;

    @Bean
    public DataSource dataSource(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcConnectionProperties.getUrl());
        hikariConfig.setUsername(jdbcConnectionProperties.getUsername());
        hikariConfig.setPassword(jdbcConnectionProperties.getPassword());
        hikariConfig.setDriverClassName(jdbcConnectionProperties.getDriver());
        return new HikariDataSource(hikariConfig);
    }

}
