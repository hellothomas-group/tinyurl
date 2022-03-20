package xyz.hellothomas.tinyurl.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Thomas
 * @date 2022/3/19 23:44
 * @description
 * @version 1.0
 */
@SpringBootApplication()
@MapperScan(basePackages = {"xyz.hellothomas.tinyurl.*.infrastructure.mapper"})
public class GeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }
}
