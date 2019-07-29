package com.study.demo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * 1对多案例的多的一方
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "12many_order")
public class Order {
    @Id //这是主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id为生成策略
    @Column(name = "order_id")   //column的字段用的是name而不是value
    private Long orderId;
    @Column(name = "order_name")
    private String orderName;
    @Column(name = "order_number")
    private String orderNumber;

    /**
     * order对buyer的多对一关系
     * 使用注解的形式配置多对一的关系
     * 1. 配置表关系
     * 2. 配置外键
     *      @JoinColumn
     *          name： 外间字段名称
     *          referencedColumnName： 主表的主键字段名称
     */
    @ManyToOne(targetEntity = Buyer.class,cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    //可选属性optional=false,表示buyer不能为空。删除order，不影响buyer
//    @JoinColumn(name = "order_buyer_id", referencedColumnName = "buyer_id")
    @JoinColumn(name = "order_buyer_id")
    private Buyer buyer;
}
