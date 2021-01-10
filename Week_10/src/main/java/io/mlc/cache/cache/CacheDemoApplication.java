package io.mlc.cache.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CacheDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheDemoApplication.class, args);
    }



    // 作业：
    // 1. 参考C2，实现基于Lettuce和Redission的Sentinel配置
    // 2. 实现springboot/spring data redis的sentinel配置
    // 3. 使用jedis命令，使用java代码手动切换 redis 主从
    // 	  Jedis jedis1 = new Jedis("localhost", 6379);
    //    jedis1.info...
    //    jedis1.set xxx...
    //	  Jedis jedis2 = new Jedis("localhost", 6380);
    //    jedis2.slaveof...
    //    jedis2.get xxx
    // 4. 使用C3的方式，使用java代码手动操作sentinel
}
