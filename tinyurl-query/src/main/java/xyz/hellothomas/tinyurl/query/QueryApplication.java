package xyz.hellothomas.tinyurl.query;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @ClassName Application
 * @Author 80234613
 * @Date 2019-7-7 13:40
 * @Descripton
 * @Version 1.0
 */
@EnableCaching
@SpringBootApplication(scanBasePackages = "xyz.hellothomas.tinyurl.*")
@MapperScan(basePackages = {"xyz.hellothomas.tinyurl.*.infrastructure.mapper"})
public class QueryApplication {
    public static void main(String[] args) {
        SpringApplication.run(QueryApplication.class, args);
    }
}
