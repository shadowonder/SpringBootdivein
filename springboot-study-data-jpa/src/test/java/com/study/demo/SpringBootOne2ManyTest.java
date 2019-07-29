package com.study.demo;

import com.study.demo.dao.BuyerDao;
import com.study.demo.dao.OrderDao;
import com.study.demo.domain.Buyer;
import com.study.demo.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 一对多测试用例
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootOne2ManyTest {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private BuyerDao buyerDao;

    @Test
    @Transactional
    public void testSave(){

        Buyer buyer = new Buyer();
        buyer.setBuyerContact("testcontact");
        buyer.setBuyerName("abcd");

        Order a = new Order();
        a.setOrderName("a");
        a.setOrderNumber("test");
        Order b = new Order();
        b.setOrderName("b");
        b.setOrderNumber("test");

//        buyer.getOrders().add(a);
//        buyer.getOrders().add(b);
        a.setBuyer(buyer);
        b.setBuyer(buyer);

        //如果先保存order就不太好，最好先保存1的一方
        buyerDao.save(buyer);

        //如果使用保存order的动作可以把保存设置order的动作省略掉，order来维护和buyer的关系
        orderDao.save(a);
        orderDao.save(b);

    }
}
