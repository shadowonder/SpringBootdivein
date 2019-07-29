package com.study.demo.dao;

import com.study.demo.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderDao
        extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {
}
