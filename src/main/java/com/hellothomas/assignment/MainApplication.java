package com.hellothomas.assignment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName MainApplication
 * @Author 80234613
 * @Date 2019-7-7 13:40
 * @Descripton
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.hellothomas.assignment.infrastructure.mapper"})
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
