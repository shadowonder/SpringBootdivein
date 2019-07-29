package com.study.demo.dao;

import com.study.demo.domain.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface BuyerDao
        extends JpaRepository<Buyer,Long>, JpaSpecificationExecutor<Buyer> {
}
