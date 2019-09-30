package com.hukaihan.redis.cachedemo.cachedemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.hukaihan.redis.**.cacheDao"})
public class CachedemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CachedemoApplication.class, args);
    }

}
