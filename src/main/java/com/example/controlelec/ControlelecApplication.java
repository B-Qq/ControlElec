package com.example.controlelec;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.controlelec.mapper")
public class ControlelecApplication {
    public static void main(String[] args) {
        SpringApplication.run(ControlelecApplication.class, args);
    }

}
