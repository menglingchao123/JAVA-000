package org.jdbc.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jdbc")
public class JdbcConnectionProperties {
    private String username;
    private String password;
    private String url;
    private String driver;
}
