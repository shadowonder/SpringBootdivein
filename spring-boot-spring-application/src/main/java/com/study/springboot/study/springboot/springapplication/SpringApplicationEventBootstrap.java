package com.study.springboot.study.springboot.springapplication;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring应用事件引导类
 *
 */
public class SpringApplicationEventBootstrap {
    public static void main(String[] args) {
        //创建一个上下文
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        //注册应用事件监听器
        context.addApplicationListener(applicationEvent -> {
            System.out.println("监听到事件："  +  applicationEvent);
        });

        //启动上下文
        context.refresh();

        //发送一个事件
        context.publishEvent("Hello world a"); // 发送的是一个payload事件
        context.publishEvent("Hello world b"); // 发送的是一个payload事件

        //自定义一个事件
        context.publishEvent(new ApplicationEvent("Hello world c") {
        });

        //关闭上下文
        context.close();
    }
}
