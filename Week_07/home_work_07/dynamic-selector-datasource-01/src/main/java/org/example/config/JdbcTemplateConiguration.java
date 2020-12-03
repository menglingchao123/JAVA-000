package org.example.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@Configuration
public class JdbcTemplateConiguration {

    @Bean("primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("primary") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean("secondaryJdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(@Qualifier("secondary") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
