package com.wp3x.redisdemo;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RedisdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run( RedisdemoApplication.class, args );
    }

    @Bean
    public RedissonClient redissonClient() {
        Config configuration = new Config();
        configuration.useSingleServer().setAddress( "redis://localhost:6379" );
        return Redisson.create( configuration );
    }

    @Bean
    public JSONMapper jsonMapper() {
        return new JSONMapper();
    }
}
