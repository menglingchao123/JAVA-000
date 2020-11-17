package org.custom.demo.config;

import org.custom.demo.pojo.Klass;
import org.custom.demo.pojo.School;
import org.custom.demo.pojo.Student;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PojoConfig {

    @Bean(name = "student2")
    public Student student2(){
        Student student = Student.builder().id(1).name("mlc").build();
        return student;
    }

    @Bean(name = "student1")
    public Student student1(){
        Student student = Student.builder().id(2).name("kimik").build();
        return student;
    }

    @Bean
    @ConditionalOnBean(Student.class)
    public Klass klass(List<Student> students){
        Klass klass = new Klass();
        klass.setStudents(students);
        return klass;
    }

    @Bean
    @ConditionalOnBean(Klass.class)
    public School school(Klass klass){
        School school = new School();
        school.setKClass(klass);
        return school;
    }
}
