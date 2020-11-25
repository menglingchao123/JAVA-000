package org.jdbc.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Data
@ConfigurationProperties(prefix = "demo")
public class DemoConfiguration {
    private String username;
    private String password;

    @PostConstruct
    public void init(){
        System.out.println(username+""+password);
    }
}
