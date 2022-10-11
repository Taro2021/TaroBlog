package com.taro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName TaroBlogApplication
 * Author taro
 * Date 2022/10/11 9:54
 * Version 1.0
 */

@SpringBootApplication
public class TaroBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaroBlogApplication.class,args);
    }
}
