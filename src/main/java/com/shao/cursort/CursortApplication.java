package com.shao.cursort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.shao.cursort.mapper"})
@ComponentScan(basePackages = {"com.shao.cursort.config","com.shao.cursort.interceptor", "com.shao.cursort.token","com.shao.cursort.controller","com.shao.cursort.service.impl","com.shao.cursort.handle"})
public class CursortApplication {

    public static void main(String[] args) {
        SpringApplication.run(CursortApplication.class, args);
    }

}
