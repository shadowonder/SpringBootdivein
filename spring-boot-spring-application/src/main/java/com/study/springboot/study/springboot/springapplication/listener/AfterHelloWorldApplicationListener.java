package com.study.springboot.study.springboot.springapplication.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
/**
 * 监听context刷新事件{@link ContextRefreshedEvent}
 * after
 */
@Order(Ordered.LOWEST_PRECEDENCE)
public class AfterHelloWorldApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("* * * After " + contextRefreshedEvent.getApplicationContext().getId() +
                " ,TimeStamp" + contextRefreshedEvent.getTimestamp());
    }
}
