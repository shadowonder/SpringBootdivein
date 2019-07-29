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
public class SpringBootJpaJPQLTest {

    @Autowired
    private CustomerDao customerDao;

    /**
     * 根据ID查询
     */
    @Test
    public void testJPQL1() {
        List<Customer> customer = customerDao.queryCustomerNameFromDatabaseUsingJpql1("test");
        System.out.println(customer);
    }

    @Test
    public void testJPQL2(){
        List<Customer> customer = customerDao.queryCustomerUsingNameAndLevel1("test","test");
        System.out.println(customer);
    }

    @Test
    @Transactional  //这是更新，添加事务
    public void testJPQL3(){
        Customer customer = customerDao.findByCustId(1L);
        customerDao.queryUpdateCustomer(customer.getCustId(),"This is a test");
        System.out.println(customer);
    }

    @Test
    public void testSQL1(){
        List<Object []> customers = customerDao.queryCustomerInfoUsingSQLLike("test%");
        for(Object [] customer : customers){
            for(Object o : customer){
                System.out.print(o + " ");
            }
            System.out.println();
        }
    }
}
