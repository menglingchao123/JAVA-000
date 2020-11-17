package org.custom.demo;

import org.demo.starter.pojo.School;
import org.demo.starter.pojo.Student;
import org.demo.starter.pojo.Klass;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.util.List;

@ImportResource("classpath:applicationContext.xml")
@SpringBootApplication
public class Application {
    public static void main( String[] args ) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .build()
                .run(args);

        School school = context.getBean(School.class);
        System.out.println("school bean ===========" + school);
        Klass kClass = school.getKClass();
        System.out.println("kclass bean ===========" + kClass);
        List<Student> students = kClass.getStudents();
        System.out.println("students bean ===========" + students.get(0));

        context.close();
    }
}
