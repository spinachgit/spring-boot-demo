package com.roncoo.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
/**
 * @EnableCaching 使用缓存
 * @author:whh
 * @date:2017年12月4日下午3:11:36
 */
@EnableCaching
@ServletComponentScan
@SpringBootApplication
public class SpringBootDemo201Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemo201Application.class, args);
	}
}
