package com.study.demo.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * 1. 实体类和表的映射关系
 *      @Entity
 *      @Table
 * 2. 类中属性和表中字段的映射关系
 *      @Id
 *      @GeneratedValue
 *      @Column
 */
@Data
@ToString
@Entity
@Table(name = "cst_customer")
public class Customer {
    @Id //这是主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id为生成策略
    @Column(name = "cust_id")   //column的字段用的是name而不是value，注意
    private Long custId;
    @Column(name = "cust_industry")
    private String custIndustry;
    @Column(name = "cust_address")
    private String custAddress;
    @Column(name = "cust_level")
    private String custLevel;
    @Column(name = "cust_name")
    private String custName;
    @Column(name = "cust_phone")
    private String custPhone;
    @Column(name = "cust_source")
    private String custSource;


}
