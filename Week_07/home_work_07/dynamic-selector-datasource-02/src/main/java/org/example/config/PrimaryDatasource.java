package org.example.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @desription 数据源配置01
 * @author 孟令超
 * @date 2020/12/3
 * @since jdk1.8
 * @version 1.0
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "datasource1")
public class PrimaryDatasource {

    private String username;
    private String password;
    private String driverClassName;
    private String url;

    @Bean("primary")
    public HikariDataSource hikariDataSource(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setPoolName("datasource1");
        return hikariDataSource;
    }
}
