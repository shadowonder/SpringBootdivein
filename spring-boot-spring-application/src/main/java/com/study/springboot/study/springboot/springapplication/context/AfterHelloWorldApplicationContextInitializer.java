package com.study.springboot.study.springboot.springapplication.context;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

//@Order(Ordered.HIGHEST_PRECEDENCE)  //实现方式相同，效果一样
public class AfterHelloWorldApplicationContextInitializer <C extends ConfigurableApplicationContext>
        implements ApplicationContextInitializer<C>,
        Ordered{
    @Override
    public void initialize(C configurableApplicationContext) {
        System.out.println("After application.id: " + configurableApplicationContext.getId());
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
