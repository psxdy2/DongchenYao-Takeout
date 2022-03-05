package com.example.takeout_server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.takeout_server.mapper")
public class TakeoutServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeoutServerApplication.class, args);
    }

}
