package org.demo.starter.config;

import org.demo.starter.annonation.EnableDemo;
import org.demo.starter.pojo.Klass;
import org.demo.starter.pojo.School;
import org.demo.starter.pojo.Student;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Collections;


@Configuration
@EnableConfigurationProperties
@ConditionalOnProperty(prefix = "demo.basic", name = "enable", havingValue = "true")
@EnableDemo
public class DemoAutoConfiguration{

}
