package org.jdbc.demo;

import org.jdbc.demo.config.DemoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(DemoConfiguration.class)
@SpringBootApplication
public class JdbcBootstarpApplication {
    public static void main( String[] args ) {
        SpringApplication.run(JdbcBootstarpApplication.class,args);
    }
}
