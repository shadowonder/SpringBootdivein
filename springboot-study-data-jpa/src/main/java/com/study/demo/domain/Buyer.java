package com.study.demo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 一对多的案例中一的一方
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "12many_buyer")
public class Buyer {
    @Id //这是主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id为生成策略
    @Column(name = "buyer_id")   //column的字段用的是name而不是value
    private Long buyerId;
    @Column(name = "buyer_name")
    private String buyerName;
    @Column(name = "buyer_contact")
    private String buyerContact;

    /**
     * 一对多关系,推荐使用set来定义
     * 1. 声明关系
     *      @OneToMany: 配置一对多的关系
     *          targetEntity: 对方对象的字节码对象
     *          cascade 级联保存、更新、删除、刷新;延迟加载。当删除buyer，会级联删除该buyer的所有order
     *          mappedBy 拥有mappedBy注解的实体类为关系被维护端
     *              mappedBy="buyer"中的buyer是Order中的buyer属性
     * 2. 配置外键（或者中间表）
     *      @JoinColumn
     *          外键叫什么名字
     *          name： 外间字段名称
     *          referencedColumnName： 主表的主键字段名称
     * 注意:[*在主表中配置了外键的设置，在从表中也具备了维护外键的作用*]
     *  这里是配置了类似双向一对多的关系
     */
    @OneToMany(mappedBy = "buyer" , cascade = CascadeType.ALL,fetch = FetchType.LAZY, targetEntity = Order.class)
//    @JoinColumn(name = "order_buyer_id", referencedColumnName = "buyer_id")
    private Set<Order> orders = new HashSet<Order>();
}
