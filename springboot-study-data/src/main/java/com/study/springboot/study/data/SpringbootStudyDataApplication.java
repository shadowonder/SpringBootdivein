package com.study.springboot.study.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringbootStudyDataApplication {

    public static void main(String[] args) {
//        new SpringApplicationBuilder(SpringbootStudyDataApplication.class)
//                .web(WebApplicationType.NONE) //不加载任何web模组的context,也可以只加载servlet和reactive模组
//                .properties("abc=def")  //添加一个属性abc，值为def
//                .run(args);
        SpringApplication.run(SpringbootStudyDataApplication.class, args);
    }
}
