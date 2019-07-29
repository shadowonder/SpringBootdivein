package com.study.springboot.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//开启servlet映射扫描
@ServletComponentScan(basePackages = "com.study.springboot.study.web.servlet")
public class SpringbootStudyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootStudyWebApplication.class, args);
    }

}
