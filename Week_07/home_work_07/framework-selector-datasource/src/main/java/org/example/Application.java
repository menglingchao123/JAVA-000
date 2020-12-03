package org.example;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Application {
    public static void main( String[] args ) {
        new SpringApplicationBuilder()
                .web(WebApplicationType.NONE)
                .main(Application.class)
                .build()
                .run(args);
    }
}
