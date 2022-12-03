package com.fusiyi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.fusiyi.mapper")
@EnableScheduling
@EnableSwagger2
public class BlogFontApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogFontApplication.class,args);
    }
}
