package tech.maplefall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("tech.maplefall.mapper")
@SpringBootApplication
public class AppStart {

    public static void main(String[] args) {
        SpringApplication.run(AppStart.class, args);
    }
}
