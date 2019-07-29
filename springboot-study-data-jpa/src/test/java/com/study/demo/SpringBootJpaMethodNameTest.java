package com.study.demo;

import com.study.demo.dao.CustomerDao;
import com.study.demo.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class) //声明spring提供的单元测试环境
@SpringBootTest
public class SpringBootJpaMethodNameTest {

    @Autowired
    private CustomerDao customerDao;

    /**
     *
     */
    @Test
    public void testJPQL1() {
        List<Customer> customer = customerDao.findByCustAddressLikeAndCustIndustryNotNullAndCustNameContains("%test%","test");
        System.out.println(customer);
    }

}
