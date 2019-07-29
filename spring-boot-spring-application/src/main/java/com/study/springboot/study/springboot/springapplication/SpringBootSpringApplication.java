package com.study.springboot.study.springboot.springapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link SpringApplication} 引导类
 */
public class SpringBootSpringApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ApplicationConfiguration.class, args);

        Set<String> sources = new HashSet<>();
        //配置class名称
        sources.add(ApplicationConfiguration.class.getName());

        SpringApplication springApplication = new SpringApplication();
        springApplication.setSources(sources);
//        springApplication.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext context = springApplication.run(args);
    }

    @SpringBootApplication
    public static class ApplicationConfiguration{
    }
}
