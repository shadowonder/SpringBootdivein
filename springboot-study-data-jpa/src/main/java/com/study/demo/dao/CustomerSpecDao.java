package com.study.demo.dao;

import com.study.demo.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerSpecDao
        extends JpaRepository<Customer,Long>, JpaSpecificationExecutor<Customer> {
}
