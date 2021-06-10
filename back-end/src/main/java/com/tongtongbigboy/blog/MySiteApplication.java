package com.tongtongbigboy.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"com.tongtongbigboy.blog.dao"})
public class MySiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySiteApplication.class, args);
	}
}
