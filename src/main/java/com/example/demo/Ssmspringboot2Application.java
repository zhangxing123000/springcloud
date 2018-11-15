package com.example.demo;

import javafx.application.Application;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement//开启事务管理
@MapperScan("com.example.demo.dao")//与dao层的@Mapper二选一写上即可(主要作用是扫包)
public class Ssmspringboot2Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Ssmspringboot2Application.class, args);
	}
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Ssmspringboot2Application.class);
    }
}
