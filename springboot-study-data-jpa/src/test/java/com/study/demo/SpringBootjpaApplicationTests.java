package com.study.demo;

import com.study.demo.dao.CustomerDao;
import com.study.demo.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class) //声明spring提供的单元测试环境
@SpringBootTest
public class SpringBootjpaApplicationTests {

    @Autowired
    private CustomerDao customerDao;

    /**
     * 根据ID查询
     */
    @Test
    public void whenTryToFindOne(){
        Customer customer = customerDao.findByCustId(1L);
        System.out.println(customer);

    }

    /**
     * 条件查询
     */
    @Test
    public void whenFindAllcustomerAddressHasTest(){
        List<Customer> list = customerDao.findAllByCustAddressContains("test");
        System.out.println(list);
    }
    /**
     * save 保存/更新客户
     */
    @Test
    public void whenSaveACustomerEitherNewOrOld(){

        Customer a = new Customer();
        a.setCustName("testa");
        a.setCustAddress("testa");
        a.setCustIndustry("testa");
        a.setCustLevel("testa");
        a.setCustPhone("123a456a789a");
        a.setCustSource("testa");
        customerDao.save(a);

        a = customerDao.findByCustId(1L);
        a.setCustLevel("testtest");
        customerDao.save(a);

    }

    @Test
    @Transactional //保证getone方法正常运行
    public void whenCountAndExist(){
        System.out.println("count:" + customerDao.count());

        //这个方法使用的是延迟加载
        System.out.println("getone:" + customerDao.getOne(1L)); //需要加上@transactional
    }

}
