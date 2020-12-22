package io.mlc.transaction.serivce;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("io.mlc.transaction.serivce.dao")
public class ATransactionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ATransactionServiceApplication.class,args);
    }

}
