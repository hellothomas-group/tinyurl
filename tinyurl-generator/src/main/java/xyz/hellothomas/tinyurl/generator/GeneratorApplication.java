package xyz.hellothomas.tinyurl.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName Application
 * @Author 80234613
 * @Date 2019-7-7 13:40
 * @Descripton
 * @Version 1.0
 */
@SpringBootApplication()
@MapperScan(basePackages = {"xyz.hellothomas.tinyurl.*.infrastructure.mapper"})
public class GeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }
}
