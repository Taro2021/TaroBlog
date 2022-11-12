package com.taro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName BlogAdminApplication
 * Author taro
 * Date 2022/10/28 16:15
 * Version 1.0
 */


@SpringBootApplication
// @MapperScan(basePackages = "com.taro.mapper")
public class BlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
    }
}
