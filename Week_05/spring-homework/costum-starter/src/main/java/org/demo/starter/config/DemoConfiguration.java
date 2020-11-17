package org.demo.starter.config;

import org.demo.starter.pojo.Klass;
import org.demo.starter.pojo.School;
import org.demo.starter.pojo.Student;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class DemoConfiguration {

    @Bean
    public Student student(){
        return Student.builder().id(1).name("2").build();
    }

    @Bean
    @ConditionalOnBean(Student.class)
    public Klass klass(Student student){
        return Klass.builder().students(Collections.singletonList(student)).build();
    }

    @Bean
    @ConditionalOnBean(Klass.class)
    public School school(Klass klass){
        return School.builder().kClass(klass).build();
    }
}
